package com.udacity.jwdnd.course1.cloudstorage.model;

import org.apache.ibatis.annotations.*;

import java.util.ArrayList;


@Mapper
public interface CredentialsMapper {

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid} and userid = #{userid}")
    Credentials getCredential( int credentialid, int userid);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userid}")
    ArrayList<Credentials> getCredentialsByUserid(int userid);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid =#{credentialid} and  userid =#{userid}")
    void deleteCredential(int credentialid, int userid);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) values (#{url}, #{username}, #{key}, #{password},#{userid})")
    void insertNote(Credentials credentials);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username} , password=#{password} WHERE credentialid =#{credentialid} and userid =#{userid}")
    void updateCredential(String url, String username, String password, int credentialid, int userid);

}
