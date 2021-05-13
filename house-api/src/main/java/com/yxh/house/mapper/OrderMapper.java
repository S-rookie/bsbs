package com.yxh.house.mapper;

import com.yxh.house.pojo.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {
	int insertOrder(Order order);

	int updateOrder(Order order);

	/**
	 * 查合同
	 * @param user_id
	 * @param owen_id
	 * @param close
	 * @param define
	 * @param house_id
	 * @param nick_name
	 * @param owen_name
	 * @return
	 */
	List<Map<Object, Object>> selectOrder(
			@Param("user_id") Integer user_id,
			@Param("owen_id") Integer owen_id,
			@Param("close") Integer close,
			@Param("define") Integer define,
			@Param("house_id")Integer house_id,
			@Param("nick_name")String nick_name,
	        @Param("owen_name")String owen_name);



	@Select("select * from `order` where id = #{id}")
	List<Order> selectOrderById(@Param("id") Integer id);
}
