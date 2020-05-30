package pl.springboot.bookrentalservice.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import pl.springboot.bookrentalservice.dao.AdminRepo;
import pl.springboot.bookrentalservice.dao.entity.UserLibrary;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AdminManager {

    private AdminRepo adminRepo;

    @Autowired
    public AdminManager(AdminRepo adminRepo){
        this.adminRepo=adminRepo;
    }

    public Iterable<UserLibrary> findAll(){
        return adminRepo.findAll();
    }

    public Iterable<UserLibrary> findUsersByLogin(String subLogin){
        Iterable<UserLibrary> result = adminRepo.findAll();
       return StreamSupport.stream(result.spliterator(),false)
                .filter(f->f.getLogin().indexOf(subLogin)!=-1).collect(Collectors.toList());
    }

    public Optional<UserLibrary> findUsersById(Long id){
        return  adminRepo.findById(id);
    }

    public UserLibrary save(UserLibrary userLibrary){
        return adminRepo.save(userLibrary);
    }

    public void deleteById(Long userId){
        adminRepo.deleteById(userId);
    }

    public UserLibrary findUserByLoginAndPassword(String login, String password){
        Iterable<UserLibrary> users = adminRepo.findAll();
        List<UserLibrary> usersList = new ArrayList<>();
        users.forEach(usersList::add);
        usersList = usersList.stream()
                .filter(f->f.getLogin().equals(login) && f.getPassword().equals(password))
                .collect(Collectors.toList());

        if (usersList.size()==0)
            return null;
        else
            return  usersList.get(0);
    }
}
