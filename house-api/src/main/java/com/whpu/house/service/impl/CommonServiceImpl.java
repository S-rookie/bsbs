package com.whpu.house.service.impl;

import com.whpu.house.mapper.CertificateMapper;
import com.whpu.house.pojo.Certificate;
import com.whpu.house.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    CertificateMapper certificateMapper;
    @Override
    public int saveFileToBytes(Certificate certificate) {
        return certificateMapper.saveCertificate(certificate);
    }
}
