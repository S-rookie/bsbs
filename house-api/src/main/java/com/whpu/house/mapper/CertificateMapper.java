package com.whpu.house.mapper;

import com.whpu.house.pojo.Certificate;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CertificateMapper {


    /**
     *  插入上传的申请
     * @param certificate
     * @return
     */
    @Insert("insert into certificate (user_id,house_id,url,create_time,certificate_type,author_role,house_or_order_id) values (#{cer.user_id},#{cer.house_id},#{cer.url},#{cer.create_time},#{cer.certificate_type},#{cer.author_role},#{cer.house_or_order_id})")
    public int saveCertificate(@Param("cer") Certificate certificate);

    /**
     * 合同
     * @param certificate
     * @return
     */
    @Select("select * from certificate where house_id = #{cer.house_id} and certificate_type = #{cer.certificate_type}")
    public List<Certificate> selectContractByHouseId(@Param("cer") Certificate certificate);

    List<Certificate> selectCertificateByOrder(@Param("cer") Certificate certificate);
}
