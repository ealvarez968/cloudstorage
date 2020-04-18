package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.model.Roles;
import com.udacity.jwdnd.course1.cloudstorage.model.Users;
import com.udacity.jwdnd.course1.cloudstorage.model.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AuthenticationService implements UserDetailsService {

    @Autowired
    UsersMapper usersMapper;

    private BCryptPasswordEncoder bCryptPasswordEncoder;




    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = usersMapper.getUserByUsername(username);
        if(user == null){
            return null;
        }
        //bCryptPasswordEncoder=  new BCryptPasswordEncoder();
        //bCryptPasswordEncoder.encode(user.getPassword());
        List<GrantedAuthority> authorities = getUserAuthority(usersMapper.getRolesByUserid(user.getUserid()));

        return buildUserForAuthentication(user, authorities);

    }

    private List<GrantedAuthority> getUserAuthority(ArrayList<Roles> userRoles) {
        //Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        Set<GrantedAuthority> roles = new HashSet<>();
        for (Roles role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getName()));
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

    private UserDetails buildUserForAuthentication(Users user, List<GrantedAuthority> authorities) {
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                true, true, true, true, authorities);
    }
}
