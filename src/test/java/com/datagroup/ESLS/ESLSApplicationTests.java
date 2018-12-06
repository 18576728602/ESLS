package com.datagroup.ESLS;

import com.datagroup.ESLS.common.constant.SqlConstant;
import com.datagroup.ESLS.netty.command.CommandCategory;
import com.datagroup.ESLS.netty.command.ProtocolConstant;
import com.datagroup.ESLS.dao.*;
import com.datagroup.ESLS.entity.Good;
import com.datagroup.ESLS.entity.Photo;
import com.datagroup.ESLS.entity.Tag;
import com.datagroup.ESLS.entity.TagandGood;
import com.datagroup.ESLS.netty.executor.AsyncTask;
import com.datagroup.ESLS.redis.RedisConstant;
import com.datagroup.ESLS.service.TagAndGoodService;
import com.datagroup.ESLS.utils.CopyUtil;
import com.datagroup.ESLS.utils.SpringContextUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.InetSocketAddress;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ESLSApplicationTests {
    @Autowired
    private TagDao tagsDao;
    @Autowired
    private GoodDao goodDao;
    @Autowired
    private TagAndGoodDao tagAndGoodDao;
    @Autowired
    private PhotoDao photoDao;
    @Autowired
    private TagAndGoodService tagAndGoodService;
    @Autowired
    private AsyncTask asyncTask;
    @Autowired
    private RedisConstant redisConstant;

    @Autowired
    private BaseDao baseDao;
    @Test
    public void displayStyle() {
        // 根据标签和商品显示样式
        Tag tag = tagsDao.findByBarCode("1347770451");
        Good g = goodDao.findByBarCode("6901236341957");
        List<TagandGood> list = tagAndGoodDao.findByidAndShopNumber(g.getId(),"A0004");
        TagandGood tg = new TagandGood();
        if(list.size() < 0)
        {
            TagandGood tg1 = (TagandGood)list.get(0);
            tg.setPrice(tg1.getPrice());
            tg.setPromotePrice(tg1.getPromotePrice());
            tg.setPromotionReason(tg1.getPromotionReason());
            tg.setPromotion(tg1.getPromotion());
            tg.setUnit(tg1.getUnit());
        } else
        {
            tg.setPrice(g.getPrice());
            tg.setPromotePrice(g.getPromotePrice());
            tg.setPromotionReason(g.getPromotionReason());
            tg.setUnit(g.getUnit());
            tg.setPromotionReason(g.getPromotionReason());
        }
        tg.setTag(tag);
        tg.setGood(g);
        tg.setBarcode(g.getBarCode());
        tg.setName(g.getName());
        tg.setQrCode(g.getQrCode());
        tg.setOrigin(g.getOrigin());
        tg.setProvider(g.getProvider());
        tg.setStatus(1);
        tagAndGoodDao.save(tg);
        byte img[] = SpringContextUtil.getImageBytes(tagAndGoodService, photoDao, tg);
        Photo photo = new Photo();
        photo.setName("12300");
        photo.setHeight(100);
        photo.setWidth(150);
        photo.setPhoto(img);
        photo.setRedphoto(img);
        photoDao.save(photo);
    }
    @Test
    public void testAsync() {
       // asyncTask.execute("handler22");
    }

    @Test
    public void testRedisKeys(){
        redisConstant.getExpiresMap().forEach((key, value)->{
            System.out.println(key+" "+value);
        });
    }
    @Test
    public void testNettyClient(){
        byte[] message = new byte[2];
        message[0]=0x02;
        message[1]=0x03;
        InetSocketAddress target = new InetSocketAddress("localhost", 9001);
            // System.out.println("执行结果："+NettyClient.startAndWrite(target,message));
    }
    @Test
    public void testProperty(){
        System.out.println(ProtocolConstant.COMMAND);
        System.out.println(CommandCategory.COMMAND_CATEGORY);
        System.out.println(CommandCategory.COMMAND_CATEGORY.get("ACK").getCommand_class() == 0x01);
       // Tag tag = new Tag();
     //   Good good = new Good();
    }

    @Test
    public void testPOI(){
        List goodlist = baseDao.findBySql(SqlConstant.QUERY_TABLIE_COLUMN + "\'goods\'");
        List<Good> goods = goodDao.findAll();
        List goodVos = CopyUtil.copyGood(goods);
       // PoiUtil.exportEmp2Excel(goodVos,goodlist);
    }
}
