package com.whpu.house.service;

import com.whpu.house.mapper.CertificateMapper;
import com.whpu.house.pojo.Certificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
