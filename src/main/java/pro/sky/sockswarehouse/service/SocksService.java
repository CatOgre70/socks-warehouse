package pro.sky.sockswarehouse.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pro.sky.sockswarehouse.constant.SocksColorsTable;
import pro.sky.sockswarehouse.constant.UserMessages;
import pro.sky.sockswarehouse.constant.WhOperation;
import pro.sky.sockswarehouse.dto.SocksDto;
import pro.sky.sockswarehouse.exception.UserNotFoundException;
import pro.sky.sockswarehouse.mapper.SocksMapper;
import pro.sky.sockswarehouse.model.Invoice;
import pro.sky.sockswarehouse.model.Socks;
import pro.sky.sockswarehouse.model.User;
import pro.sky.sockswarehouse.repository.InvoiceRepository;
import pro.sky.sockswarehouse.repository.SocksRepository;
import pro.sky.sockswarehouse.repository.UserRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class SocksService {
    private final Logger logger = LoggerFactory.getLogger(SocksService.class);
    private final SocksRepository socksRepository;
    private final InvoiceRepository invoiceRepository;
    private final SocksMapper socksMapper;
    private final UserRepository userRepository;

    public SocksService(SocksRepository socksRepository, InvoiceRepository invoiceRepository, SocksMapper socksMapper, UserRepository userRepository){
        this.socksRepository = socksRepository;
        this.invoiceRepository = invoiceRepository;
        this.socksMapper = socksMapper;
        this.userRepository = userRepository;
    }


    public ResponseEntity<SocksDto> addSocks(SocksDto socksDto, Authentication authentication) {
        // User input checking and reactions
        if(SocksColorsTable.getSocksColorByColor(socksDto.getColor()) == null) {
            String message = UserMessages.SOCKS_COLOR_NOT_FOUND_EXCEPTION.msgText
                    .replace("%color%", socksDto.getColor());
            logger.error(message);
            return ResponseEntity.badRequest().build();
        }
        if(socksDto.getCottonPart() < 0 || socksDto.getCottonPart() > 100) {
            logger.error(UserMessages.SOCKS_COTTON_PART_WRONG_NUMBER_EXCEPTION.msgText);
            return ResponseEntity.badRequest().build();
        }
        if(socksDto.getQuantity() <= 0) {
            logger.error(UserMessages.SOCKS_QUANTITY_WRONG_NUMBER_EXCEPTION.msgText);
            return ResponseEntity.badRequest().build();
        }

        // adding socks in warehouse
        Socks socks = socksMapper.dtoToEntity(socksDto);
        Optional<Socks> socksFound = socksRepository.findSocksByColorAndCottonPart(socks.getColor(), socks.getCottonPart());
        if(socksFound.isEmpty()){ // New type of socks
            socks.setQuantity(socksDto.getQuantity());
        } else { // Add new socks to existing type
            socks.setQuantity(socksFound.get().getQuantity() + socks.getQuantity());
            socks.setId(socksFound.get().getId());
        }
        socks = socksRepository.save(socks);
        Invoice invoice = new Invoice(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), socks.getColor(),
                socks.getCottonPart(), socks.getQuantity(), WhOperation.INCOME);
        Optional<User> user = userRepository.getUserByUsername(authentication.getName());
        if(user.isEmpty()){
            throw new UserNotFoundException(UserMessages.USER_NOT_FOUND_EXCEPTION.msgText);
        }
        invoice.setUserId(user.get().getUserId());
        invoiceRepository.save(invoice);
        return ResponseEntity.ok(socksMapper.entityToDto(socks));
    }
}
