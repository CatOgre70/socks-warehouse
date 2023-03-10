package pro.sky.sockswarehouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pro.sky.sockswarehouse.constant.SocksColorsTable;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "socks")
public class Socks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private SocksColorsTable color;
    private int cottonPart;
    private int quantity;

    public Socks(SocksColorsTable color, int cottonPart) {
        this.color = color;
        this.cottonPart = cottonPart;
    }

}
