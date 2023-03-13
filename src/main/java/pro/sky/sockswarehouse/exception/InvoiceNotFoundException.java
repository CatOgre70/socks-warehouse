package pro.sky.sockswarehouse.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User with such id was not found in the database")
public class InvoiceNotFoundException extends RuntimeException{
    public InvoiceNotFoundException(String message){
        super(message);
        Logger logger = LoggerFactory.getLogger(InvoiceNotFoundException.class);
        logger.error(message);
    }
}
