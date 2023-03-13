package pro.sky.sockswarehouse.mapper;

import org.springframework.stereotype.Component;
import pro.sky.sockswarehouse.constant.SocksColorsTable;
import pro.sky.sockswarehouse.dto.SocksDto;
import pro.sky.sockswarehouse.model.Socks;

@Component
public class SocksMapper {

    public SocksDto entityToDto(Socks socks){
        return new SocksDto(socks.getColor().colorName, socks.getCottonPart(), socks.getQuantity());
    }

    public Socks dtoToEntity(SocksDto socksDto){
        Socks socks = new Socks(SocksColorsTable.getSocksColorByColor(socksDto.getColor()), socksDto.getCottonPart());
        socks.setQuantity(socksDto.getQuantity());
        return socks;
    }
}
