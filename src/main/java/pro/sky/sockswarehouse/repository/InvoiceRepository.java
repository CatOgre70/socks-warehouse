package pro.sky.sockswarehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.sockswarehouse.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
