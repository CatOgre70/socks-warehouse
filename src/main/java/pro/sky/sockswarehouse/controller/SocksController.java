package pro.sky.sockswarehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pro.sky.sockswarehouse.dto.SocksDto;
import pro.sky.sockswarehouse.service.SocksService;

@RestController
@RequestMapping("api/socks")
public class SocksController {

    private final SocksService socksService;

    public SocksController(SocksService socksService){
        this.socksService = socksService;
    }

    @Operation(
            summary = "Display socks stock with parameters",
            operationId = "getSocks",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.TEXT_HTML_VALUE
                            )
                    )},
            tags = "Socks"
    )
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    ResponseEntity<String> getSocks(
            @Parameter(description = "Socks color to find", schema = @Schema(implementation = String.class))
            @RequestParam(required = false) String color,
            @Parameter(description = "Comparison operator", schema = @Schema(implementation = String.class))
            @RequestParam String operation,
            @Parameter(description = "Socks cotton part for comparison operator", schema = @Schema(implementation = Integer.class))
            @RequestParam Integer cottonPart){
        return socksService.getSocks(color, operation, cottonPart);
    }

    @Operation(
            summary = "Add new socks on stock",
            operationId = "addSocks",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SocksDto.class),
                            examples = {@ExampleObject(name = "socksDto in JSON format",
                                    value = """
                                            {"color":"Red",
                                            "cottonPart":90,
                                            "quantity":100
                                            }"""
                            )}
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = SocksDto.class)
                            )
                    )},
            tags = "Socks"
    )
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/income")
    ResponseEntity<SocksDto> income(
            @Parameter(description = "Socks to be added to warehouse", schema = @Schema(implementation = SocksDto.class))
            @RequestBody SocksDto socksDto,
            Authentication authentication){
        return socksService.addSocks(socksDto, authentication);
    }

    @Operation(
            summary = "Remove socks from stock",
            operationId = "removeSocks",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SocksDto.class),
                            examples = {@ExampleObject(name = "socksDto in JSON format",
                                    value = """
                                            {"color":"Red",
                                            "cottonPart":90,
                                            "quantity":100
                                            }"""
                            )}
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = SocksDto.class)
                            )
                    )},
            tags = "Socks"
    )
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/outcome")
    ResponseEntity<SocksDto> outcome(
            @Parameter(description = "Socks to be remover from warehouse", schema = @Schema(implementation = SocksDto.class))
            @RequestBody SocksDto socksDto,
            Authentication authentication){
        return socksService.removeSocks(socksDto, authentication);

    }

}
