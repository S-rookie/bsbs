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
import com.yxh.house.service.CertificateService;
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
import java.util.List;


@RestController
@RequestMapping("order")
public class OrderController {
    private OrderService orderService;

    @Autowired
    CertificateService certificateService;

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
        // 上传合同
        if (order.isCheckSign()){
            Certificate certificate = new Certificate();
            certificate.setHouse_id(order.getHouse_id());
            certificate.setCertificate_type("222");
            List<Certificate> certificates = certificateService.searchCertificate(certificate);
            Order o = new Order();
            if (certificates.size() == 1){
                o.setStatus(100);
                return Response.Success(orderService.updateOrder(o));
            }else if (certificates.size() == 2){
                o.setStatus(1);
                return Response.Success(orderService.updateOrder(o));
            }
        }
        // 签订
        if (order.isFinalSign()){
            Order o = new Order();
            // 先查询状态是不是 900
            List<Order> orders = orderService.searchOrderById(order.getId());
            // 如果是900 直接改为 999
            if (orders.get(0).getStatus() == 900){
                o.setStatus(999);
            }else if (order.getStatus() == 1){
                // 如果不是说明第一个
                o.setStatus(900);
            }
            return Response.Success(orderService.updateOrder(o));
        }
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
        List<Certificate> certificates = orderService.searchContract(certificate);
        byte[] cerBytes = Convert.hexToBytes(certificates.get(0).getUrl());
        OutputStream baos = new ByteArrayOutputStream();
        // 转换成 FileOutputStream
//        FileCopyUtils.copy();
//        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(baos));
        FileCopyUtils.copy(cerBytes,response.getOutputStream());
    }
}
