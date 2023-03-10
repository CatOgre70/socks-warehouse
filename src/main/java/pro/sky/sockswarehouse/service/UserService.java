package pro.sky.sockswarehouse.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pro.sky.sockswarehouse.dto.NewPassword;
import pro.sky.sockswarehouse.dto.UserDto;
import pro.sky.sockswarehouse.exception.BadPasswordException;
import pro.sky.sockswarehouse.exception.NewPasswordAlreadyUsedException;
import pro.sky.sockswarehouse.exception.UserNotFoundException;
import pro.sky.sockswarehouse.mapper.UserMapper;
import pro.sky.sockswarehouse.model.User;
import pro.sky.sockswarehouse.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passEnc;
    /** Конструктор */
    public UserService(UserRepository userRepository,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passEnc = new BCryptPasswordEncoder();
    }

    /**
     * Метод, который получает из БД Пользователя по его ИД номеру
     * @param userID
     * @return представление Пользователя
     */
    public User getUserByID(Long userID) {
        User userFound = userRepository.findUserByUserId(userID).orElse(null);
        if (userFound == null){
            throw new UserNotFoundException("User not found!");
        }
        return userFound;
    }

    /**
     * Метод, который выводит информацию о Пользователе (DTO)
     * @param inpUser
     * @return представление Пользователя
     */
    public UserDto getUserDto(User inpUser) {
        return userMapper.entityToDto(inpUser);
    }

    /**
     * Метод, смены пароля Пользователя
     * @param authUserName
     * @param newPassword
     * @return Возвращает DTO Пользователя
     */
    public UserDto updatePassword(String authUserName, NewPassword newPassword){
        Long id = getUserIdByName(authUserName);
        User userFound = getUserByID(id);
        String currPass = newPassword.getCurrentPassword() == null ? "EMPTY" : newPassword.getCurrentPassword();
        if (newPassword.getNewPassword() == null){
            throw new BadPasswordException("пароль не может быть пустым!");
        } else if (newPassword.getNewPassword().equals(newPassword.getCurrentPassword())){
            throw new NewPasswordAlreadyUsedException("Пароль совпадает с текущим");
        } else if (!passEnc.matches(currPass, userFound.getPassword())){
            throw new BadPasswordException("Неверно введён текущий пароль!");
        }
        userFound.setPassword(passEnc.encode(newPassword.getNewPassword()));
        return userMapper.entityToDto(userRepository.save(userFound));
    }
    /**
     * Метод, который вносит изменения в профиль Пользователя
     * @param authUserName
     * @param inpUserDto
     * @return
     */
    public UserDto updateUser(UserDto inpUserDto, String authUserName) {
        Long id = getUserIdByName(authUserName);
        User userFound = getUserByID(id);
        userFound.setFirstName(inpUserDto.getFirstName());
        userFound.setLastName(inpUserDto.getLastName());
        userFound.setPhone(inpUserDto.getPhone());
        userFound.setRegDate(inpUserDto.getRegistrationDate());
        userRepository.save(userFound);
        return userMapper.entityToDto(userFound);
    }

    /**
     * Метод, поиска ИД Пользователя по его Логину
     * @param name
     * @return ИД Пользователя
     */
    public Long getUserIdByName(String name) {
        User user = userRepository.findUserByEmail(name).orElseThrow();
        return user.getUserId();
    }

    /**
     * Метод, поиска Пользователя по его Логину
     * @param name
     * @return ИД Пользователя
     */
    public User getUserByName(String name) {
        return userRepository.findUserByEmail(name).orElseThrow();
    }
}
