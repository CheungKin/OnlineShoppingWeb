# OnlineShoppingWeb

An online shopping website demo made by:<br>
* Java framework : Spring boot<br>
* Java Persistence framework : Mybatis<br>
* Template engine : thymeleaf<br>
* Data Serialization Language : YAML

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

# Address
Each user allows having multiple addresses.<br>
![address](https://i.imgur.com/5a629SS.png)<br>
## Adding new address
![new_address](https://i.imgur.com/TqZukah.png)

# Product detail & comment
When you click a picture of product, it enters a detailed product page.<br>
![product_detail](https://i.imgur.com/qLGUv7k.png)<br>
After you bought those product(s), you can write comment(s)<br>
![comment](https://i.imgur.com/0r3Abx2.png)

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

# Cart(s)

# Order(s)

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

![update_user](https://i.imgur.com/0Z7JcW2.png)
### Searching user
![search_user](https://i.imgur.com/M1cuJoZ.png)
## Product management
![Create_product](https://i.imgur.com/3RKEcto.png)<br>
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

