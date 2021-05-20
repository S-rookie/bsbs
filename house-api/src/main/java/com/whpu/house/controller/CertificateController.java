package com.whpu.house.controller;

import cn.hutool.core.util.StrUtil;
import com.whpu.house.common.Response;
import com.whpu.house.pojo.Certificate;
import com.whpu.house.service.CertificateService;
import com.whpu.house.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author eshore
 */
@RestController
@RequestMapping("certificate")
public class CertificateController {

    @Autowired
    OrderService orderService;

    @Autowired
    CertificateService certificateService;

    @RequestMapping("/searchCertificate")
    public Response searchCertificate(HttpServletRequest request){
        String houseId = request.getParameter("house_id");
        String cerType = request.getParameter("certificate_type");
        String author_role = request.getParameter("author_role");
        String house_or_order_id = request.getParameter("house_or_order_id");
        Certificate certificate = new Certificate();
        if (!StrUtil.isEmpty(houseId)){
            certificate.setHouse_id(Integer.parseInt(houseId));
        }
        if (!StrUtil.isEmpty(cerType)){
            certificate.setCertificate_type(cerType);
        }
        if (!StrUtil.isEmpty(author_role)){
            certificate.setAuthor_role(author_role);
        }
        if (!StrUtil.isEmpty(house_or_order_id)){
            certificate.setHouse_or_order_id(Integer.parseInt(house_or_order_id));
        }
        List<Certificate> certificates = certificateService.selectCertificateByOrder(certificate);
        if (certificates.size() != 0){
            return Response.Success(certificates);
        }
        return Response.Fail();
    }

}
