package com.cqyb.wechat.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Auther: yanbiao
 * @Date: 2019/10/22 16:23
 * @Description:
 */
public class FileUtil {
    public static File multipartFileToFile(MultipartFile file) throws Exception{

        File toFile =null;

        if(file.equals("")||file.getSize()<=0){

            file =null;

        }else {

            InputStream ins =null;

            ins = file.getInputStream();

            toFile =new File(file.getOriginalFilename());

            inputStreamToFile(ins, toFile);

            ins.close();

        }

        return toFile;

    }

    public static void inputStreamToFile(InputStream ins, File file) {

        try {

            OutputStream os =new FileOutputStream(file);

            int bytesRead =0;

            byte[] buffer =new byte[8192];

            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {

                os.write(buffer, 0, bytesRead);

            }

            os.close();

            ins.close();

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
    /**
     * 判断文件大小处于限制内
     *
     * @param fileLen 文件长度
     * @param fileSize 限制大小
     * @param fileUnit 限制的单位（B,K,M,G）
     * @return
     */
    public static boolean checkFileSize(Long fileLen, int fileSize, String fileUnit) {
        double fileSizeCom = 0;
        if ("B".equals(fileUnit.toUpperCase())) {
            fileSizeCom = (double) fileLen;
        } else if ("K".equals(fileUnit.toUpperCase())) {
            fileSizeCom = (double) fileLen / 1024;
        } else if ("M".equals(fileUnit.toUpperCase())) {
            fileSizeCom = (double) fileLen / (1024*1024);
        } else if ("G".equals(fileUnit.toUpperCase())) {
            fileSizeCom = (double) fileLen / (1024*1024*1024);
        }
        if (fileSizeCom > fileSize) {
            return false;
        }
        return true;

    }

}
