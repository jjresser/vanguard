#Requirements
* Java 17 or newer
* Postman App (Testing of Api)
* Database server - MySql 

#Getting Started
* mySql create 2 tables
using the provided scripts in the resource folder
1. createGame_sales_summary.sql 
2. createGameSales.sql (Task 1)
3. createBatch_log.sql(Task 2)

#Generate csv file to your local disk 
Go to test folder  -> java -> com.example.demo -> GenerateCsvFile.java
Change directory below to save your csv file location.
```sh
String filePath = "C:/temp/vanguard/src/main/resources/import1.csv";
```
After that run this java class to generate the csv file to your filePath

In "resources" folder , filename import1.csv is an example of 1 million records
#Configure Setup on application.properties
```sh
spring.datasource.url=jdbc:mysql://localhost:3306/test?rewriteBatchedStatements=true
spring.datasource.username=root
spring.datasource.password=
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.cronjob.seconds=1
```

#Once Setup finished , run springBoot
go to main folder -> java -> com.example.demo -> Run VanguardApplication

#Swagger Url (Optional)
http://localhost:8080/swagger-ui/index.html

#Import the Csv file into Database through Api /csv/Import(Use Postman app - Task 5)
In postman app , select POST method paste url : 
http://localhost:8080/csv/import
and under "Key" column type "file" . 
In the middle drop down menu ,change to "File".
Under the "Value" column browse to the generated Csv file location and press "Send" button
![img.png](img.png)

# Api delete database on tables game_sales and batch_log - DELETE method (Optional)
In postman app , select DELETE method paste url : 
http://localhost:8080/csv/delete
and click "Send" button

#Testing Api Task 3 (Use Postman app - GET method)
http://localhost:8080/getGameSales?page=5&size=100
![img_1.png](img_1.png)
http://localhost:8080/getGameSales/byDate?fromDate=2025-04-01&toDate=2025-04-20&page=5&size=100
![img_2.png](img_2.png)
http://localhost:8080/getGameSales/byPrice?price=50&operator=<&page=5&size=100
![img_3.png](img_3.png)

#Testing Api Task 4 (Use Postman app - GET method)
http://localhost:8080/getTotalSales/count?fromDate=2025-04-01&toDate=2025-04-20
![img_4.png](img_4.png)
http://localhost:8080/getTotalSales/sales?fromDate=2025-04-01&toDate=2025-04-20
![img_5.png](img_5.png)
http://localhost:8080/getTotalSales/salesByGameNo?gameNo=3&fromDate=2025-04-01&toDate=2025-04-30
![img_6.png](img_6.png)

#Task 2 . Point 2 : Design and create the necessary tables to track the progress of the CSV import.
This is handle by -> If Import does not go through , will rollback on runtime exceptions or errors using @Transactional annotation.