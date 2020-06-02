package pl.springboot.bookrentalservice.manager;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import pl.springboot.bookrentalservice.dao.AdminRepo;
import pl.springboot.bookrentalservice.dao.entity.UserLibrary;
import pl.springboot.bookrentalservice.dao.modelWrappers.LoginAndRoleWrapper;
import pl.springboot.bookrentalservice.dao.modelWrappers.TokenWrapper;

import javax.servlet.http.HttpServletRequest;
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

    public Object getLoginAndRole(HttpServletRequest request) {

        String jwtToken = request.getHeader("Authorization").replace("Bearer ","");
        String result="c";
        java.util.Base64.Decoder decoder = java.util.Base64.getUrlDecoder();
        if(jwtToken != null) {
            String[] parts = jwtToken.split("\\."); // split out the "parts" (header, payload and signature)

            if(parts.length == 3){
                result  = new String(decoder.decode(parts[1]));

                TokenWrapper tokenAttributes = new Gson().fromJson(result, TokenWrapper.class);

                LoginAndRoleWrapper loginAndRoleWrapper = new LoginAndRoleWrapper(tokenAttributes.getLogin(),tokenAttributes.getRole());

                Iterable<UserLibrary> users = findUsersByLogin(tokenAttributes.getLogin());

                if(users.iterator().hasNext())
                loginAndRoleWrapper.setId(users.iterator().next().getId());


                return loginAndRoleWrapper;
            }

        }

        return "błąd nie znaleziono takiego typa";
    }
}
