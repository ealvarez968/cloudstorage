package com.udacity.jwdnd.course1.cloudstorage.controllers;


import com.udacity.jwdnd.course1.cloudstorage.config.Constant;
import com.udacity.jwdnd.course1.cloudstorage.model.*;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;

@RestController
@ContextConfiguration(locations = "classpath:/beans.xml")
public class HomeController {

    @Autowired
    private NotesMapper notesMapper;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private CredentialsMapper credentialsMapper;
    @Autowired
    private FilesMapper filesMapper;
    @Autowired
    private FileStorageService dbFileStorageService;

    private EncryptionService es;

    @PostMapping("/home/notes/add")
    public RedirectView notes(@ModelAttribute Notes note, RedirectAttributes attributes, @RequestParam(defaultValue="-1") int noteId) {
        String _sessionUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Users _user = usersMapper.getUserByUsername(_sessionUsername);


        if(_user == null){

            attributes.addFlashAttribute("alertClass", "danger");
            attributes.addFlashAttribute("msg", "Error on session ID.");
            return new RedirectView("/home");

        }

        if(note.getNotetitle() == null
                || note.getNotedescription() == null
                || note.getNotetitle().trim().length() == 0
                || note.getNotedescription().trim().length() == 0){

            attributes.addFlashAttribute("alertClass", "danger");
            attributes.addFlashAttribute("msg", "An Error ocurred while creating your note.");
            return new RedirectView("/home");

        }

        attributes.addFlashAttribute("alertClass", "success");


        if(noteId == -1){
            Notes n = new Notes();
            n.setUserid(_user.getUserid());
            n.setNotetitle(note.getNotetitle());
            n.setNotedescription(note.getNotedescription());

            notesMapper.insertNote(n);

            attributes.addFlashAttribute("msg", "Your note "+note.getNotetitle()+" has been added successfully");
            return new RedirectView("/home");

        }

        attributes.addFlashAttribute("msg", "Your note "+note.getNotetitle()+" has been updated successfully");

        notesMapper.updateNote(note.getNotetitle(),note.getNotedescription(),noteId, _user.getUserid());

        return new RedirectView("/home");

    }


    @PostMapping("/home/credential/add")
    public RedirectView credentials(@ModelAttribute Credentials credential, RedirectAttributes attributes, @RequestParam(defaultValue="-1") int credentialId) {
        String _sessionUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Users _user = usersMapper.getUserByUsername(_sessionUsername);

        if(_user == null){

            attributes.addFlashAttribute("alertClass", "danger");
            attributes.addFlashAttribute("msg", "Error on session ID.");
            return new RedirectView("/home");

        }

        if(credential.getPassword() == null
                || credential.getUrl() == null
                || credential.getUsername() == null
                || credential.getPassword().trim().length() == 0
                || credential.getUrl().trim().length() == 0
                || credential.getUsername().trim().length() == 0){

            attributes.addFlashAttribute("alertClass", "danger");
            attributes.addFlashAttribute("msg", "An Error ocurred while creating your credential.");
            return new RedirectView("/home");

        }
        attributes.addFlashAttribute("alertClass", "success");

        if(credentialId == -1){
            final String KEY = es.generateRandomSpecialCharacters(Constant.KEY_LENGTH);
            Credentials newCredential = new Credentials();

            newCredential.setUserid(_user.getUserid());
            newCredential.setKey(KEY);
            newCredential.setUrl(credential.getUrl());
            newCredential.setUsername(credential.getUsername());
            newCredential.setPassword( es.encryptValue(credential.getPassword(),KEY));

            credentialsMapper.insertNote(newCredential);

            attributes.addFlashAttribute("msg", "Your credential for "+newCredential.getUrl()+" has been added successfully");
            return new RedirectView("/home");

        }

        Credentials oldCredential = credentialsMapper.getCredential(credentialId, _user.getUserid());

        attributes.addFlashAttribute("msg", "Your credential for "+credential.getUrl()+" has been updated successfully");

        credentialsMapper.updateCredential(credential.getUrl(),credential.getUsername(),
                es.encryptValue(credential.getPassword(), oldCredential.getKey()),credentialId,_user.getUserid());

        return new RedirectView("/home");
    }

    @GetMapping("/home/notes/delete/{noteid}")
    public RedirectView deleteNote( @PathVariable("noteid") int noteid,  RedirectAttributes attributes){
        String _sessionUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Users _user = usersMapper.getUserByUsername(_sessionUsername);

        Notes n = notesMapper.getNote(noteid, _user.getUserid());

        if(_user == null){
            attributes.addFlashAttribute("alertClass", "danger");
            attributes.addFlashAttribute("msg", "Note not found.");
            return new RedirectView("/home");
        }
        attributes.addFlashAttribute("alertClass", "success");
        attributes.addFlashAttribute("msg", "Your note "+n.getNotetitle()+" has been deleted successfully");

        notesMapper.deleteNote(n.getNoteid(), _user.getUserid());

        return new RedirectView("/home");

    }

    @GetMapping("/home/files/delete/{fileid}")
    public RedirectView deleteFile( @PathVariable("fileid") int fileid,  RedirectAttributes attributes){
        String _sessionUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Users _user = usersMapper.getUserByUsername(_sessionUsername);

        Files f = filesMapper.getFile(fileid, _user.getUserid());

        if(_user == null){
            attributes.addFlashAttribute("alertClass", "danger");
            attributes.addFlashAttribute("msg", "File not found.");
            return new RedirectView("/home");
        }
        attributes.addFlashAttribute("alertClass", "success");
        attributes.addFlashAttribute("msg", "Your file "+f.getFilename()+" has been deleted successfully");

        filesMapper.deleteFiles(f.getFileid(), _user.getUserid());

        return new RedirectView("/home");

    }

    @GetMapping("/home/credentials/delete/{credentialid}")
    public RedirectView deleteCredential( @PathVariable("credentialid") int credentialid,  RedirectAttributes attributes){
        String _sessionUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Users _user = usersMapper.getUserByUsername(_sessionUsername);

        if(_user == null){
            attributes.addFlashAttribute("alertClass", "danger");
            attributes.addFlashAttribute("msg", "Credential not found.");
            return new RedirectView("/home");
        }

        Credentials n = credentialsMapper.getCredential(credentialid, _user.getUserid());

        if(n == null){
            attributes.addFlashAttribute("alertClass", "danger");
            attributes.addFlashAttribute("msg", "Credential not found.");
            return new RedirectView("/home");
        }
        attributes.addFlashAttribute("alertClass", "success");
        attributes.addFlashAttribute("msg", "Your credential for "+n.getUrl()+" has been deleted successfully");

        credentialsMapper.deleteCredential(n.getCredentialid(), _user.getUserid());

        return new RedirectView("/home");

    }


    @RequestMapping("/home")
    public ModelAndView getHome(@ModelAttribute("alertClass") String alertClass, @ModelAttribute("msg") String msg){
        String _sessionUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Users _user = usersMapper.getUserByUsername(_sessionUsername);
        es = new EncryptionService();

        ModelAndView mav = new ModelAndView();
        mav.setViewName("home");

        if(_user == null){

            mav.addObject("msg", "Error on session ID.");
            mav.addObject("alertClass", "danger");
            return mav;

        }

        ArrayList<Credentials> newCreds = new ArrayList<>();
        for(Credentials c : credentialsMapper.getCredentialsByUserid(_user.getUserid())){
            c.setUnencripted(es.decryptValue(c.getPassword(),c.getKey()));
            newCreds.add(c);
        }


        mav.addObject("notes", notesMapper.getNotesByUserid(_user.getUserid()));
        mav.addObject("credentials", newCreds);
        mav.addObject("files", filesMapper.getFilesByUserId(_user.getUserid()));
        mav.addObject("note", new Notes());
        mav.addObject("file", new Files());
        mav.addObject("credential", new Credentials());
        mav.addObject("msg", msg);
        mav.addObject("alertClass", alertClass);
        return mav;
    }

    @PostMapping("/home/file/upload")
    public RedirectView file(@RequestParam("fileUpload") MultipartFile file,
                             RedirectAttributes attributes) {
        String _sessionUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Users _user = usersMapper.getUserByUsername(_sessionUsername);

        Files fileExists = filesMapper.getFileByFilename(file.getOriginalFilename(), _user.getUserid());
        if(fileExists != null ){
            attributes.addFlashAttribute("alertClass", "danger");
            attributes.addFlashAttribute("msg", "Your file  "+file.getOriginalFilename()+" already exists.");
            return new RedirectView("/home");
        }
        Files f = dbFileStorageService.storeFile(file, _user.getUserid());

        attributes.addFlashAttribute("alertClass", "success");
        attributes.addFlashAttribute("msg", "Your file  "+f.getFilename()+" has been saved successfully");

        return new RedirectView("/home");
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") int fileId) {
        String _sessionUsername = SecurityContextHolder.getContext().getAuthentication().getName();
        Users _user = usersMapper.getUserByUsername(_sessionUsername);
        Files dbFile = filesMapper.getFile(fileId, _user.getUserid());
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getContenttype()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFilename() + "\"")
                .body(new ByteArrayResource(dbFile.getFiledata()));
    }
}
