package pro.sky.sockswarehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {

    private Long id;
    private LocalDateTime localDateTime;
    private String userFullName;
    private String userEmail;
    private String UserRole;
    private String socksColor;
    private int cottonPart;
    private int quantity;
    private String whOperation;

}
