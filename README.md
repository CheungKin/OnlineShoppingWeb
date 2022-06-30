# OnlineShoppingWeb

An online shopping website demo made by:<br>
* Java framework : Spring boot<br>
* Java Persistence framework : Mybatis<br>
* Template engine : thymeleaf<br>
* Data Serialization Language : YAML
## Function:
* [Regristration](https://github.com/CheungKin/OnlineShoppingWeb/blob/main/README.md#registration)
* [Login](https://github.com/CheungKin/OnlineShoppingWeb/blob/main/README.md#login)
* [User Role](https://github.com/CheungKin/OnlineShoppingWeb/blob/main/README.md#login)
* [Navigation Bar]
* [Update self information]

# Index
![Index](https://i.imgur.com/KO8HUEj.png)

# Registration
Click `Sign up` bottom on the naviagtion bar to register an account.<br>
![register](https://i.imgur.com/OBk3YDG.png)<br>
Fill in user's information & address. (Password with pattern)<br>
![pattern](https://i.imgur.com/7weWLII.png)

# Login
Fill in `user name` or `email` and `password` for login.<br>
![login](https://i.imgur.com/qPo8fGd.png)<br>
When the user is not existing or fill in a wrong password, it returns an error.<br>
![login_fail](https://i.imgur.com/oG2wiR2.png)

# User Role
Each user includes a role in either `ROLE_ADMIN` or `ROLE_USER`.<br>
![User_Role](https://i.imgur.com/E4MWurM.png)

# Navigation Bar
Each role has a corresponding navigation bar:<br>
* User:<br>
![ROLE_USER](https://i.imgur.com/MHg1mov.png)<br>
* Admin:<br>
![ROLE_ADMIN](https://i.imgur.com/ae3DLgu.png)

# Update self information
User can update his information including password, gender and email.<br>
![update_user](https://i.imgur.com/0Z7JcW2.png)

# Address
Each user allows having multiple addresses and sets one address as a default address.<br>
![address](https://i.imgur.com/5a629SS.png)<br>
## Adding new address
![new_address](https://i.imgur.com/TqZukah.png)

# Product detail & comment
When you click a picture of product, it enters a detailed product page.<br>
![product_detail](https://i.imgur.com/qLGUv7k.png)<br>
After you bought those product(s), you can write comment(s)<br>
![comment](https://i.imgur.com/0r3Abx2.png)<br>
Other user cannot add comment<br>
![other_user](https://i.imgur.com/l1ZVHgo.png)

# Category
There are three levels of categories<br>
![Category1](https://i.imgur.com/YBkdiQO.png)<br>
![Category2](https://i.imgur.com/bC4f6Sy.png)<br>
![Category3](https://i.imgur.com/NFpdLRp.png)<br>
For example, click `CPU` category<br>
![Category_CPU](https://i.imgur.com/UmIRMgi.png)

# Search
Type the name of a product for searching.<br>
![Search1](https://i.imgur.com/32ZEp2b.png)<br>
Return the product with the given product name.<br>
![Search2](https://i.imgur.com/dJDHcIb.png)

# Sold out
If the stock of product is 0, it means that product is out-of-stock.<br>
![sold_out1](https://i.imgur.com/YlA7W6D.png)
![sold_out2](https://i.imgur.com/r99pLXS.png)

# Cart(s)
Users can add product(s) to their cart<br>
![cart1](https://i.imgur.com/vunQRlQ.png)<br>
If the cart is empty:<br>
![empty_cart](https://i.imgur.com/BgUhpH5.png)

# Order(s)
After clicking `check out` or `buy it` buttom, it redircts to confirm page.<br>
To confirm your destination and delivery time.<br>
![order_comfirm](https://i.imgur.com/QsNV7CG.png)
Then, user can view his order with its `status`<br>
![order_detail](https://i.imgur.com/oTHwDDH.png)<br>
![order_detail2](https://i.imgur.com/aKMBovx.png)<br>
Adminstrator can change order's `status`.<br>
![order_id](https://i.imgur.com/zElTamV.png)<br>
![order_detail3](https://i.imgur.com/8ph4PPw.png)

# Page partition
Using `pageHelper` for page partition<br>
### page 1:
![page1](https://i.imgur.com/b8F0slZ.png)
### page 2:
![page2](https://i.imgur.com/OOup6sX.png)

# Adminstration
When the user's role is `ROLE_ADMIN`:<br>
![admin_index](https://i.imgur.com/GZQAi2S.png)<br>
## User management
Click `update` or `delete` to manage user(s):<br>
![user_list](https://i.imgur.com/HfbocBT.png)<br>
### Upate user information
Adminstrator can change user's role and other information, but only that user can change the password himself.
![admin_user_role](https://i.imgur.com/SX9yDkZ.png)
### Searching user
![search_user](https://i.imgur.com/M1cuJoZ.png)
## Product management
Adminstrator can add or update product and set its category
![Create_product](https://i.imgur.com/3RKEcto.png)<br>
![Create_product2](https://i.imgur.com/SavkoaR.png)<br>
![Update_product](https://i.imgur.com/gUetNyr.png)<br>

# Error page
If an error is happened, it redirects to the error page.<br>
![error](https://i.imgur.com/heMPg1e.png)

# Database
Users<br>
![Users](https://i.imgur.com/y6cvPj6.png)<br>
Products<br>
![Products](https://i.imgur.com/SyvIqUy.png)<br>
Category<br>
![Category](https://i.imgur.com/O7npxZO.png)<br>
Cart<br>
![Cart](https://i.imgur.com/LZe3LTx.png)<br>
Orders<br>
![Orders](https://i.imgur.com/kcWhYwv.png)<br>
Order_detail<br>
![Order_detail](https://i.imgur.com/kZNCfAB.png)<br>
Comment<br>
![Comment](https://i.imgur.com/KiWnnxA.png)

