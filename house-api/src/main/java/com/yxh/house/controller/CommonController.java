package com.yxh.house.controller;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yxh.house.common.Response;
import com.yxh.house.pojo.Certificate;
import com.yxh.house.pojo.User;
import com.yxh.house.service.CommonService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

@RestController
@RequestMapping("common")
public class CommonController {

    @Autowired
    CommonService commonService;

    /**
     * 凭证文件上传
     * @param request
     * @return
     */
    @RequestMapping("/upload")
    public Response upload(HttpServletRequest request){
        String userId = request.getParameter("user_id");
        String houseId = request.getParameter("house_id");
        String certificate_type = request.getParameter("certificate_type");
        Certificate certificate = new Certificate();
        certificate.setHouse_id(Integer.parseInt(houseId));
        certificate.setUser_id(Integer.parseInt(userId));
        certificate.setCertificate_type(certificate_type);

        MultipartHttpServletRequest mr = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = mr.getFileMap();
        fileMap.forEach((k,v)->{
            try {
                byte[] bytes = v.getBytes();
                String toHex = Convert.toHex(bytes);
                certificate.setUrl(toHex);
                certificate.setCreate_time(new Date());
                commonService.saveFileToBytes(certificate);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return Response.Success(1);
    }
}
