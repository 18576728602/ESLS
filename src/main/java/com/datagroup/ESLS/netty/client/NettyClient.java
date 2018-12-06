package com.datagroup.ESLS.netty.client;

import com.datagroup.ESLS.netty.server.ServerChannelHandler;
import com.datagroup.ESLS.utils.SpringContextUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.Callable;

@Slf4j
public class NettyClient implements Callable {

    private Bootstrap b;
    private ChannelFuture f;
    private final EventLoopGroup workerGroup = new NioEventLoopGroup();
    public MyClientHandler clientHandler;
    private Channel channel;
    private byte[] send;

    public NettyClient() {
    }

    public NettyClient(Channel channel, byte[] send) {
        this.channel = channel;
        this.send = send;
    }

    private void init() {
        try {
            clientHandler = new MyClientHandler();
            b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            //是否启用心跳保活机制。在双方TCP套接字建立连接后（即都进入ESTABLISHED状态）并且在两个小时左右上层没有任何数据传输的情况下，这套机制才会被激活。
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) {
                    socketChannel.pipeline().addLast(clientHandler);
                    socketChannel.pipeline().addLast(new StringEncoder());
                    socketChannel.pipeline().addLast(new StringDecoder());
                }
            });
            log.info("客户端启动成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String startAndWrite() throws InterruptedException {
//        init();
//        f = b.connect(this.address).sync();
//        Channel channel = f.channel();
//        // 等待连接激活
//        while (!channel.isActive()) {
//            Thread.sleep(1000);
//        }
        // 传数据给服务端

        ServerChannelHandler serverChannelHandler = SpringContextUtil.serverChannelHandler;
        ChannelPromise promise = serverChannelHandler.sendMessage(channel,send);
        promise.await();
       // f.channel().closeFuture().sync();
        return serverChannelHandler.getData();
    }

    @Override
    public Object call() throws Exception {
        return startAndWrite();
    }
}
