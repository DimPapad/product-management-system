# Product Management System Application

## 1. Features
- User register and login in their home page with their credentials.
- Users have roles (user/admin) in order to view, add, edit and delete products according to their role.
- A user can view product(s) and add a product.
- An admin can also edit and delete a product and change the role of another user.
- Every action of product addition, edit, deletion is recorded with the appropriate log and timestamp, referring to the user and product.

---

## 2. Tech Stack
- SpringBoot
- PostgreSQL
- pgAdmin

---

## 3. Usage Instructions

### Database Setup and Access

#### Docker-compose
File path: __...\product-management-system\database\docker\postgres_pgadmin_product_management_system\docker-compose.yml__

#### pg-Admin connection
- Server: __localhost__  
- Port: __5071__  
- Email: __dim@gmail.com__  
- Password: __myproduct__  

#### PostgreSQL Server Setup for data access
1. Add New Server -OR- context menu->Register Server
2. [General] tab
   1. Name: product_management_system
3. [Connection] tab
   1. Host name/Address: product_management_system-postgres
   2. Port: 5432 (postgresql default port)
   3. Maintenance database: product_management_system
   4. Username: dim
   5. Password: myproduct
   6. Save password: CHECKED

### Application Access
- Server: __localhost__  
- Port: __8081__  

#### Credentials
- Admin: username: PapGian, password: 1234
- User: username: PapIoan, password: 1234

#### Swagger Documentation
http://localhost:8081/swagger-ui/