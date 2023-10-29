# Online Bookstore

## Project Introduction

* This project is a simple version of the online bookstore system, including the following functions:

    * Member registration, member login, member logout

    * Display product (book) information.

    * Add products to shopping cart, view shopping cart contents.

    * Checkout, checking shopping history.

* The project is developed in server-side rendering mode. After processing the request, the server will use `Thymeleaf` to render the page first, and then send the rendered page back to the client.

* This project does not use frameworks such as SpringBoot, SSM, etc. Instead, we uses Java Web basic components (`Servlet`, `Filter`, `Listener`) and `JDBC` for development.

* In addition, this project uses `reflection` technology in Java to simulate the dependency insertion function in Spring and the DispatcherServlet function in SpringMVC.

* This project is a Course Project of [AtGuiGu "Java Web"](https://www.youtube.com/playlist?list=PLmOn9nNkQxJGKsCUQt6CpDmE2SjBOyLkK).

## Results Display

* Member login and registration page

  <img src="https://github.com/Ivan-Fang/BookStore/assets/40261483/7eab87bb-fc5c-42bc-b75b-7763fc11736a" height="200px">
  <img src="https://github.com/Ivan-Fang/BookStore/assets/40261483/fc85869b-8b3d-4476-a0c8-da230c7325de" height="200px">

* Product display page

   <img src="https://github.com/Ivan-Fang/BookStore/assets/40261483/73892648-9278-4b9a-a4f3-7d464705103e" width="600px"><br/>

* Shopping cart page
  
   <img src="https://github.com/Ivan-Fang/BookStore/assets/40261483/dcc8c809-2a0a-4683-87b1-e76059ad3f83" width="600px"><br/>

* Shopping history page
  
   <img src="https://github.com/Ivan-Fang/BookStore/assets/40261483/03eaf970-ffcc-4196-86ee-d93eeb1173d9" width="600px"><br/>

## Detailed description

This project uses the following techniques to develop each feature:

* We use `Filter` for login authentication and character encoding setting.

* We use `JDBC` for CRUD and transaction management.

* We read the bean profile through self-defined methods, create instances with `reflection` and save them in self-defined IoC container, and perform dependency injection.

    * The bean profile format in this project is consistent with Spring. It is mainly used for accessing controller, service, and dao instances, and managing their dependencies.

    * See /src/ioc/ClassPathXmlApplicationContext.java for more details

* We use `Servlet` to receive requests, retrieve controllers from self-defined IoC container based on the URL, and invoke the controllers' methods through `reflection`.

    * See /src/servlet/DispatcherServlet.java for more details.

* We use `Thymeleaf` for page rendering.

## Development environment

* Programming language: Java 1.8

* Data source: MySQL 8.0.33, Druid 1.1.10.

* Web container: Tomcat 8.5.91.

* Template engine: Thymeleaf 3.0.12