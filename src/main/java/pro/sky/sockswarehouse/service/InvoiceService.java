package pro.sky.sockswarehouse.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pro.sky.sockswarehouse.constant.UserRole;
import pro.sky.sockswarehouse.dto.InvoiceDto;
import pro.sky.sockswarehouse.mapper.InvoiceMapper;
import pro.sky.sockswarehouse.model.Invoice;
import pro.sky.sockswarehouse.model.User;
import pro.sky.sockswarehouse.repository.InvoiceRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final UserService userService;
    private final InvoiceMapper invoiceMapper;

    public InvoiceService(InvoiceRepository invoiceRepository, UserService userService, InvoiceMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.userService = userService;
        this.invoiceMapper = invoiceMapper;
    }

    /**
     * Read the full list of invoices from the database
     * @param authentication Authentication data from browser
     * @return The list of all invoices from the database
     */
    public ResponseEntity<List<InvoiceDto>> readAllInvoices(Authentication authentication) {
        User user = userService.getUserByName(authentication.getName());
        if(user == null || user.getRole() != UserRole.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        List<Invoice> invoices = invoiceRepository.findAll();
        return ResponseEntity.ok(invoices.stream().map(invoiceMapper::entityToDto).collect(Collectors.toList()));
    }

    /**
     * Read the invoice with selected id
     * @param id Invoice id
     * @param authentication Authentication data from browser
     * @return The invoice from the database with selected id
     */
    public ResponseEntity<Invoice> readInvoiceById(Long id, Authentication authentication) {
        User user = userService.getUserByName(authentication.getName());
        if(user == null || user.getRole() != UserRole.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(invoiceRepository.findById(id).orElseThrow());
    }

}
