package com.oreilly.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class InvoiceTableLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(InvoiceTableLoader.class);

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... strings) throws Exception {
        log.debug("This is the InvoiceTableLoader!");
        log.info("Begin Creating invoices table");
        try {
            jdbcTemplate.execute("DROP TABLE invoices IF EXISTS");
            jdbcTemplate.execute("CREATE TABLE invoices(" +
                    " invoice_id  INT, customer_id INT, invoice_data VARCHAR(255))");
        } catch (Exception e) {
            log.error("Error encountered while dropping and creating invoices table: " + e.getMessage());
        }
        //data to load into the table...
        var rec1 = new Object[]{54, 1, "{\"time\": 19:53, \"tenderDetails\": {\"amount\": 23.43, \"type\": \"cash\"}, storeNumber:\"999\"}"};
        var rec2 = new Object[]{55, 2, "{\"time\": 12:00, \"tenderDetails\": {\"amount\": 4.95, \"type\": \"cash\"}, storeNumber:\"999\"}"};
        var rec3 = new Object[]{56, 2, "{\"time\": 08:49, \"tenderDetails\": {\"amount\": 100.12, \"type\": \"credit\"}, storeNumber:\"999\"}"};
        List<Object[]> invoiceRecords = Arrays.asList(rec1, rec2, rec3);
        invoiceRecords.forEach(invRec -> log.info(String.format("Inserting invoice record invoice_id: %s, customer_id: %s, invoice_data: %s", invRec[0], invRec[1], invRec[2])));

        try {
            jdbcTemplate.batchUpdate("INSERT INTO invoices(invoice_id, customer_id, invoice_data) VALUES (?,?,?)", invoiceRecords);
        } catch (Exception e) {
            log.error("Error encountered while inserting into the invoices table: " + e.getMessage());
        }
        log.info("End Creating invoices table");
    }
}