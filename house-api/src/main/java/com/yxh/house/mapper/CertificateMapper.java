package com.yxh.house.mapper;

import com.yxh.house.pojo.Certificate;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CertificateMapper {


    /**
     *  插入上传的申请
     * @param certificate
     * @return
     */
    @Insert("insert into certificate (user_id,house_id,url,create_time) values (#{cer.user_id},#{cer.house_id},#{cer.url},#{cer.create_time})")
    public int saveCertificate(@Param("cer") Certificate certificate);
}
