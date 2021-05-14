package com.yxh.house.mapper;

import com.yxh.house.pojo.House;
import com.yxh.house.pojo.HouseAddr;
import com.yxh.house.pojo.HousePic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;


@Mapper
public interface HouseMapper {
    int insertAddr(HouseAddr houseAddr);
    List<HouseAddr> selectAddr();

    int insertHouse(House house);
    int updateHouse(House house);
    List<Map> selectHouseList(House house);

    int insertHousePic(HousePic housePic);
    int updateHousePic(HousePic housePic);
    List<HousePic> selectHousePic(@Param("house_id") Long house_id);

    int deleteHouseById(@Param("house_id") Integer id);

    /**
     * 审核
     * @param id
     * @return
     */
    @Update("update house set status = 1 where house_id = #{house_id}")
    int updateHouseById(@Param("house_id") Integer id);
}
