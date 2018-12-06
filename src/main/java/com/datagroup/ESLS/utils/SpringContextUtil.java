
package com.datagroup.ESLS.utils;

import com.datagroup.ESLS.dao.PhotoDao;
import com.datagroup.ESLS.entity.*;
import com.datagroup.ESLS.graphic.BarCode;
import com.datagroup.ESLS.graphic.QRCode;
import com.datagroup.ESLS.netty.server.ServerChannelHandler;
import com.datagroup.ESLS.service.TagAndGoodService;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.SocketAddress;
import java.util.*;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

/**
 * @author lenovo
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (SpringContextUtil.applicationContext == null) {
            SpringContextUtil.applicationContext = applicationContext;
        }
    }

    // 获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    // 通过name获取 Bean.
    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }

    // 通过class获取Bean.
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    public SpringContextUtil() {
    }

    public static void setUser(User user, HttpServletRequest request) {
        if (user != null) {
            request.getSession().setMaxInactiveInterval(0);
            request.getSession().setAttribute("sessionUser", user);
        } else {
            request.getSession().removeAttribute("sessionUser");
        }
    }

    public static User get(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("sessionUser");
    }

    public static ChannelGroup getChannelGroup() {
        return channelGroup;
    }

    public static Map<SocketAddress,Channel> getChannelIdGroup() {
        return channelIdGroup;
    }

    public static boolean setTagMap(Map trMap, HttpServletRequest request) {
        User user = get(request);
        request.getSession().setMaxInactiveInterval(0);
        request.getSession().setAttribute(user.getName(), trMap);
        return true;
    }

    public static Map getTagMap(HttpServletRequest request) {
        User user = get(request);
        Map attribute = (Map) request.getSession().getAttribute(user.getName());
        return attribute;
    }

    public static void removeTagMap(HttpServletRequest request) {
        User user = get(request);
        request.getSession().removeAttribute(user.getName());
    }

    public static byte[] getImageBytes(TagAndGoodService tagAndGoodDao, PhotoDao photoDao, TagandGood good) {

        if (good.getTag() != null && good.getTag().getStyle() != null && good.getTag().getStyle().getDispmses() != null) {
            Style style = good.getTag().getStyle();
            Collection<Dispms> dispMs = good.getTag().getStyle().getDispmses();
            for (Iterator iterator = dispMs.iterator(); iterator.hasNext(); ) {
                Dispms dispM = (Dispms) iterator.next();
                StringBuilder sqlBuilder = new StringBuilder("select ");
                sqlBuilder.append(dispM.getSourceColumn()).append(" ");
                sqlBuilder.append("from ").append(dispM.getSource()).append(" ");
                sqlBuilder.append("where id=").append(good.getId());
                java.util.List list = tagAndGoodDao.findBySql(sqlBuilder.toString());
                if (list != null && list.size() > 0) {
                    Object obj = list.get(0);
                    if (dispM.getColumnType().contains("数字")) {
                        dispM.setText(String.format("%.2f", new Object[]{
                                obj
                        }));
                    } else if (dispM.getName().equals("条形码")) {
                        int o = Integer.parseInt("123");
                        String barcode = (new StringBuilder(String.valueOf(o >> 24 & 0xff))).append(".").append(o >> 16 & 0xff).append(".").append(o >> 8 & 0xff).append(".").append(o & 0xff).toString();
                        dispM.setText(barcode);
                    }
                    // 字符串
                    else {
                        dispM.setText(String.valueOf(obj));
                    }
                }
            }
            try {
                BufferedImage bfi = createBufferedImage(style.getWidth(), style.getHeight());
                BufferedImage bufferedImage = getBufferedImage(bfi, photoDao, dispMs);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "BMP", outputStream);
                byte byteArray[] = outputStream.toByteArray();
                outputStream.close();
                return byteArray;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static BufferedImage createBufferedImage(int width, int height) throws IOException {
        // 位图
        BufferedImage bufferedImage = new BufferedImage(width, height, 12);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++)
                bufferedImage.setRGB(i, j, Color.WHITE.getRGB());

        }
        return bufferedImage;
    }

    private static BufferedImage getBufferedImage(BufferedImage bufferedImage, PhotoDao photoService, Collection<Dispms> dispMs)
            throws IOException {
       // Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
        BufferedImage buffer = null;
        Graphics2D g2d = null;
        int bold = 0;
        for (Iterator iterator = dispMs.iterator(); iterator.hasNext(); ) {
            Dispms dispM = (Dispms) iterator.next();
            if(dispM.getId()==21)
                continue;
            buffer = new BufferedImage(dispM.getWidth(),dispM.getHeight(),12);
            g2d = (Graphics2D) buffer.getGraphics();
            if (dispM.getStatus() && !dispM.getSource().equals("tb_photos"))
                if (dispM.getColumnType().contains("字符串") && dispM.getFontColor() == 0) {
                    g2d.setColor(new Color(dispM.getBackgroundColor()));
                    g2d.fillRect(dispM.getX(), dispM.getY(), dispM.getWidth(), dispM.getHeight());
                    g2d.setColor(new Color(dispM.getFontColor()));
                    g2d.setStroke(new BasicStroke(dispM.getLineBoarderWidth()));
                    if (dispM.getFontBold().equals("bold"))
                        bold = 1;
                    g2d.setFont(new Font(dispM.getFontFamily(), bold, dispM.getFontSize()));
                    g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
                    if (dispM.getTextAlign().equals("center")) {
                        String text = (new StringBuilder(String.valueOf(dispM.getStartText()))).append(dispM.getText()).append(dispM.getEndText()).toString();
                        int x = dispM.getX() + (dispM.getWidth() - text.length() * dispM.getFontSize()) / 2;
                        g2d.drawString(text, x, dispM.getTextY() + dispM.getFontSize());
                    } else if (dispM.getSourceColumn().equals("origin")) {
                        String text = (new StringBuilder(String.valueOf(dispM.getStartText()))).append(dispM.getText()).append(dispM.getEndText()).toString();
                        int count = dispM.getWidth() / dispM.getFontSize();
                        if (count > text.length()) {
                            g2d.drawString((new StringBuilder(String.valueOf(dispM.getStartText()))).append(dispM.getText()).append(dispM.getEndText()).toString(), dispM.getTextX(), dispM.getTextY() + dispM.getFontSize());
                        } else {
                            for (int i = 0; i < (text.length() % count != 0 ? text.length() / count + 1 : text.length() / count); i++) {
                                String string = "";
                                if (i == (text.length() % count != 0 ? text.length() / count + 1 : text.length() / count) - 1)
                                    string = text.substring(i * count);
                                else
                                    string = text.substring(i * count, (i + 1) * count);
                                g2d.drawString(string, dispM.getTextX(), dispM.getTextY() + (dispM.getFontSize() + 5) * i);
                            }

                        }
                        System.out.println("orgin");
                    } else {
                        g2d.drawString((new StringBuilder(String.valueOf(dispM.getStartText()))).append(dispM.getText()).append(dispM.getEndText()).toString(), dispM.getTextX(), dispM.getTextY() + dispM.getFontSize());
                    }
                } else if (dispM.getColumnType().contains("数字") && dispM.getFontColor() == 0) {
                    g2d.setColor(new Color(dispM.getBackgroundColor()));
                    g2d.fillRect(dispM.getX(), dispM.getY(), dispM.getWidth(), dispM.getHeight());
                    g2d.setColor(new Color(dispM.getFontColor()));
                    g2d.setStroke(new BasicStroke(dispM.getLineBoarderWidth()));
                    if (dispM.getFontBold().equals("bold"))
                        bold = 1;
                    g2d.setFont(new Font(dispM.getFontFamily(), bold, dispM.getFontSize()));
                    g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
                    if (dispM.getStartText().contains("原价")) {
                        g2d.drawString((new StringBuilder(String.valueOf(dispM.getStartText()))).append(dispM.getText()).append(dispM.getEndText()).toString(), dispM.getTextX(), dispM.getTextY() + dispM.getFontSize());
                    } else {
                        String pString = (new StringBuilder(String.valueOf(dispM.getStartText()))).append(dispM.getText().substring(0, dispM.getText().indexOf(".") + 1)).toString();
                        g2d.drawString(pString, dispM.getTextX(), dispM.getTextY() + dispM.getFontSize());
                        String pString2 = dispM.getText().substring(dispM.getText().indexOf(".") + 1);
                        g2d.setFont(new Font(dispM.getFontFamily(), bold, dispM.getFontSize() / 2 + dispM.getFontSize() / 4));
                        int xl = 0;
                        if (dispM.getStartText() != null && dispM.getStartText().trim() != "")
                            xl = dispM.getTextX() + dispM.getFontSize() + ((pString.length() - 1) * dispM.getFontSize()) / 2;
                        else
                            xl = dispM.getTextX() + (pString.length() * dispM.getFontSize()) / 2;
                        g2d.drawString(pString2, xl, dispM.getTextY() + dispM.getFontSize() / 2 + dispM.getFontSize() / 4);
                    }
                } else if (dispM.getColumnType().contains("二维码") && dispM.getFontColor() == 0) {
                    int value = Math.min(dispM.getWidth(), dispM.getHeight());
                    BufferedImage img = QRCode.encode(dispM.getText(), value, value);
                    g2d.drawImage(img, dispM.getX(), dispM.getY(), null);
                } else if (dispM.getColumnType().contains("条形码") && dispM.getFontColor() == 0) {
                    BufferedImage img = BarCode.encode(dispM.getText(), dispM.getWidth(), dispM.getHeight());
                    g2d.drawImage(img, dispM.getX(), dispM.getY(), null);
                }
                return buffer;
        }
        for (Iterator iterator1 = dispMs.iterator(); iterator1.hasNext(); ) {
            Dispms dispM = (Dispms) iterator1.next();
            if(dispM.getId()==21)
                continue;
//            buffer = new BufferedImage(dispM.getWidth(),dispM.getHeight(),12);
//            g2d = (Graphics2D) buffer.getGraphics();
            if (dispM.getStatus())
                if (dispM.getSource().equals("tb_photos")) {
                    String name = dispM.getName();
                    Photo photo = photoService.findByName(name);
                    ByteArrayInputStream in = new ByteArrayInputStream(photo.getPhoto());
                    BufferedImage bf = ImageIO.read(in);
                    in.close();
                    // 宋体
                    g2d.setFont(new Font("宋体", 0, 20));
                    g2d.setBackground(new Color(0xffffff));
                    g2d.setColor(new Color(0));
                    g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
                    g2d.drawImage(bf.getScaledInstance(dispM.getWidth(), dispM.getHeight(), 8), dispM.getX(), dispM.getY(), dispM.getWidth(), dispM.getHeight(), null);
                }
                // 字符串
                else if (dispM.getColumnType().contains("字符串") && dispM.getFontColor() > 0) {
                    g2d.setColor(new Color(dispM.getBackgroundColor()));
                    g2d.fillRect(dispM.getX(), dispM.getY(), dispM.getWidth(), dispM.getHeight());
                    g2d.setColor(new Color(dispM.getFontColor()));
                    g2d.setStroke(new BasicStroke(dispM.getLineBoarderWidth()));
                    if (dispM.getFontBold().equals("bold"))
                        bold = 1;
                    g2d.setFont(new Font(dispM.getFontFamily(), bold, dispM.getFontSize()));
                    g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
                    if (dispM.getTextAlign().equals("center")) {
                        String text = (new StringBuilder(String.valueOf(dispM.getStartText()))).append(dispM.getText()).append(dispM.getEndText()).toString();
                        int x = dispM.getX() + (dispM.getWidth() - text.length() * dispM.getFontSize()) / 2;
                        g2d.drawString(text, x, dispM.getTextY() + dispM.getFontSize());
                    } else if (dispM.getSourceColumn().equals("origin")) {
                        String text = (new StringBuilder(String.valueOf(dispM.getStartText()))).append(dispM.getText()).append(dispM.getEndText()).toString();
                        int count = dispM.getWidth() / dispM.getFontSize();
                        if (count > text.length()) {
                            g2d.drawString((new StringBuilder(String.valueOf(dispM.getStartText()))).append(dispM.getText()).append(dispM.getEndText()).toString(), dispM.getTextX(), dispM.getTextY() + dispM.getFontSize());
                        } else {
                            for (int i = 0; i < (text.length() % count != 0 ? text.length() / count + 1 : text.length() / count); i++) {
                                String string = "";
                                if (i == (text.length() % count != 0 ? text.length() / count + 1 : text.length() / count) - 1)
                                    string = text.substring(i * count);
                                else
                                    string = text.substring(i * count, (i + 1) * count);
                                g2d.drawString(string, dispM.getTextX(), dispM.getTextY() + (dispM.getFontSize() + 5) * i);
                            }
                        }
                    } else {
                        g2d.drawString((new StringBuilder(String.valueOf(dispM.getStartText()))).append(dispM.getText()).append(dispM.getEndText()).toString(), dispM.getTextX(), dispM.getTextY() + dispM.getFontSize());
                    }
                } else if (dispM.getColumnType().contains("数字") && dispM.getFontColor() > 0) {
                    g2d.setColor(new Color(dispM.getBackgroundColor()));
                    g2d.fillRect(dispM.getX(), dispM.getY(), dispM.getWidth(), dispM.getHeight());
                    g2d.setColor(new Color(dispM.getFontColor()));
                    g2d.setStroke(new BasicStroke(dispM.getLineBoarderWidth()));
                    if (dispM.getFontBold().equals("bold"))
                        bold = 1;
                    g2d.setFont(new Font(dispM.getFontFamily(), bold, dispM.getFontSize()));
                    g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);
                    // 原价
                    if (dispM.getStartText().contains("原价")) {
                        g2d.drawString((new StringBuilder(String.valueOf(dispM.getStartText()))).append(dispM.getText()).append(dispM.getEndText()).toString(), dispM.getTextX(), dispM.getTextY() + dispM.getFontSize());
                    } else {
                        String pString = (new StringBuilder(String.valueOf(dispM.getStartText()))).append(dispM.getText().substring(0, dispM.getText().indexOf(".") + 1)).toString();
                        g2d.drawString(pString, dispM.getTextX(), dispM.getTextY() + dispM.getFontSize());
                        String pString2 = dispM.getText().substring(dispM.getText().indexOf(".") + 1);
                        g2d.setFont(new Font(dispM.getFontFamily(), bold, dispM.getFontSize() / 2 + dispM.getFontSize() / 4));
                        int xl = 0;
                        if (dispM.getStartText() != null && !dispM.getStartText().trim().equals(""))
                            xl = dispM.getTextX() + dispM.getFontSize() + ((pString.length() - 1) * dispM.getFontSize()) / 2 + 5;
                        else
                            xl = dispM.getTextX() + (pString.length() * dispM.getFontSize()) / 2;
                        g2d.drawString(pString2, xl, dispM.getTextY() + dispM.getFontSize() / 2 + dispM.getFontSize() / 4);
                    }
                }
                // 二维码
                else if (dispM.getColumnType().contains("二维码") && dispM.getFontColor() > 0) {
                    int value = Math.min(dispM.getWidth(), dispM.getHeight());
                    BufferedImage img = QRCode.encode(dispM.getText(), value, value);
                    g2d.drawImage(img, dispM.getX(), dispM.getY(), null);
                }
                // 条形码
                else if (dispM.getColumnType().contains("条形码") && dispM.getFontColor() > 0) {
                    BufferedImage img = BarCode.encode(dispM.getText(), dispM.getWidth(), dispM.getHeight());
                    g2d.drawImage(img, dispM.getX(), dispM.getY(), null);
                }
            break;
        }
        return buffer;
    }

    public static final String SESSIONUSER = "sessionUser";
    public static final Map IMAGELIST = new HashMap();
    public static final Map PROGRESSMAP = new HashMap();
    public static final Map SESSIONRECORD = new HashMap();
    public static boolean QUERYTAGLISTACK = false;
    public static ChannelGroup channelGroup;
    public static Map<SocketAddress, Channel> channelIdGroup = new HashMap();
    public static ServerChannelHandler serverChannelHandler;
    private static String commandTime;
    private static Long aliveTime = (long)6;
    public static String getCommandTime() {
        return commandTime;
    }

    public static void setCommandTime(String commandTime) {
        SpringContextUtil.commandTime = commandTime;
    }

    public static Long getAliveTime() {
        return aliveTime;
    }

    public static void setAliveTime(Long aliveTime) {
        SpringContextUtil.aliveTime = aliveTime;
    }

    static {
        channelGroup = new DefaultChannelGroup("NettyServer", GlobalEventExecutor.INSTANCE);
    }
}
