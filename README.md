
# Description
  * Create a online bookstore with <span style="background-color: #FFFF00">self-defined Java framework</span>.
  * Only basic Java Web elements (ex. JDBC, Servlet, Listener, Filter...) are included.
  * SSM and Springboot are not used, we use self-defined framework instead.

# Functionalities
This project supports the following functionalities:
* login, register
* show the books info based on database
* add books to the cart, adjust the number of the books
* checkout, check the order list

# Self-defined Java framework
* `DispatcherServlet`: Mimics the `DispatcherServlet` in Spring which implements the Controller functionality in MVC.
* `ClassPathXmlApplicationContext`: A bean factory for implementing IOC (Inverse of Control) and DI (Dependency Injection) functionality.
* `ConnUtils`: Connect mysql database with JDBC.
* `OpenSessionInViewFilter`, `TransactionManager`: Unified the management of connect, close, and rollback of transactions.
* `BaseDAO`: A general data access object .
  * `BookDAO`, `CartItemDAO`, `OrderDAO`, `OrderItemDAO`, `UserDAO`: Inherit `BaseDAO` and access the corressponding table.
