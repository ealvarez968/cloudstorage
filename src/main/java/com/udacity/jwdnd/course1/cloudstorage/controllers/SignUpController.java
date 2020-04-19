package com.udacity.jwdnd.course1.cloudstorage.controllers;


import com.udacity.jwdnd.course1.cloudstorage.config.Constant;
import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class SignUpController {

    @Autowired
    UsersMapper usersMapper;

    @Autowired
    RolesMapper rolesMapper;

    private EncryptionService es;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping("/signup")
    public ModelAndView getHome(@ModelAttribute("alertClass") String alertClass, @ModelAttribute("msg") String msg){
        ModelAndView mav = new ModelAndView();
        mav.setViewName("signup");

        mav.addObject("user", new Users());

        return mav;
    }


    @PostMapping("/signup")
    public RedirectView validate(@ModelAttribute Users user, RedirectAttributes attributes) {

        es = new EncryptionService();
        if(user == null
                || user.getLastname() == null
                || user.getFirstname() == null
                || user.getPassword() == null
                || user.getUsername() == null
                || user.getPassword().trim().length() == 0
                || user.getFirstname().trim().length() == 0
                || user.getUsername().trim().length() == 0
                || user.getLastname().trim().length() == 0){


            attributes.addFlashAttribute("alertClass", "danger");
            attributes.addFlashAttribute("msg", "An Error ocurred while creating your user, please check the form again.");
            return new RedirectView("/signup");

        }


        Users newUser = new Users();
        final String KEY = es.generateRandomSpecialCharacters(Constant.KEY_LENGTH);
        bCryptPasswordEncoder=  new BCryptPasswordEncoder();
        bCryptPasswordEncoder.encode(user.getPassword());

        Users exist = usersMapper.getUserByUsername(user.getUsername());
        if(exist != null){
            attributes.addFlashAttribute("alertClass", "danger");
            attributes.addFlashAttribute("msg", "The username already exists.");
            return new RedirectView("/signup");

        }
        newUser.setFirstname(user.getFirstname());
        newUser.setLastname(user.getLastname());
        newUser.setSalt(KEY);
        newUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        newUser.setUsername(user.getUsername());
        usersMapper.insertUser(newUser);

        Users inserted = usersMapper.getUserByUsername(user.getUsername());
        rolesMapper.insertRole("ADMIN", inserted.getUserid());
        for(Users u : usersMapper.getUsers()){
            System.out.println("username "+u.getUsername()+ " Password: "+u.getPassword());

        }

        attributes.addFlashAttribute("alertClass", "success");







        return new RedirectView("/signup");

    }


}
