package pro.sky.sockswarehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.sockswarehouse.dto.InvoiceDto;
import pro.sky.sockswarehouse.model.Invoice;
import pro.sky.sockswarehouse.service.InvoiceService;
import pro.sky.sockswarehouse.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {

    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService, UserService userService){
        this.invoiceService = invoiceService;
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
        return invoiceService.readAllInvoices(authentication);
    }

    @Operation(
            summary = "Get invoice by id",
            operationId = "readInvoiceById",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = InvoiceDto.class)
                            )
                    )},
            tags = "Invoices"
    )
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    ResponseEntity<Invoice> readInvoiceById(@PathVariable Long id, Authentication authentication){
        return invoiceService.readInvoiceById(id, authentication);
    }

}
