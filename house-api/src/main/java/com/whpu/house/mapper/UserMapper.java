package com.whpu.house.mapper;

import com.whpu.house.pojo.Role;
import com.whpu.house.pojo.User;
import com.whpu.house.pojo.UserPic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface UserMapper{

    int insertUserPic(UserPic housePic);
    int updateUserPic(UserPic housePic);
    List<UserPic> selectUserPic(@Param("user_id") Integer user_id);

    int insertUser(User user);
    int updateUser(User user);
    List<User> selectUsers(User user);
    User selectUserByName(String username);

    int insertRole(Role role);
    int updateRole(Role role);
    List<Role> selectRoles(Role role);

    int addUserRole(@Param("user") User user, @Param("role") Role role);
    int updateUserRole(@Param("user") User user, @Param("role") Role role);
}
