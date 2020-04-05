package com.udacity.jwdnd.course1.cloudstorage.model;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface RolesMapper {

    @Select("SELECT * FROM ROLES WHERE userid = #{userid}")
    ArrayList<Roles> getRoles(@Param("userid") int userid);
}