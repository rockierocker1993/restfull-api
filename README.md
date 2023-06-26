**How to run**
```
Docker Compose
run command line : docker-compose up

Maven
create database dan seting config datasource 
    
    spring:
      datasource:
        url: jdbc:postgresql://localhost:5432/binaadzkia
        username: postgres
        password: chelsea80
    
di application-local.yml

run command line : mvn spring-boot:run

dan buka 
http://localhost:8080/swagger-ui.html
```

**spesification**
```
Spring boot 2.0.14
Oauth2
Swagger 2.9.2
Database Postgresql
JDK 8
```

**NOTE FOR LOGIN**
```
Api login sudah tersedia dari fitur spring security Oauth2
Cara Login via swagger 
klik button Authorize di pojok kanan atas di tampilan swagger
lalu masukan
username = phone number yang di register pada api register
password = password yang di register pada api register
client-id = client-test
client-secret : @Password123
```

**NOTE**
```
asymmetric keys belum terimplementasi
tidak ada public key dan priveta key
masih pake HS256
```

