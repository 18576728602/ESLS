package com.datagroup.ESLS.utils;

import java.io.File;
import java.io.FileOutputStream;

public class FileUtil {
    // 存储文件
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    // 根据文件名删除文件
    public static boolean deleteFile(String filePath, String fileName) {
        boolean flag = false;
        File file = new File(filePath + fileName);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
    // 判断文件是否存在
    public static boolean judeFileExists(String filePath, String fileName) {
        File file = new File(filePath + fileName);
        if (file.exists())
            return true;
        return false;
    }
}
