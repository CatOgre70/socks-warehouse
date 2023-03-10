package pro.sky.sockswarehouse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pro.sky.sockswarehouse.constant.SocksColorsTable;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Socks socks = (Socks) o;
        return cottonPart == socks.cottonPart && color == socks.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, cottonPart);
    }
}
