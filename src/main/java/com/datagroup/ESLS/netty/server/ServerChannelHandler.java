package com.datagroup.ESLS.netty.server;

import com.datagroup.ESLS.common.constant.TableConstant;
import com.datagroup.ESLS.common.request.RequestBean;
import com.datagroup.ESLS.common.request.RequestItem;
import com.datagroup.ESLS.netty.command.CommandCategory;
import com.datagroup.ESLS.netty.command.CommandConstant;
import com.datagroup.ESLS.netty.executor.AsyncTask;
import com.datagroup.ESLS.service.Service;
import com.datagroup.ESLS.utils.ByteUtil;
import com.datagroup.ESLS.utils.SpringContextUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.FailureCallback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SuccessCallback;

import java.net.InetSocketAddress;

@Slf4j
public class ServerChannelHandler extends SimpleChannelInboundHandler<Object> {
    private ChannelPromise promise;
    private String data;

    /**
     * 连接上服务器
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        log.info("服务端客户加入连接====>" + ctx.channel().toString());
        SpringContextUtil.getChannelGroup().add(ctx.channel());
        SpringContextUtil.getChannelIdGroup().put(ctx.channel().remoteAddress(), ctx.channel());
        InetSocketAddress socketAddress = (InetSocketAddress) ctx.channel().remoteAddress();
        // 更改路由器端口号
        RequestBean source = new RequestBean();
        RequestItem itemSource = new RequestItem("ip", socketAddress.getAddress().getHostAddress());
        source.getItems().add(itemSource);
        RequestBean target = new RequestBean();
        RequestItem itemTarget = new RequestItem("port", String.valueOf(socketAddress.getPort()));
        target.getItems().add(itemTarget);
        // 更新记录数
        Integer result = ((Service) SpringContextUtil.getBean("BaseService")).updateByArrtribute(TableConstant.TABLE_ROUTERS, source, target);
        if (result > 0)
            log.info(new StringBuffer("路由器：").append(ctx.channel().toString()).append("更新端口成功").toString());
        // System.out.println(result);
    }

    /**
     * 断开连接
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        log.info("服务端客户移除连接====>" + ctx.channel().remoteAddress());
    }

    /**
     * 连接异常   需要关闭相关资源
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("【系统异常】======>" + cause.toString());
    }

    /**
     * 活跃的通道  也可以当作用户连接上客户端进行使用
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.info((new StringBuilder("NettyServerHandler::活跃 remoteAddress=")).append(ctx.channel().remoteAddress()).toString());
        // 全局对象
    }

    /**
     * 不活跃的通道  就说明用户失去连接
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (SpringContextUtil.getChannelGroup().contains(ctx.channel())) {
            log.info((new StringBuilder("服务端客户移除连接 IP地址: ")).append(ctx.channel().remoteAddress()).toString());
            SpringContextUtil.getChannelGroup().remove(ctx.channel());
        }
    }

    /**
     * 这里只要完成 flush
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * 这里是保持服务器与客户端长连接  进行心跳检测 避免连接断开
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    // 超时处理
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent stateEvent = (IdleStateEvent) evt;
            switch (stateEvent.state()) {
                //读空闲（服务器端）
                case READER_IDLE:
                    log.info("【" + ctx.channel().remoteAddress() + "】读空闲（服务器端）");
                    channelInactive(ctx);
                    Thread.sleep(1000L);
                    break;
                //写空闲（客户端）
                case WRITER_IDLE:
                    log.info("【" + ctx.channel().remoteAddress() + "】写空闲（客户端）");
                    break;
                case ALL_IDLE:
                    log.info("【" + ctx.channel().remoteAddress() + "】读写空闲");
                    break;
                default:
                    break;
            }
        }
    }

    // 接受消息
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        //Message message = (Message)msg;
        //recvExcutor.addTask((new StringBuilder("Handler")).append(message.getHeader().getCOMMAND()).toString(), new MWork(message, ctx.channel()));
        ByteBuf in = (ByteBuf) msg;
        byte[] req = new byte[in.readableBytes()];
        in.readBytes(req);
        if(req.length<3) return;
        byte[] header = new byte[2];
        header[0] = req[0];
        header[1] = req[1];
        // ACK
        if (CommandConstant.ACK.equals(CommandCategory.getCommandCategory(header))) {
            this.data = "成功";
            this.promise.setSuccess();
        }
        // NACK
        else if (CommandConstant.NACK.equals(CommandCategory.getCommandCategory(header))) {
            this.data = "失败";
            this.promise.setSuccess();
        }
        // 通讯超时
        else if (CommandConstant.OVERTIME.equals(CommandCategory.getCommandCategory(header))) {
            this.data = "通讯超时";
            this.promise.setSuccess();
        }
        // 正常命令
        else {
            String handler = "handler" + header[0] + "" + header[1];
            ListenableFuture<String> result = ((AsyncTask) SpringContextUtil.getBean("AsyncTask")).execute(handler,ctx.channel(), header,ByteUtil.splitByte(req,3,req[2]));
  /*          SuccessCallback<String> successCallback = str -> {
                System.out.println("服务端异步回调成功了, return : " + str);
            };
            FailureCallback failureCallback = throwable -> System.out.println("异步回调失败了, exception message : " + throwable.getMessage());
            result.addCallback(successCallback, failureCallback);*/
        }
    }

    // 主动发送命令
    public ChannelPromise sendMessage(Channel channel, byte[] message) {
        if (channel == null)
            throw new IllegalStateException();
        this.promise = channel.writeAndFlush(Unpooled.wrappedBuffer(message)).channel().newPromise();
        return this.promise;
    }

    public String getData() {
        return data;
    }
}