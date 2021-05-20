package com.whpu.house.controller;

import cn.hutool.core.convert.Convert;
import com.whpu.house.service.CertificateService;
import com.whpu.house.common.Groups;
import com.whpu.house.common.Response;
import com.whpu.house.pojo.Certificate;
import com.whpu.house.pojo.Order;
import com.whpu.house.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
                int i = Integer.parseInt(certificates.get(0).getAuthor_role());
                if (i == 0) {
                    o.setStatus(1100);
                } else {
                    o.setStatus(2100);
                }
                return Response.Success(orderService.updateOrder(o));
            }else if (certificates.size() == 2){
                o.setStatus(1);
                return Response.Success(orderService.updateOrder(o));
            }
        }
        // 签订
        if (order.isFinalSign()){
            List<Order> orders = orderService.searchOrderById(order.getId());
            Order res = orders.get(0);
            Order o = new Order();
            o.setId(res.getId());
            if (res.getStatus() == 1900 || res.getStatus() == 2900){
                o.setStatus(999);
            }else if (res.getStatus() == 1){
                // 如果不是说明第一个
                if (Integer.parseInt(res.getEditRole()) == 0) {
                    // 租客
                    o.setStatus(1900);
                }else {
                    o.setStatus(2900);
                }
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
            Integer house_id,
            String nick_name,
            String owen_name,
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,HttpServletRequest request){
        Map<String, String[]> parameterMap = request.getParameterMap();
        return Response.Success(orderService.getOrder(user_id,owen_id,close,define,pageNum,pageSize,house_id,nick_name,owen_name));
    }

    @RequestMapping("showContract")
    public void searchContract(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String houseId = request.getParameter("house_id");
        String cerType = request.getParameter("certificate_type");
        Certificate certificate = new Certificate();
        certificate.setHouse_id(Integer.parseInt(houseId));
        certificate.setCertificate_type(cerType);
        List<Certificate> certificates = orderService.searchContract(certificate);
        List<byte[]> bytesList = new ArrayList<>();
        certificates.forEach((c)->{
            byte[] bytes = Convert.hexToBytes(c.getUrl());
            bytesList.add(bytes);
        } );
        byte[] cerBytes = Convert.hexToBytes(certificates.get(0).getUrl());
        OutputStream baos = new ByteArrayOutputStream();
        // 转换成 FileOutputStream
//        FileCopyUtils.copy();
//        JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(baos));
        FileCopyUtils.copy(cerBytes,response.getOutputStream());
    }
}
