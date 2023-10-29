# 線上書城

## 專案介紹

* 本專案是一個簡易版的線上書城系統，包含以下功能：

    * 會員註冊、會員登入、會員登出

    * 展示商品（書籍）資訊

    * 添加商品至購物車、查看購物車內容、

    * 結帳、查詢購物紀錄

* 本專案採伺服器端渲染（server-side rendering）模式開發。伺服器處理完請求後會先用 `Thymeleaf` 進行頁面渲染，然後才把渲染好的頁面回傳給客戶端。

* 本專案未使用 SpringBoot、SSM 等框架，而使用使用 Java Web 基本組件（`Servlet`、`Filter`、`Listener`） 與 `JDBC` 進行開發。

* 此外，本專案利用 Java 中的 `reflection` 技術模擬了 Spring 中的依賴注入（dependency insertion）功能，以及 SpringMVC 中的 DispatcherServlet 功能。

* 本專案為 [尚硅谷《Java Web》](https://www.youtube.com/playlist?list=PLmOn9nNkQxJGKsCUQt6CpDmE2SjBOyLkK) 的 Course Project。

## 成果展示

* 會員登入與註冊頁面

  <img src="https://github.com/Ivan-Fang/BookStore/assets/40261483/7eab87bb-fc5c-42bc-b75b-7763fc11736a" height="200px">
  <img src="https://github.com/Ivan-Fang/BookStore/assets/40261483/fc85869b-8b3d-4476-a0c8-da230c7325de" height="200px">

* 商品展示頁面

  <img src="https://github.com/Ivan-Fang/BookStore/assets/40261483/73892648-9278-4b9a-a4f3-7d464705103e" width="600px"><br/>

* 購物車頁面
  
  <img src="https://github.com/Ivan-Fang/BookStore/assets/40261483/dcc8c809-2a0a-4683-87b1-e76059ad3f83" width="600px"><br/>

* 購物紀錄查詢頁面
  
  <img src="https://github.com/Ivan-Fang/BookStore/assets/40261483/03eaf970-ffcc-4196-86ee-d93eeb1173d9" width="600px"><br/>

## 細節說明

本專案使用以下技術完成各項功能的開發：

* 使用 `Filter` 進行登入驗證以及設定字元編碼。

* 使用 `JDBC` 進行增刪改查操作以及事務管理。

* 透過自定義方法讀取 bean 配置文件，並用 `reflection` 創建實例儲存到自定義 IoC 容器中，以及進行賴注入。

    * 本專案的 bean 配置文件格式與 Spring 一致，主要用於存取 controller、service 與 dao 物件，及其依賴關係。

    * 程式碼詳見：/src/ioc/ClassPathXmlApplicationContext.java

* 使用 `Servlet` 接收請求，根據網址從自定義 IoC 容器中取出 controller，並透過 `reflection` 調用該 controller 的方法。

    * 程式碼詳見：/src/servlet/DispatcherServlet.java

* 使用 `Listener` 讓專案在 ServletContext 初始化時就根據 bean 配置建創建 IoC 容器。

* 使用 `Thymeleaf` 進行頁面渲染。

## 開發環境

* 程式語言：Java 1.8

* 數據源：MySQL 8.0.33、Druid 1.1.10

* Web 容器：Tomcat 8.5.91

* 模板引擎：Thymeleaf 3.0.12
