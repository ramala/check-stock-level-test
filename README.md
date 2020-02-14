# check-stock-level-test
Service to check stock level for a retailer.

This is a spring boot application, uses in memory H2 database.

# Steps to set up project:

- Clone project from https://github.com/ramala/check-stock-level-test
```
git clone https://github.com/ramala/check-stock-level-test
```
- Open project in either IntelliJ or Eclipse as maven project
- Build project, cd to project directory and run below command:
```
mvn clean install
```   
- Run "ProductStockLevelApplication" run configuration or open "ProductStockLevelApplication" class and run "main" method
- As part of the config app creates "stockdb" in memory
- Creates tables PRODUCT, PRODUCT_RULES and STOCK_CHECK_ADVICE_AUDIT 
- Connect to H2 DB instance at http://localhost:8080/h2 when prompts for password enter below password
```
password
```  
Below are e.g values I have used
```
JDBC URL: jdbc:h2:mem:stockdb
user name: retial
password: password
```
- Database path user name and password details are in application.properties (under resources folder)

- Data can be inserted by running the below SQL statements in H2 db console
```
INSERT INTO PRODUCT (product_name, current_stock) VALUES
  ('a', 5),
  ('b', 8),
  ('c', 2),
  ('d', 0),
  ('e', 1);

INSERT INTO PRODUCT_RULES (product_name, minimum_stock_level, product_blocked, additional_volume) VALUES
  ('a', 4, FALSE, 0),
  ('b', 4, FALSE, 0),
  ('c', 4, TRUE, 0),
  ('d', 8, FALSE, 15),
  ('e', 4, FALSE, 0);
```
# Rest API and Request and Response payloads
**Product**
http://localhost:8080/products   **GET** method
Returns all products (Array of products) example payload is below:
```
[
  {
    "productName": "a",
    "currentStock": 5
  },
  {
    "productName": "b",
    "currentStock": 8
  },
  {
    "productName": "c",
    "currentStock": 2
  },
  {
    "productName": "d",
    "currentStock": 0
  },
  {
    "productName": "e",
    "currentStock": 1
  }
]
```
http://localhost:8080/product/{productName}   **GET** method
E.g. http://localhost:8080/product/a
returns only one product: as below:
```
{
"productName": "a",
"currentStock": 5
}
```

To create/update a new product use http://localhost:8080/product   **POST** method
Request Body is as below and response would be created/updated object returned
```
{"productName": "f", "currentStock":9}
```

To delete a product use http://localhost:8080/product/a   **DELETE** method
Returns a (http 201) NO_CONTENT response back.
 
 *Product Rules*
 http://localhost:8080/product/rules   **GET** Method
 Returns all product rules (Array of product rules) example payload is below:
 ```
 [
   {
     "productName": "a",
     "minimumStockLevel": 4,
     "productBlocked": false,
     "additionalVolume": 0
   },
   {
     "productName": "b",
     "minimumStockLevel": 4,
     "productBlocked": false,
     "additionalVolume": 0
   },
   {
     "productName": "c",
     "minimumStockLevel": 4,
     "productBlocked": true,
     "additionalVolume": 0
   },
   {
     "productName": "d",
     "minimumStockLevel": 8,
     "productBlocked": false,
     "additionalVolume": 15
   },
   {
     "productName": "e",
     "minimumStockLevel": 4,
     "productBlocked": false,
     "additionalVolume": 0
   }
 ]
 ```
 
 http://localhost:8080/product/rules/{productName}   **GET** method
 E.g. http://localhost:8080/product/rules/a
 returns only one product: as below:
 ```
 {
 "productName": "a",
 "minimumStockLevel": 4,
 "productBlocked": false,
 "additionalVolume": 0
 }
 ```
 To create/update a new product rule use http://localhost:8080/product/rules   **POST** method
 Request Body is as below and response would be created/updated object returned
 ```
{
    "productName": "R",
    "minimumStockLevel": 4,
    "productBlocked": false,
    "additionalVolume": 0
}
 ```
 
To delete a product rule use http://localhost:8080/product/rules/a   **DELETE** method
Returns a (http 201) NO_CONTENT response back.

**Stock check advice**

To check product order advice navigate to: http://localhost:8080/product-order-advice  **GET** method
Response will be as below:
```
{
  "toBeOrderdList": [
    {
      "productName": "d",
      "quantity": 23
    },
    {
      "productName": "e",
      "quantity": 3
    }
  ]
}
```
All the advices are stored in an audit table **STOCK_CHECK_ADVICE_AUDIT**

After few queries, from h2 DB console run 
```
SELECT * FROM STOCK_CHECK_ADVICE_AUDIT;
```
Results would be as below:
```
SELECT * FROM STOCK_CHECK_ADVICE_AUDIT;
ADVICE_CREATED_DATE  	ADVICE_PAYLOAD  
2020-02-13 20:39:53.804	{"toBeOrderdList":[{"productName":"d","quantity":23},{"productName":"e","quantity":3}]}
2020-02-13 20:56:57.04	{"toBeOrderdList":[{"productName":"d","quantity":23},{"productName":"e","quantity":3}]}
2020-02-13 20:56:58.361	{"toBeOrderdList":[{"productName":"d","quantity":23},{"productName":"e","quantity":3}]}
2020-02-13 20:56:59.776	{"toBeOrderdList":[{"productName":"d","quantity":23},{"productName":"e","quantity":3}]}
```
This table *ADVICE_PAYLOD* column is a *JSON* type.

# Assumptions
Third rule, order additional volume for certain products and in the example product **d** has a one of order of 15 isn't clear to me.
I would check with BA for requirements clarification but made an assumption here that
 
**On top of minimum stock, this is an additional volume required.**

 # Enhancements
- Layered architecture can be implemented 
- I would use may be **Drools rules engine** or easy rules with method chaining.
- Security implementation by role based access to end points
- API documentation will be provided in confluence
- More test coverage i.e. more unit tests, like MockMvc tests, integration tests and PIT tests 
- I would also set up a cucumber project with one happy path so that QA's can build on top of it
- UI interface using thymeleaf or react or angular
 - For an enterprise application I wouldn't use entity objects being returned in service controllers
- I would create a Service API package with model objects to return in response.
- I would also use **lombok** and **mapstruct** libraries for mapping objects.
 
# Best practices and current implementation
- For simplicity, used java 8, and did not use new features of JAVA8, streams API, lambda functions, method reference etc
- Spring recommends constructor injects rather than @Autowiring so used constructors. The Spring team generally advocates constructor 
  injection as it enables one to implement application components as immutable objects and to ensure that required dependencies are not null.
  the above also enforces single responsibility principle of SOLID principles.
 
 
 
 