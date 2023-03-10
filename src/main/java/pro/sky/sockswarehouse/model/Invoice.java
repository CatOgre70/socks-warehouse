package pro.sky.sockswarehouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pro.sky.sockswarehouse.constant.SocksColorsTable;
import pro.sky.sockswarehouse.constant.WhOperation;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long invoiceId;
    private LocalDateTime localDateTime;
    Long userId;
    @Enumerated(EnumType.STRING)
    private SocksColorsTable socksColor;
    private int cottonPart;
    private int quantity;
    @Enumerated(EnumType.STRING)
    private WhOperation whOperation;

    public Invoice(LocalDateTime localDateTime, SocksColorsTable socksColor, int cottonPart, int quantity, WhOperation whOperation) {
        this.localDateTime = localDateTime;
        this.socksColor = socksColor;
        this.cottonPart = cottonPart;
        this.quantity = quantity;
        this.whOperation = whOperation;
    }

}
