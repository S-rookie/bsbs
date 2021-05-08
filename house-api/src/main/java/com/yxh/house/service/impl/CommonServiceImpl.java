package com.yxh.house.service.impl;

import com.yxh.house.mapper.CertificateMapper;
import com.yxh.house.pojo.Certificate;
import com.yxh.house.service.CommonService;
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
