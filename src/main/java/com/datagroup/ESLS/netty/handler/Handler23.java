package com.datagroup.ESLS.netty.handler;

import com.datagroup.ESLS.entity.Router;
import com.datagroup.ESLS.netty.command.CommandCategory;
import com.datagroup.ESLS.netty.command.CommandConstant;
import com.datagroup.ESLS.service.RouterService;
import com.datagroup.ESLS.utils.ByteUtil;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.sql.Timestamp;

@Component("handler23")
@Slf4j
public class Handler23 implements ServiceHandler {
    @Autowired
    private RouterService routerService;

    @Override
    public byte[] execute(byte[] header,byte[] message) {
        log.info("路由器注册-----处理器执行！");
        // 测试命令
        // 02 03 25 11 11 11 11 11 11 11 11 11 11 11 11  11 11 11 11 11 11 11 11 11 11 11 11 11 01 01 11 11 11 11 11 11 11 11 11 fd
        try {
            String routerMac = ByteUtil.getRealMessage(ByteUtil.splitByte(message, 0, 6));
            String routerIP = ByteUtil.getRealMessage(ByteUtil.splitByte(message, 6, 4));
            String routerPort = ByteUtil.getRealMessage(ByteUtil.splitByte(message, 10, 2));
            String routerBarCode = ByteUtil.getRealMessage(ByteUtil.splitByte(message, 12, 12));
            String routerChannelId = ByteUtil.getRealMessage(ByteUtil.splitByte(message, 24, 1));
            String routerFrequency = ByteUtil.getRealMessage(ByteUtil.splitByte(message, 25, 2));
            String routerHardVersion = ByteUtil.getRealMessage(ByteUtil.splitByte(message, 27, 5));
            String routerSoftVersion = ByteUtil.getRealMessage(ByteUtil.splitByte(message, 32, 5));
            Router router = new Router();
            router.setMac(routerMac);
            router.setIp(routerIP);
            router.setPort(Integer.parseInt(routerPort));
            router.setBarCode(routerBarCode);
            router.setChannelId(routerChannelId);
            router.setSoftVersion(routerSoftVersion);
            router.setHardVersion(routerHardVersion);
            router.setFrequency(routerFrequency);
            router.setHeartBeat(new Timestamp(System.currentTimeMillis()));
            routerService.saveOne(router);
        }
        catch (Exception e){
            System.out.println(e);
        }
        return CommandCategory.getResponse(CommandConstant.ACK,header);
    }

    @Override
    public String execute(String message, Channel channel) {
        InetSocketAddress socketAddress = (InetSocketAddress) channel.remoteAddress();
        log.info(new StringBuilder().append("IP:" + socketAddress.getAddress()).append("------PORT:" + socketAddress.getPort()).toString());
        return "成功";
    }
}
