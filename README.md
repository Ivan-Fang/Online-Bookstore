# Description
  * A online bookstore using <b>self-defined Java framework</b>.
  * SSM and Springboot are not used, we use self-defined framework instead.
  * Only basic Java Web elements (ex. JDBC, Servlet, Listener, Filter...) are included.

# Functionalities
This project supports the following functionalities.
* login, register
  <br/><img src="https://github.com/Ivan-Fang/BookStore/assets/40261483/7eab87bb-fc5c-42bc-b75b-7763fc11736a" height="200px">
  <img src="https://github.com/Ivan-Fang/BookStore/assets/40261483/fc85869b-8b3d-4476-a0c8-da230c7325de" height="200px">
* show the books info based on database, add books to the cart
  <br/><img src="https://github.com/Ivan-Fang/BookStore/assets/40261483/73892648-9278-4b9a-a4f3-7d464705103e" width="600px"><br/>
* adjust the number of the books, checkout
  <br/><img src="https://github.com/Ivan-Fang/BookStore/assets/40261483/dcc8c809-2a0a-4683-87b1-e76059ad3f83" width="600px"><br/>
* check the order list
  <br/><img src="https://github.com/Ivan-Fang/BookStore/assets/40261483/03eaf970-ffcc-4196-86ee-d93eeb1173d9" width="600px"><br/>

# Self-defined Java framework
* `DispatcherServlet`: Mimics the `DispatcherServlet` in Spring which implements the Controller functionality in MVC.
* `ClassPathXmlApplicationContext`: A bean factory for implementing IOC (Inverse of Control) and DI (Dependency Injection) functionality.
* `ConnUtils`: Connect mysql database with JDBC.
* `OpenSessionInViewFilter`, `TransactionManager`: Unified the management of connect, close, and rollback of transactions.
* `BaseDAO`: A general data access object .
  * `BookDAO`, `CartItemDAO`, `OrderDAO`, `OrderItemDAO`, `UserDAO`: Inherit `BaseDAO` and access the corressponding table.

# Development Environment
* Java 1.8
* MySQL 8.0.33
* Tomcat 8.5.91
* Thymeleaf 3.0.12
* Druid 1.1.10
* Vue 2
* Axios 0.21.1
* IDE: Intellij IDEA 2023
