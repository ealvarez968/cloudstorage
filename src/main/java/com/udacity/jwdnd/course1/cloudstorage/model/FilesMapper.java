package com.udacity.jwdnd.course1.cloudstorage.model;

import org.apache.ibatis.annotations.*;

import java.util.ArrayList;


@Mapper
public interface FilesMapper {

    @Select("SELECT * FROM FILES WHERE fileid = #{fileid} and  userid = #{userid}")
    Files getFile(@Param("fileid") int fileid, int userid);

    @Select("SELECT * FROM FILES WHERE filename = #{filename} and  userid = #{userid}")
    Files getFileByFilename(String filename, int userid);

    @Select("SELECT * FROM FILES WHERE  userid = #{userid}")
    ArrayList<Files> getFilesByUserId(int userid);

    @Update("UPDATE FILES SET filename=#{filename} WHERE fileid =#{fileid}")
    void updateFilename(String filename, int fileid);


    @Delete("DELETE FROM FILES WHERE fileid =#{fileid} and userid =#{userid}")
    void deleteFiles(int fileid, int userid);

    @Insert("INSERT INTO FILES (filename, contenttype, filedata, userid, filesize) values (#{filename}, #{contenttype}, #{filedata}, #{userid}, #{filesize})")
    void insertFile(Files file);



}
