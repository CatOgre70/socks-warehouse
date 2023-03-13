package pro.sky.sockswarehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.sockswarehouse.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Получение Пользователя по его ИД номеру
     * */
    Optional<User> findUserByUserId(Long userID);

    /**
     * Получение Пользователя по его Логину
     * */
    Optional<User> getUserByUsername(String username);

    Optional<User> findUserByEmail(String email);

}
