# cma-server

Clinic Management Application - Server

### 1. Environment requirement

| Element    | Version        |
|------------|----------------|
| PostgreSQL | 12             |
| Java       | 11             |
| Maven      | 3.6.3 or later |
| SpringBoot | 2.3.4          |

### 2. How to config

Config file directory: `cma-server\src\main\resources\application.yml`

### 3. How to run

- Open `terminal` or `command line`

- Navigate to `cma-server` folder:
```
cd {your-custom-folder}\cma-server
```
- Run project:
```
mvn spring-boot:run
```


### 4. Generate Test Report 

```
 mvn clean install test surefire-report:report
```
