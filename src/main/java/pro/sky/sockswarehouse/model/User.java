package pro.sky.sockswarehouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pro.sky.sockswarehouse.constant.UserRole;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String email;
    @Column
    private String firstName;
    @Column
    private String lastName;
    private String username;
    private String password;
    private String phone;
    @Enumerated(EnumType.STRING)
    private UserRole role;
    private LocalDateTime regDate;
    private boolean enabled;

    public User(String email, String firstName, String lastName, String username, String password, String phone,
                UserRole role, LocalDateTime regDate) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.regDate = regDate;
    }

}
