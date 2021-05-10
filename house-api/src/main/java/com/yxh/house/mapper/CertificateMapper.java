package com.yxh.house.mapper;

import com.yxh.house.pojo.Certificate;
import com.yxh.house.service.HouseService;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CertificateMapper {


    /**
     *  插入上传的申请
     * @param certificate
     * @return
     */
    @Insert("insert into certificate (user_id,house_id,url,create_time,certificate_type) values (#{cer.user_id},#{cer.house_id},#{cer.url},#{cer.create_time},#{cer.certificate_type})")
    public int saveCertificate(@Param("cer") Certificate certificate);

    /**
     * 合同
     * @param certificate
     * @return
     */
    @Select("select * from certificate where house_id = #{cer.house_id} and certificate_type = #{cer.certificate_type}")
    public Certificate selectContractByHouseId(@Param("cer") Certificate certificate);
}
