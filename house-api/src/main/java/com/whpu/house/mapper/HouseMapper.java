package com.whpu.house.mapper;

import com.whpu.house.pojo.House;
import com.whpu.house.pojo.HouseAddr;
import com.whpu.house.pojo.HousePic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
     * sd
     * @param id
     * @param status
     * @return
     */
    @Update("update house set status = 1 where id = #{house_id} and status = #{status}")
    int updateHouseById(@Param("house_id") Integer id, @Param("status") String status);
}
