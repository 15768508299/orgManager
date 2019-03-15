package com.gk.cbl.util;

import org.apache.shiro.codec.Base64;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class Compent {
    //存储文件
    public static String stroeFile(HttpServletRequest request, CommonsMultipartFile file)throws IllegalStateException, IOException {
        //封装下filename,去除中文影响
        String filename[] = file.getOriginalFilename().split("\\.");
        filename[0] = Base64.encodeToString(filename[0].getBytes());
        filename[0] = filename[0].substring(0,filename[0].length()-2);
        String path= request.getServletContext().getRealPath("/")+"aupload/"+new Date().getTime()+filename[0]+"."+filename[1];
        File newFile=new File(path);
        file.transferTo(newFile);
        String tempPath = path.substring(path.indexOf("aupload"));
        return tempPath;
    }
}
