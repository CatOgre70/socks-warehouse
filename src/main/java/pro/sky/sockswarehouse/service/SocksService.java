package pro.sky.sockswarehouse.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pro.sky.sockswarehouse.constant.Operation;
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
        if(!socksDtoChecking(socksDto)) {
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
        saveInvoiceInRepository(socks, WhOperation.INCOME, authentication);
        return ResponseEntity.ok(socksMapper.entityToDto(socks));
    }

    public ResponseEntity<SocksDto> removeSocks(SocksDto socksDto, Authentication authentication) {
        // User input checking and reactions
        if(!socksDtoChecking(socksDto)) {
            return ResponseEntity.badRequest().build();
        }
        // removing socks from warehouse
        Socks socks = socksMapper.dtoToEntity(socksDto);
        Optional<Socks> socksFound = socksRepository.findSocksByColorAndCottonPart(socks.getColor(), socks.getCottonPart());
        if(socksFound.isEmpty() || socksFound.get().getQuantity() < socks.getQuantity() ){ // There is no such socks in warehouse or not enough quantity
            logger.error(UserMessages.SOCKS_NOT_FOUND_OR_NOT_ENOUGH_QUANTITY.msgText);
            return ResponseEntity.badRequest().build();
        } else { // Remove socks from warehouse
            socks.setQuantity(socksFound.get().getQuantity() - socks.getQuantity());
            socks.setId(socksFound.get().getId());
        }
        socks = socksRepository.save(socks);
        saveInvoiceInRepository(socks, WhOperation.OUTCOME, authentication);
        return ResponseEntity.ok(socksMapper.entityToDto(socks));
    }

    /**
     * User input as SocksDto checking method
     * @param socksDto - user input
     * @return true - there are no any errors in socksDto
     *         false - there is the error in socksDto
     */
    private boolean socksDtoChecking(SocksDto socksDto){
        if(SocksColorsTable.getSocksColorByColor(socksDto.getColor()) == null) {
            String message = UserMessages.SOCKS_COLOR_NOT_FOUND_EXCEPTION.msgText
                    .replace("%color%", socksDto.getColor());
            logger.error(message);
            return false;
        }
        if(socksDto.getCottonPart() < 0 || socksDto.getCottonPart() > 100) {
            logger.error(UserMessages.SOCKS_COTTON_PART_WRONG_NUMBER_EXCEPTION.msgText);
            return false;
        }
        if(socksDto.getQuantity() <= 0) {
            logger.error(UserMessages.SOCKS_QUANTITY_WRONG_NUMBER_EXCEPTION.msgText);
            return false;
        }
        return true;
    }

    /**
     * Method to save invoice in the invoiceRepository for records
     * @param socks - socks added or distract from warehouse
     * @param authentication - authentication information from browser to save in the invoice for records
     */
    private void saveInvoiceInRepository(Socks socks, WhOperation whOperation, Authentication authentication){
        Invoice invoice = new Invoice(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS), socks.getColor(),
                socks.getCottonPart(), socks.getQuantity(), whOperation);
        Optional<User> user = userRepository.getUserByUsername(authentication.getName());
        if(user.isEmpty()){
            throw new UserNotFoundException(UserMessages.USER_NOT_FOUND_EXCEPTION.msgText);
        }
        invoice.setUserId(user.get().getUserId());
        invoiceRepository.save(invoice);
    }

    public ResponseEntity<Integer> getSocks(String color, String operation, Integer cottonPart) {

        Operation op = Operation.getOperationByName(operation);
        if(operation == null || op == null || cottonPart < 0 || cottonPart > 100) {
            return ResponseEntity.badRequest().build();
        }
        if(color != null && SocksColorsTable.getSocksColorByColor(color) == null) {
            return ResponseEntity.ok(0);
        }
        switch(op) {
            case EQUAL -> {
                return ResponseEntity.ok(socksRepository.getSocksEqual(color, cottonPart));
            }
            case LESS_THAN -> {
                return ResponseEntity.ok(socksRepository.getSocksLessThan(color, cottonPart));
            }
            case MORE_THAN -> {
                return ResponseEntity.ok(socksRepository.getSocksMoreThan(color, cottonPart));
            }
            default -> {
                return ResponseEntity.badRequest().build();
            }
        }
    }
}
