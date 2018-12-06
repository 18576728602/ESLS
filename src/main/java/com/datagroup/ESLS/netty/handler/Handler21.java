package com.datagroup.ESLS.netty.handler;

import com.datagroup.ESLS.dao.AdminDao;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("handler21")
@Slf4j
public class Handler21 implements ServiceHandler{

    @Autowired
    AdminDao adminDao;

    @Override
    @Transactional
    public byte[]  execute(byte[] header, byte[] message){
        //adminDao.updateOne("吴东阳",1);
        log.info("标签注册-----处理器执行！");
        return message;
    }
    @Override
    public String execute(String message, Channel channel) {
        return null;
    }
}
