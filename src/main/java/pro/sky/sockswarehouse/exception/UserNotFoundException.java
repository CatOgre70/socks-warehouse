package pro.sky.sockswarehouse.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User with such id was not found in the database")
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String msg){
        super(msg);
        Logger logger = LoggerFactory.getLogger(UserNotFoundException.class);
        logger.error(msg);
    }
}
