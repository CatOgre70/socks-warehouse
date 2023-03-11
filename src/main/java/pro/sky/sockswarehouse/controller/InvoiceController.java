package pro.sky.sockswarehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.sockswarehouse.constant.UserRole;
import pro.sky.sockswarehouse.dto.InvoiceDto;
import pro.sky.sockswarehouse.model.User;
import pro.sky.sockswarehouse.service.InvoiceService;
import pro.sky.sockswarehouse.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final UserService userService;

    public InvoiceController(InvoiceService invoiceService, UserService userService){
        this.invoiceService = invoiceService;
        this.userService = userService;
    }

    @Operation(
            summary = "Get list of all invoices",
            operationId = "getAllInvoices",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = InvoiceDto[].class)
                            )
                    )},
            tags = "Invoices"
    )
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    ResponseEntity<List<InvoiceDto>> readAllInvoices(Authentication authentication) {
        User user = userService.getUserByName(authentication.getName());
        if(user == null || user.getRole() != UserRole.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return invoiceService.readAllInvoices();
    }

}
