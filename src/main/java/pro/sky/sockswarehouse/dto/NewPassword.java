package pro.sky.sockswarehouse.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class NewPassword {
    private String currentPassword;
    private String newPassword;
}
