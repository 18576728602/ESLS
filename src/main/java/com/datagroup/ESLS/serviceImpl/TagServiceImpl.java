package com.datagroup.ESLS.serviceImpl;

import com.datagroup.ESLS.common.constant.TableConstant;
import com.datagroup.ESLS.common.request.RequestBean;
import com.datagroup.ESLS.common.request.RequestItem;
import com.datagroup.ESLS.common.response.ResponseBean;
import com.datagroup.ESLS.dao.TagDao;
import com.datagroup.ESLS.entity.Router;
import com.datagroup.ESLS.entity.Tag;
import com.datagroup.ESLS.netty.client.NettyClient;
import com.datagroup.ESLS.redis.RedisConstant;
import com.datagroup.ESLS.service.TagService;
import com.datagroup.ESLS.utils.NettyUtil;
import com.datagroup.ESLS.utils.ResponseUtil;
import com.datagroup.ESLS.utils.SpringContextUtil;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class TagServiceImpl extends BaseServiceImpl implements TagService {
    @Autowired
    private TagDao tagDao;
    @Autowired
    private NettyUtil nettyUtil;
    @Override
    public List<Tag> findAll() {
        return tagDao.findAll();
    }

    @Override
    public List<Tag> findAll(Integer page, Integer count) {
        List<Tag> content = tagDao.findAll(PageRequest.of(page, count, Sort.Direction.DESC, "id")).getContent();
        return content;
    }

    @Override
    public Tag saveOne(Tag tag) {
        return tagDao.save(tag);
    }

    @Override
    public Optional<Tag> findById(Long id) {
        return tagDao.findById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        try {
            tagDao.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public ResponseBean updateTags(RequestBean requestBean) {
        int sum = 0;
        int successNumber = 0;
        for (RequestItem items : requestBean.getItems()) {
            // 获取指定属性的所有标签
            List<Tag> tagList = this.findByArrtribute(TableConstant.TABLE_TAGS, items.getQuery(), items.getQueryString(), Tag.class);
            // 一般为1个
            for (Tag itemTag : tagList) {
                // 等待更新
                if (itemTag.getUpdateStatus() == 0) {
                    Router router = itemTag.getRouter();
                    InetSocketAddress tagAddress = new InetSocketAddress(router.getIp(), router.getPort());
                   // tagAddress = new InetSocketAddress("192.168.1.30", 49884);
                    Channel channel = SpringContextUtil.getChannelIdGroup().get(tagAddress);
                    try {
                        // 发送更新命令(获取标签样式分区域发送)
                        // 先发外部
                        // 在发文字
                        byte[] message = new byte[2];
                        message[0] = 0x02;
                        message[1] = 0x03;
                        // 更新完毕 判断是否超时
                        // 判断是否成功
                        String result = nettyUtil.sendMessage(channel, message);
                        System.out.println("响应结果："+result);
                        if ("成功".equals(result))
                            successNumber++;
                        sum++;
                        itemTag.setUpdateStatus(1);
                        log.info("目标路由器：" + tagAddress + "的标签" + itemTag.getBarCode() + "更新完毕");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return new ResponseBean(sum, successNumber);
    }

    @Override
    public ResponseBean updateTagsByRouter(RequestBean requestBean) {
        int sum = 0;
        int successNumber = 0;
        for (RequestItem items : requestBean.getItems()) {

        }
        return new ResponseBean(sum, successNumber);
    }

    @Override
    public ResponseBean updateTagsByCycle(RequestBean requestBean) {
        int sum = 0;
        int successNumber = 0;
        for (RequestItem items : requestBean.getItems()) {

        }
        return new ResponseBean(sum, successNumber);
    }

    @Override
    public ResponseBean scanTags(RequestBean requestBean) {
        int sum = 0;
        int successNumber = 0;
        for (RequestItem items : requestBean.getItems()) {

        }
        return new ResponseBean(sum, successNumber);
    }

    @Override
    public ResponseBean scanTagsByRouter(RequestBean requestBean) {
        int sum = 0;
        int successNumber = 0;
        for (RequestItem items : requestBean.getItems()) {

        }
        return new ResponseBean(sum, successNumber);
    }

    @Override
    public ResponseBean scanTagsByCycle(RequestBean requestBean) {
        int sum = 0;
        int successNumber = 0;
        for (RequestItem items : requestBean.getItems()) {

        }
        return new ResponseBean(sum, successNumber);
    }

    @Override
    public ResponseBean changeStatus(RequestBean requestBean, Integer mode) {
        int sum = 0;
        int successNumber = 0;
        Tag tag = null;
        for (RequestItem items : requestBean.getItems()) {
            // 获取指定属性的所有标签
            List<Tag> tagList = this.findByArrtribute(TableConstant.TABLE_TAGS, items.getQuery(), items.getQueryString(), Tag.class);
            for (Tag itemTag : tagList) {
                itemTag.setForbidState(mode);
                try {
                     tag = saveOne(itemTag);
                }
                catch (Exception e){
                    log.info("对标签执行更新操作出错");
                }
                if(tag!=null)
                    successNumber++;
                sum++;
            }
        }
        return new ResponseBean(sum, successNumber);
    }

    @Override
    public ResponseBean changeLightStatus(RequestBean requestBean, Integer mode) {
        int sum = 0;
        int successNumber = 0;
       if(mode == 0){
           log.info("向指定的信息集合发送结束闪灯命令");
       }
       else if(mode == 1){
           log.info("向指定的信息集合发送闪灯命令");
       }
        return new ResponseBean(sum, successNumber);
    }
}
