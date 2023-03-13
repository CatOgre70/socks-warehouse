# socks-warehouse

Small REST API application corresponds to Test Task of Sky.Pro Career Center with short Technical Assignment (TA). 

This is corporate class application to register incomes and outcomes of socks to/from warehouse. Additionally, 
following end-points was added to application: 

/invoices - display invoices (special records in database to register income/outcome socks operations)
/users - add new users, edit their details, display user details
/login - logon form processing
/logout - logout command processing

Swagger UI: 
/swagger-ui/index.html

To work with core functionality (api/socks end points) you should proceed through authentication (Basic one). There is 
only one user with ADMIN role available in users database immediately after installation: 

username: "admin@gmail.com"
password: "password"

You can use this user for all tests or add news user(s) through /users end point. Only authenticated users can add or 
remove socks to/from stock. 

You can test application here (two Ubuntu Hyper-V virtual machines on home server, PostgreSQL database and Jar 
application on JVM): 

http://46.188.28.196:8080/swagger-ui.html

## Development team
* [Vasily Demin](https://github.com/CatOgre70)