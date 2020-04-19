package com.udacity.jwdnd.course1.cloudstorage.model;

import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface UsersMapper {


    @Select("SELECT * FROM USERS WHERE username = #{username}")
    Users getUserByUsername(String username);

    @Select("SELECT * FROM ROLES WHERE userid = #{userid}")
    ArrayList<Roles> getRolesByUserid(int userid);

    @Insert("INSERT INTO USERS ( username, salt, password, firstname, lastname) values (#{username}, #{salt}, #{password}, #{firstname}, #{lastname})")
    void insertUser(Users users);

    @Select("SELECT * FROM USERS ")
    ArrayList<Users> getUsers();

}
