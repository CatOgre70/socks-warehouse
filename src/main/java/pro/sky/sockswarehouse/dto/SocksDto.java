package pro.sky.sockswarehouse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO class for Socks model (Entity) class
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocksDto {

    private String color;
    private int cottonPart;
    private int quantity;

}
