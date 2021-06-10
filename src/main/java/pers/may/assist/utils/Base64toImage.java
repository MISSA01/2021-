package pers.may.assist.utils;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Bean;
import sun.misc.BASE64Decoder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Base64toImage {

    //处理Base64
    public String fixBase64Code(String originCode){
        //前台在用Ajax传base64值的时候会把base64中的+换成空格，所以需要替换回来。
        String baseValue = originCode.replaceAll(" ", "+");
        //去除base64值开头的声明
        baseValue = baseValue.replace("data:image/jpeg;base64,", "");
        baseValue = baseValue.replace("data:image/png;base64,", "");
        return baseValue;
    }

    //生成图片
    public boolean generateImage(String imgStr, String path) {
        if (imgStr == null)
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // 解密
            byte[] b = decoder.decodeBuffer(imgStr);
            // 处理数据
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            OutputStream out = new FileOutputStream(path);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public File getImgDirFile(String dir) {

//        ApplicationHome h = new ApplicationHome(getClass());
//        File jarF = h.getDir();
//        //System.out.println(jarF.getParentFile().toString());
//        System.out.println(System.getProperty("user.dir"));
//        // 构建上传文件的存放 "文件夹" 路径
//        String fileDirPath = new String(jarF.getParentFile().toString()+"\\img\\" + dir);\
        File projectFile = new File(System.getProperty("user.dir"));
        //String fileDirPath = projectFile.toString() +"\\img\\"+ dir; //windows
        String fileDirPath = projectFile.toString() +"/img/"+ dir; //linux
        File fileDir = new File(fileDirPath);
        if (!fileDir.exists()) {
            // 递归生成文件夹
            fileDir.mkdirs();
        }
        return fileDir;
    }

    public File getDocsDirFile(String dir) {

//        ApplicationHome h = new ApplicationHome(getClass());
//        File jarF = h.getDir();
//        //System.out.println(jarF.getParentFile().toString());
//        System.out.println(System.getProperty("user.dir"));
//        // 构建上传文件的存放 "文件夹" 路径
//        String fileDirPath = new String(jarF.getParentFile().toString()+"\\img\\" + dir);\
        File projectFile = new File(System.getProperty("user.dir"));
        //String fileDirPath = projectFile.toString() +"\\img\\"+ dir; //windows
        String fileDirPath = projectFile.toString() +"/docs/"+ dir; //linux
        File fileDir = new File(fileDirPath);
        if (!fileDir.exists()) {
            // 递归生成文件夹
            fileDir.mkdirs();
        }
        return fileDir;
    }


}
