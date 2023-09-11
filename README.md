# Product Management System Application

---

## Database Setup and Access

### Docker-compose
File path: __...\product-management-system\database\docker\postgres_pgadmin_product_management_system\docker-compose.yml__

### pg-Admin connection
- Server: __localhost__  
- Port: __5071__  
- Email: __dim@gmail.com__  
- Password: __myproduct__  

### PostgreSql Server Setup for data access
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

---

## Application Access
- Server: __localhost__  
- Port: __8081__  

### Swagger Documentation
http://localhost:8081/swagger-ui/