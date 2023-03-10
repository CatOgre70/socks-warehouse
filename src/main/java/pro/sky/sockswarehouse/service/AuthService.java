package pro.sky.sockswarehouse.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import pro.sky.sockswarehouse.constant.UserRole;
import pro.sky.sockswarehouse.dto.RegReqDto;
import pro.sky.sockswarehouse.repository.UserRepository;

import java.util.Objects;

@Service
public class AuthService {
    private final UserDetailsManager userDM;

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public AuthService(UserDetailsManager userDM, UserRepository userRepository) {
        this.userDM = userDM;
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public UserDetails login(String userName, String password) {
        password = password == null ? "EMPTY" : password;
        String encryptPwd;
        String encryptPwdWithoutType;
        if (!userDM.userExists(userName)) {
            return null;
        } else {
            UserDetails userD = userDM.loadUserByUsername(userName);
            encryptPwd = userD.getPassword();
            encryptPwdWithoutType = encryptPwd.substring(8);
            if (passwordEncoder.matches(password, encryptPwdWithoutType)) {
                return userD;
            } else {
                return null;
            }
        }
    }

    public boolean register(RegReqDto registerReq, UserRole role) {
        if (userDM.userExists(registerReq.getUsername())) {
            return false;
        }
        UserDetails instUser = User.withDefaultPasswordEncoder()
                .password(registerReq.getPassword())
                .username(registerReq.getUsername())
                .roles(role.name())
                .build();
        userDM.createUser(instUser);
        pro.sky.sockswarehouse.model.User userInst = userRepository.getUserByUsername(registerReq.getUsername()).orElse(null);
        if (userInst != null){
            userInst.setRole(role);
            userInst.setEmail(registerReq.getUsername());
            userInst.setPhone(registerReq.getPhone());
            userInst.setFirstName(registerReq.getFirstName());
            userInst.setLastName(registerReq.getLastName());
            userRepository.save(userInst);
        }
        return true;
    }
    public Boolean dasActionPermit(Long commentUserId, Long currentUserId, UserRole currentUserRole){
        return Objects.equals(commentUserId, currentUserId) || currentUserRole == UserRole.ADMIN;
    }

}
