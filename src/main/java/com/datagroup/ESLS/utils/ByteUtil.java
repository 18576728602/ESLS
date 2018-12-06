package com.datagroup.ESLS.utils;

public class ByteUtil {
    private static byte[] ACK = new byte[5];
    private static byte[] NACK = new byte[5];
    private static byte[] OVER_TIME = new byte[5];
    public static byte[] splitByte(byte[] request, int begin, int len) {
        /*        byte数组截取当然要提到效率非常高的arraycopy，java中调用方式如下：
        System.arraycopy(src, srcPos, dest, destPos, length/)
        参数解析：
        src：byte源数组
        srcPos：截取源byte数组起始位置（0位置有效）
        dest,：byte目的数组（截取后存放的数组）
        destPos：截取后存放的数组起始位置（0位置有效）
        length：截取的数据长度*/
        byte[] target = new byte[len];
        System.arraycopy(request, begin, target, 0, len);
        return target;
    }

    public static int sumByte(byte[] request) {
        int sum = 0;
        for (byte b : request)
            sum += b;
        return sum;
    }

    public static String getRealMessage(byte[] request) {
        StringBuffer sb = new StringBuffer();
        for (byte item : request) {
            sb.append(item);
        }
        return sb.toString();
    }

    public static byte[] getACK(byte _class,byte _id) {
        ByteUtil.ACK[0] = 0x01;
        ByteUtil.ACK[1] = 0x01;
        ByteUtil.ACK[2] = _class;
        ByteUtil.ACK[3] = _id;
        return ACK;
    }
    public static byte[] getNACK(byte _class,byte _id) {
        ByteUtil.NACK[0] = 0x01;
        ByteUtil.NACK[1] = 0x02;
        ByteUtil.NACK[2] = _class;
        ByteUtil.NACK[3] = _id;
        return NACK;
    }
    public static byte[] getOverTime(byte _class,byte _id) {
        ByteUtil.OVER_TIME[0] = 0x01;
        ByteUtil.OVER_TIME[1] = 0x03;
        ByteUtil.OVER_TIME[2] = _class;
        ByteUtil.OVER_TIME[3] = _id;
        return OVER_TIME;
    }

}
