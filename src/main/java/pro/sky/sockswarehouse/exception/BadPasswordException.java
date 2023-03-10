package pro.sky.sockswarehouse.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Wrong password")
public class BadPasswordException extends RuntimeException {
    public BadPasswordException(String message) {
        super(message);
        Logger logger = LoggerFactory.getLogger(BadPasswordException.class);
        logger.error(message);
    }
}
