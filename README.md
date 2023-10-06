# oreilly-dant-spring-rest-demo

Dan Tiedemans submission for OReilly Enhancement Team - Interview Assessment project
 
 This project uses Spring Web dependencies to create webservice
 
 This project uses JDBC API and H2 Database dependencies to load invoice table on startup
 
 Instructions:
 run DemoApplication
 
 go to http://localhost:8080/invoice?customer_id=1
 
 you can select other customer_ids but only 1 and 2 are valid

It will return items from the invoices table depending on which customer_id you pass.

There is a public method in the InvoiceController class called getInvoicesMapByCustId which should satisfy the requirements.

The requirements were:

Build a Spring rest service that accepts a long CustomerID and returns a Map<long InvoiceID, String TenderType>. This method will get all invoices and tendertypes for that customerID from the invoice table. 
