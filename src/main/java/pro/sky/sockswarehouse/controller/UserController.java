package pro.sky.sockswarehouse.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pro.sky.sockswarehouse.constant.UserRole;
import pro.sky.sockswarehouse.dto.NewPassword;
import pro.sky.sockswarehouse.dto.RegReqDto;
import pro.sky.sockswarehouse.dto.UserDto;
import pro.sky.sockswarehouse.model.User;
import pro.sky.sockswarehouse.service.AuthService;
import pro.sky.sockswarehouse.service.UserService;

import static pro.sky.sockswarehouse.constant.UserRole.USER;

@RestController
@RequestMapping(path = "/users")
public class UserController {
    private final UserService userService;
    private final AuthService authService;

    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @Operation(
            summary = "getUser - User data output",
            operationId = "getUser_1",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    )
            }, tags = "Users"
    )
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/me")
    public ResponseEntity<UserDto> getUser(Authentication authentication) {
        Long id = userService.getUserIdByName(authentication.getName());
        UserDto instUserDto = userService.getUserDto(userService.getUserByID(id));
        return ResponseEntity.ok(instUserDto);
    }

    @Operation(
            summary = "setPassword - new user password input",
            operationId = "setPassword",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NewPassword.class),
                            examples = {@ExampleObject(name = "JSON object NewPassword",
                                    value = """
                                            {"currentPassword": "OldPassword",
                                            "newPassword": "NewPassword"
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
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    )},
            tags = "Users"
    )
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/set_password")
    public ResponseEntity<UserDto> setPassword(@Parameter(description = "Данные о пароле Пользователя") @RequestBody NewPassword inpPWD,
                                               Authentication authentication
    ) {
        UserDto resultEntity = userService.updatePassword(authentication.getName(), inpPWD);
        return ResponseEntity.ok(resultEntity);
    }

    @Operation(
            summary = "Обновление данных о Пользователе",
            operationId = "updateUser",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserDto.class),
                            examples = {@ExampleObject(name = "JSON object UserDto",
                            value = """
                                    {"id" : 1,
                                    "email" : "user@domain.com",
                                    "firstName" : "Alex",
                                    "lastName" : "Alexandrov",
                                    "phone" : "+79008007060",
                                    "role" : "User",
                                    "registrationDate" : "2023-03-10T18:25:43.511Z"
                                    }"""
                            )}
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Данные записаны!",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = UserDto.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "203",
                            description = "No content",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    )},
            tags = "Users"
    )
    @PreAuthorize("isAuthenticated()")
    @PatchMapping("/me")
    public ResponseEntity<UserDto> updateUserData(@RequestBody UserDto inpUser,
                                                  Authentication authentication) {
        UserDto resultEntity = userService.updateUser(inpUser, authentication.getName());
        return ResponseEntity.ok(resultEntity);
    }

    @Operation(
            summary = "Adding new user by Admin",
            operationId = "addNewUser",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RegReqDto.class),
                            examples = {@ExampleObject(name = "JSON object RegReqDto",
                            value = """
                                    {"username" : "user@domain.com",
                                    "password" : "password",
                                    "firstName" : "Alex",
                                    "lastName" : "Fereiro",
                                    "phone" : "+71002003040",
                                    "role" : "ADMIN"
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
                                    schema = @Schema(implementation = UserDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not Found",
                            content = @Content(mediaType = MediaType.TEXT_HTML_VALUE)
                    )},
            tags = "Users"
    )
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/addnew")
    public ResponseEntity<UserDto> addNewUser(@RequestBody RegReqDto req, Authentication authentication) {
        User user = userService.getUserByName(authentication.getName());
        if(user == null || user.getRole() != UserRole.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        UserRole role = req.getRole() == null ? USER : req.getRole();
        if (authService.register(req, role)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
