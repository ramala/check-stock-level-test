# check-stock-level-test
Service to check stock level for a retailer.

This is a spring boot application, uses H2 database in memory.

Clone project and open in either IntelliJ or Eclipse as maven project

run "ProductStockLevelApplication" config or open "ProductStockLevelApplication" class and run "main" method

As part of the config app creates "stockdb" in memory

creates tables PRODUCT, PRODUCT_RULES and STOCK_CHECK_ADVICE_AUDIT  and inserts data into PRODUCT and PRODUCT_RULES table

connect to H2 instance at http://localhost:8080/h2

JDBC URL: jdbc:h2:mem:stockdb
user name: retial
password: password

data should be inserted as part of running the configuration, check if data is inserted if not run the SQL commands in data.sql file in H2 console.

Below are the rest end points:

http://localhost:8080/products    -- gives list of Products and their current stock
http://localhost:8080/product-order-advice
