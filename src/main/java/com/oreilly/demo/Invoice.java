package com.oreilly.demo;

public class Invoice {
    private long invoice_id;
    private long customer_id;
    private String invoice_data;

    public Invoice(long invoice_id, long customer_id, String invoice_data) {
        this.invoice_id = invoice_id;
        this.customer_id = customer_id;
        this.invoice_data = invoice_data;
    }

    @Override
    public String toString() {
        return String.format(
                "Invoice[invoice_id=%d, customer_id=%d, invoice_data='%s']",
                invoice_id, customer_id, invoice_data);
    }

    public long getInvoice_id() {
        return invoice_id;
    }

    public String getInvoice_data() {
        return invoice_data;
    }
}