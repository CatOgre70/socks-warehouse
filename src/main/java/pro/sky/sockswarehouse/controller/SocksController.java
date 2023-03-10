package pro.sky.sockswarehouse.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.sockswarehouse.dto.SocksDto;
import pro.sky.sockswarehouse.service.SocksService;

@RestController
@RequestMapping("api/socks")
public class SocksController {

    private final SocksService socksService;

    public SocksController(SocksService socksService){
        this.socksService = socksService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/income")
    ResponseEntity<SocksDto> income(
            @Parameter(description = "Socks to be added to warehouse", schema = @Schema(implementation = SocksDto.class))
            @RequestBody SocksDto socksDto,
            Authentication authentication){
        return socksService.addSocks(socksDto, authentication);
    }
}
