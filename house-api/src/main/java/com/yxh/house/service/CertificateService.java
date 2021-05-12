package com.yxh.house.service;

import com.yxh.house.mapper.CertificateMapper;
import com.yxh.house.pojo.Certificate;
import com.yxh.house.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author eshore
 */
@Service
public class CertificateService {

    @Autowired
    CertificateMapper certificateMapper;

    public List<Certificate> searchCertificate(Certificate certificate) {
        return certificateMapper.selectContractByHouseId(certificate);
    }

    public List<Certificate> selectCertificateByOrder(Certificate cer){
        List<Certificate> certificates = certificateMapper.selectCertificateByOrder(cer);
        return certificates;
    }
}
