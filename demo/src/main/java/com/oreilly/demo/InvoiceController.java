package com.oreilly.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class InvoiceController {
    private static final Logger log = LoggerFactory.getLogger(InvoiceController.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("/invoice")
    public String invoice(@RequestParam(value = "customer_id", defaultValue = "") String customer_id) {
        log.debug("This is the invoice method in the InvoiceController!");
        return getInvoicesByCustId(customer_id);
    }

    //accepts a long CustomerID and returns a Map<long InvoiceID, String TenderType>
    public HashMap<Long, String> getInvoicesMapByCustId(long longCustId) {
        // Create an empty hash map by declaring object of string and integer type
        HashMap<Long, String> invoiceMap = new HashMap<>();
        //populate the map with data returned by the query
        log.debug("query invoices for customer_id: " + longCustId);
        try {
            jdbcTemplate.query(
                    "SELECT invoice_id, customer_id, invoice_data FROM invoices WHERE customer_id = ?", new Object[]{longCustId},
                    (rs, rowNum) -> new Invoice(rs.getLong("invoice_id"), rs.getLong("customer_id"), rs.getString("invoice_data"))
            ).forEach(invoice -> invoiceMap.put(invoice.getInvoice_id(), invoice.getInvoice_data()));
        } catch (Exception e) {
            log.error("Error encountered while querying for cust_id: " + longCustId + " : " + e.getMessage());
        }
        return invoiceMap;
    }

    public String getInvoicesByCustId(String cust_id) {
        var longCustId = convertStringToLong(cust_id);
        if (longCustId != null) {
            StringBuilder invoiceResults = new StringBuilder("customer_id: " + cust_id + " invoices: ");
            log.debug("This is the getInvoicesByCustId method of InvoiceTableLoader!");
            HashMap<Long, String> invoiceMap = getInvoicesMapByCustId(longCustId);
            // Iterating HashMap through for loop
            for (Map.Entry<Long, String> set : invoiceMap.entrySet()) {
                invoiceResults.append("<br> Invoice:").append(set.getKey()).append(" TenderType: ").append(set.getValue());
            }
            return invoiceResults.toString();
        } else {
            log.warn("Invalid cust_id: " + cust_id);
            return "Invalid cust_id: " + cust_id;
        }
    }

    private Long convertStringToLong(String str) {
        try {
            return Long.valueOf(str);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}