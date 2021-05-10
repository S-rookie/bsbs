package com.yxh.house.controller;

import ch.qos.logback.core.rolling.helper.FileStoreUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.IoUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yxh.house.common.Groups;
import com.yxh.house.common.Response;
import com.yxh.house.pojo.Certificate;
import com.yxh.house.pojo.Order;
import com.yxh.house.service.OrderService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;


@RestController
@RequestMapping("order")
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }
    @RequestMapping("add")
    public Response add(Order order){
        return Response.Success(orderService.addOrder(order));
    }
    @RequestMapping("update")
    public Response update(@Validated(value = {Groups.Update.class}) Order order){
        return Response.Success(orderService.updateOrder(order));
    }
    @RequestMapping("list")
    public Response getUsers(
            Integer user_id,
            Integer owen_id,
            Integer close,
            Integer define,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize){
        return Response.Success(orderService.getOrder(user_id,owen_id,close,define,pageNum,pageSize));
    }

    @RequestMapping("showContract")
    public void searchContract(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String houseId = request.getParameter("house_id");
        String cerType = request.getParameter("certificate_type");
        Certificate certificate = new Certificate();
        certificate.setHouse_id(Integer.parseInt(houseId));
        certificate.setCertificate_type(cerType);
        Certificate cer = orderService.searchContract(certificate);
        byte[] cerBytes = Convert.hexToBytes(cer.getUrl());
        OutputStream baos = new ByteArrayOutputStream();
        // 转换成 FileOutputStream
//        FileCopyUtils.copy();
//        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(baos));
        FileCopyUtils.copy(cerBytes,response.getOutputStream());
    }
}
