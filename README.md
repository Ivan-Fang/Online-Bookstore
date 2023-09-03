# Description
  * A online bookstore using <b>self-defined Java framework</b>.
  * SSM and Springboot are not used, we use self-defined framework instead.
  * Only basic Java Web elements (ex. JDBC, Servlet, Listener, Filter...) are included.

# Functionalities
This project supports the following functionalities:
* login, register
* show the books info based on database
* add books to the cart, adjust the number of the books
* checkout, check the order list

# Screenshots
<img src="https://github.com/Ivan-Fang/BookStore/assets/40261483/35408165-b4d7-4403-86d7-2e2f88d1fa25" height="200px">
<img src="https://github.com/Ivan-Fang/BookStore/assets/40261483/a9f9a365-bd03-4e77-b82f-4a68d459d810" height="200px"><br/>
<img src="https://github.com/Ivan-Fang/BookStore/assets/40261483/5cca87fe-7949-41a4-b774-13856b72337d" width="800px"><br/>
<img src="https://github.com/Ivan-Fang/BookStore/assets/40261483/c662e730-ddd9-4524-932c-ffa5e196b695" width="800px"><br/>
<img src="https://github.com/Ivan-Fang/BookStore/assets/40261483/cc37cb9a-3a33-42e8-96eb-9ef7248f7625" width="800px"><br/>


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
* IDE: Intellij IDEA
