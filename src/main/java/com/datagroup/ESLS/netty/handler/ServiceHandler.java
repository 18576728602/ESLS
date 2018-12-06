package com.datagroup.ESLS.netty.handler;

import io.netty.channel.Channel;

public interface ServiceHandler {
    byte[] execute(byte[] header,byte[] message);
    String execute(String message, Channel channel);
}
