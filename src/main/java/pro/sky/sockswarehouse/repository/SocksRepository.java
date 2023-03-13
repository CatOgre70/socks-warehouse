package pro.sky.sockswarehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.sockswarehouse.constant.SocksColorsTable;
import pro.sky.sockswarehouse.model.Socks;

import java.util.Optional;

public interface SocksRepository extends JpaRepository<Socks, Long> {
    Optional<Socks> findSocksByColorAndCottonPart(SocksColorsTable color, int cottonPart);

    @Query(value = "SELECT sum(socks.quantity) FROM socks WHERE (socks.color ILIKE CONCAT('%', :color, '%') " +
            "AND (socks.cotton_part = :cottonPart))", nativeQuery = true)
    Integer getSocksEqual(String color, Integer cottonPart);

    @Query(value = "SELECT sum(socks.quantity) FROM socks WHERE (socks.color ILIKE CONCAT('%', :color, '%') " +
            "AND (socks.cotton_part < :cottonPart))", nativeQuery = true)
    Integer getSocksLessThan(String color, Integer cottonPart);

    @Query(value = "SELECT sum(socks.quantity) FROM socks WHERE (socks.color ILIKE CONCAT('%', :color, '%') " +
            "AND (socks.cotton_part > :cottonPart))", nativeQuery = true)
    Integer getSocksMoreThan(String color, Integer cottonPart);
}
