package pro.sky.sockswarehouse.mapper;

import org.springframework.stereotype.Component;
import pro.sky.sockswarehouse.constant.SocksColorsTable;
import pro.sky.sockswarehouse.constant.UserMessages;
import pro.sky.sockswarehouse.constant.WhOperation;
import pro.sky.sockswarehouse.dto.InvoiceDto;
import pro.sky.sockswarehouse.exception.UserNotFoundException;
import pro.sky.sockswarehouse.model.Invoice;
import pro.sky.sockswarehouse.model.User;
import pro.sky.sockswarehouse.repository.UserRepository;

import java.util.Optional;

@Component
public class InvoiceMapper {


    private final UserRepository userRepository;

    public InvoiceMapper(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public InvoiceDto entityToDto(Invoice invoice){
        Optional<User> userResult = userRepository.findUserByUserId(invoice.getUserId());
        if(userResult.isEmpty()) {
            String message = UserMessages.USER_NOT_FOUND_EXCEPTION.msgText
                    .replace("%id%", invoice.getUserId().toString());
            throw new UserNotFoundException(message);
        }
        return new InvoiceDto(invoice.getInvoiceId(), invoice.getLocalDateTime(), invoice.getUserId(),
                userResult.get().getFirstName() + userResult.get().getLastName(),
                userResult.get().getEmail(), userResult.get().getRole().description, invoice.getSocksColor().colorName,
                invoice.getCottonPart(), invoice.getQuantity(), invoice.getWhOperation().operationName);
    }

    public Invoice dtoToEntity(InvoiceDto invoiceDto) {

        Optional<User> userResult = userRepository.findUserByUserId(invoiceDto.getUserId());
        if(userResult.isEmpty()){
            String message = UserMessages.USER_NOT_FOUND_EXCEPTION.msgText
                    .replace("%id%", invoiceDto.getUserId().toString());
            throw new UserNotFoundException(message);
        }
        return new Invoice(invoiceDto.getId(), invoiceDto.getLocalDateTime(), invoiceDto.getUserId(),
                SocksColorsTable.getSocksColorByColor(invoiceDto.getSocksColor()), invoiceDto.getCottonPart(),
                invoiceDto.getQuantity(), WhOperation.getWhOperationByName(invoiceDto.getWhOperation()));

    }
}
