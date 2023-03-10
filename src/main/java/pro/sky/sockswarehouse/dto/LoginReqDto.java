package pro.sky.sockswarehouse.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LoginReqDto {

    private String username;
    private String password;
}
