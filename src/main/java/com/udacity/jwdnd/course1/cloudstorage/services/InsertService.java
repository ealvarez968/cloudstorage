package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.Roles;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.model.UsersMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class InsertService implements UsersMapper {
    /*@Override
    public Users getUser(int userid) {
        return null;
    }*/

    @Override
    public Users getUserByUsername(String username) {
        return null;
    }

    @Override
    public ArrayList<Roles> getRolesByUserid(int userid) {
        return null;
    }

    @Override
    public void insertUser(Users users) {

    }

    @Override
    public ArrayList<Users> getUsers() {
        return null;
    }

   /* @Override
    public void updateUsername(String username, int userid) {

    }

    @Override
    public void deleteUser(int userid) {

    }*/
}
