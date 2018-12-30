package com.code.controller;

import com.code.utils.QiniuUtil;
import com.code.utils.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("/file")
public class FileUploadController {

    @Autowired
    private QiniuUtil uploadUtil;

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        return "upload/index";
    }

    // 图片上传
    @RequestMapping("/upload")
    @ResponseBody
    public ResultMap upload(@RequestParam("file") MultipartFile multipartFile, HttpSession session) throws Exception {
        if (!StringUtils.isEmpty(multipartFile) && multipartFile.getSize() > 0) {
            String filename = multipartFile.getOriginalFilename();
            String suffix = filename.substring(filename.lastIndexOf(".") + 1);
            if (filename.endsWith("jpg")||filename.endsWith("png")||filename.endsWith("jpeg")){
                //若上传路径文件夹不存在，创建文件夹
                File tmpUpload = new File(session.getServletContext().getRealPath("/")+"/image/");//缓存目录
                if(!tmpUpload.exists()) tmpUpload.mkdirs();
                String realPath = session.getServletContext().getRealPath("/")+"/image/"+new Date().getTime()+"."+suffix;
                File newfile = new File(realPath);
                try {
                    multipartFile.transferTo(newfile);
                    String imageName = uploadUtil.upload(newfile);
                    newfile.delete();//删除缓存文件
                    return ResultMap.okData(imageName);
                } catch (IOException e) {
                    e.printStackTrace();
                    return ResultMap.fail(1,"文件上传异常");
                }
            }else {
                return ResultMap.fail(2,"不支持该上传类型");
            }
        } else {
            return ResultMap.fail(3, "文件为空");
        }
    }
}