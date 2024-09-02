## DAO design pattern

Access to data varies depending on the source of the data. Access to persistent storage, such as to a database, varies greatly depending on the type of storage (relational databases, object-oriented databases, flat files, and so forth) and the vendor implementation.

Use a Data Access Object (**DAO**) to **abstract and encapsulate all access to the data source.** The DAO manages the connection with the data source to obtain and store data.

-----

```mermaid
---
title: Factory for Data Access Object strategy using Factory Method
---

classDiagram
    direction LR
    class DAOFactory {
        +static getInstance() DAOFactory
        +getProductDAO() ProductDAO
        +getUserDAO() UserDAO
        +getBuyerDAO() BuyerDAO
        +getReviewDAO() ReviewDAO
        +getShappingCartDAO() ShappingCartDAO
        +getOrderDAO() OrderDAO
        +getRetailerDAO() RetailerDAO
    }

    class ProductDAO {
        <<interface>>
    }

    class UserDAO {
        <<interface>>
    }

    class BuyerDAO {
        <<interface>>
    }

    class ReviewDAO {
        <<interface>>
    }

    class ShoppingCartDAO {
        <<interface>>
    }

    class OrderDAO {
        <<interface>>
    }

    class RetailerDAO {
        <<interface>>
    }

    class DataSource {
        <<interface>>
    }

    DAOFactory <|-- DatabaseDAOFactory: inherits
    DatabaseDAOFactory --> DatabaseProductDAO: creates
    DatabaseDAOFactory --> DatabaseUserDAO
    DatabaseDAOFactory --> DatabaseBuyerDAO
    DatabaseDAOFactory --> DatabaseReviewDAO
    DatabaseDAOFactory --> DatabaseCartDAO
    DatabaseDAOFactory --> DatabaseOrderDAO
    DatabaseDAOFactory --> DatabaseRetailerDAO
    DatabaseProductDAO --|> ProductDAO: implements
    DatabaseUserDAO --|> UserDAO
    DatabaseBuyerDAO --|> BuyerDAO
    DatabaseReviewDAO --|> ReviewDAO
    DatabaseCartDAO --|> ShoppingCartDAO
    DatabaseOrderDAO --|> OrderDAO
    DatabaseRetailerDAO --|> RetailerDAO
    DatabaseProductDAO ..> DataSource: depends
```

------

Reference: https://www.oracle.com/java/technologies/dataaccessobject.html
