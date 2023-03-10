package pro.sky.sockswarehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.sockswarehouse.constant.SocksColorsTable;
import pro.sky.sockswarehouse.model.Socks;

import java.util.Optional;

public interface SocksRepository extends JpaRepository<Socks, Long> {
    Optional<Socks> findSocksByColorAndCottonPart(SocksColorsTable color, int cottonPart);
    Socks save(Socks socks);
}
