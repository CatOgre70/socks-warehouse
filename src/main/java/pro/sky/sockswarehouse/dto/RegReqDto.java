package pro.sky.sockswarehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pro.sky.sockswarehouse.constant.UserRole;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegReqDto {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private UserRole role;
}
