package pl.springboot.bookrentalservice.api;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.springboot.bookrentalservice.dao.entity.UserLibrary;
import pl.springboot.bookrentalservice.dao.modelWrappers.LoginAndRoleWrapper;
import pl.springboot.bookrentalservice.dao.modelWrappers.TokenWrapper;
import pl.springboot.bookrentalservice.manager.AdminManager;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("api/admin")
public class AdminApi {

    private AdminManager adminManager;

    @Autowired
    public AdminApi(AdminManager adminManager){
        this.adminManager=adminManager;
    }

    @GetMapping("/all")
    public Iterable<UserLibrary> getAllUsers(){
        return adminManager.findAll();
    }

    @GetMapping("/user")
    public Iterable<UserLibrary> findByLogin(@RequestParam String login){
        return adminManager.findUsersByLogin(login);
    }

    @PostMapping("/register")
    public UserLibrary createLibraryUser(@RequestBody UserLibrary userLibrary){
        return adminManager.save(userLibrary);
    }



}
