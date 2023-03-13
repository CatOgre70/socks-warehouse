package pro.sky.sockswarehouse.mapper;

import org.springframework.stereotype.Component;
import pro.sky.sockswarehouse.constant.UserRole;
import pro.sky.sockswarehouse.dto.UserDto;
import pro.sky.sockswarehouse.model.User;

@Component
public class UserMapper {

    public UserDto entityToDto (User user) {
        return new UserDto(
                user.getUserId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhone(),
                user.getRole(),
                user.getRegDate()
        );
    }

    public User dtoToUser(UserDto dto) {
        return new User(dto.getEmail(),
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                null,
                dto.getPhone(),
                UserRole.USER,
                dto.getRegistrationDate()
        );
    }
}
