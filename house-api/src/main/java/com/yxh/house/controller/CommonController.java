package com.yxh.house.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yxh.house.common.Response;
import com.yxh.house.pojo.User;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("common")
public class CommonController {

    @RequestMapping("/upload")
    public Response upload(HttpServletRequest request){
        String user_info = request.getParameter("user_info");
        String s = JSON.toJSONString(user_info);
        MultipartHttpServletRequest mr = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = mr.getFileMap();
        fileMap.forEach((k,v)->{
            try {
                byte[] bytes = v.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return null;
    }
}
