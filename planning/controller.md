```mermaid
---
title: Class diagram for buyer controllers

---

classDiagram
    HttpServlet <|-- LoginServlet : /login
    HttpServlet <|-- LogoutServlet : /logout
    HttpServlet <|-- SignupServlet : /signup
    
    

    HttpServlet <|-- CartServlet : /cart
    HttpServlet <|-- BuyerOrdersServlet : /orders
    HttpServlet <|-- CheckoutServlet : /checkout
    HttpServlet <|-- OrderInfoServlet : /orders
```