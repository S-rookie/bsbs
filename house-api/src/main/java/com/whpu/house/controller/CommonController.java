package com.whpu.house.controller;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.whpu.house.common.Response;
import com.whpu.house.pojo.Certificate;
import com.whpu.house.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
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
        String author_role = request.getParameter("author_role");
        String house_or_order_id = request.getParameter("house_or_order_id");

        Certificate certificate = new Certificate();
        if (!StrUtil.isEmpty(userId)){
            certificate.setUser_id(Integer.parseInt(userId));
        }
        if (!StrUtil.isEmpty(houseId)){
            certificate.setHouse_id(Integer.parseInt(houseId));
        }
        if (!StrUtil.isEmpty(certificate_type)){
            certificate.setCertificate_type(certificate_type);
        }
        if (!StrUtil.isEmpty(author_role)){
            certificate.setAuthor_role(author_role);
        }
        if (!StrUtil.isEmpty(house_or_order_id)){
            certificate.setHouse_or_order_id(Integer.parseInt(house_or_order_id));
        }

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
