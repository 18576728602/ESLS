package com.datagroup.ESLS.common.constant;

import java.util.HashMap;
import java.util.Map;

public class FileConstant {
    public static String GOODS_CSV = "goods_csv/";
    public static String GOODS_PHOTO_CSV= "goods_photo/";
    public static String GOODS_CHANGE_CSV= "goods_change_csv/";
    public static String TAGS_CSV= "tags_csv/";
    public static String ROUTERS_CSV= "routers_csv/";
    public static Map ModeMap = new HashMap<Integer,String>();
    static {
        ModeMap.put(0,GOODS_CSV);
        ModeMap.put(1,GOODS_CHANGE_CSV);
        ModeMap.put(2,TAGS_CSV);
        ModeMap.put(3,ROUTERS_CSV);
        ModeMap.put(4,GOODS_PHOTO_CSV);
    }
}
