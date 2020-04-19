package com.udacity.jwdnd.course1.cloudstorage.model;


import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

@Mapper
public interface NotesMapper {

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteid} and userid = #{userid}")
    Notes getNote(int noteid, int userid);

    @Select("SELECT * FROM NOTES WHERE userid = #{userid}")
    ArrayList<Notes> getNotesByUserid(int userid);

    @Update("UPDATE NOTES SET notetitle=#{notetitle} , notedescription=#{notedescription} WHERE noteid =#{noteid} and userid= #{userid}")
    void updateNote(String notetitle, String notedescription, int noteid, int userid);

    @Insert("INSERT INTO NOTES ( notetitle, notedescription, userid) values (#{notetitle}, #{notedescription}, #{userid})")
    void insertNote(Notes notes);


    @Delete("DELETE FROM NOTES WHERE noteid =#{noteid} and userid =#{userid}")
    void deleteNote(int noteid, int userid);
}
