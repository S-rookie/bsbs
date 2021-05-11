package com.yxh.house.controller;

import com.yxh.house.common.Response;
import com.yxh.house.pojo.Certificate;
import com.yxh.house.service.CertificateService;
import com.yxh.house.service.OrderService;
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

    @RequestMapping("/searchCertificate")
    public Response searchCertificate(HttpServletRequest request){
        String houseId = request.getParameter("house_id");
        String cerType = request.getParameter("certificate_type");
        Certificate certificate = new Certificate();
        certificate.setHouse_id(Integer.parseInt(houseId));
        certificate.setCertificate_type(cerType);
        List<Certificate> certificates = orderService.searchContract(certificate);
        if (certificates.size() != 0){
            return Response.Success(certificates);
        }
        return Response.Fail();
    }

}
