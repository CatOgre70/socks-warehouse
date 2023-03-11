package pro.sky.sockswarehouse.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pro.sky.sockswarehouse.dto.InvoiceDto;
import pro.sky.sockswarehouse.mapper.InvoiceMapper;
import pro.sky.sockswarehouse.model.Invoice;
import pro.sky.sockswarehouse.repository.InvoiceRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;

    public InvoiceService(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
    }

    public ResponseEntity<List<InvoiceDto>> readAllInvoices() {
        List<Invoice> invoices = invoiceRepository.findAll();
        return ResponseEntity.ok(invoices.stream().map(invoiceMapper::entityToDto).collect(Collectors.toList()));
    }
}
