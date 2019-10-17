# Systems Development Analyst Practical Assessment

> **RESTFUL API** *developed using Spring Boot and TDD*

## Endpoints

### Departments

1. **Create Department** 

```http
POST http://localhost:8090/departments
```

> **Payload**
```json
{
  "name" : "string"
}
```

> **Response**
```json
{
    "statusCode": 201,
    "success": true,
    "message": "Department created successfully",
    "data": {
        "id": 1,
        "name": "string",
        "status": "ACTIVE"
    }
}
```
---


2. **Retrieve Department** 

```http
GET http://localhost:8090/departments/{departmentId}
```
| Parameter | Type | Description |
| :--- | :--- | :--- |
| `departmentId` | `long` | **Required**. The department id |

> **Response**
```json
{
    "statusCode": 200,
    "success": true,
    "message": "Department retrieved successfully",
    "data": {
        "id": 1,
        "name": "string",
        "status": "ACTIVE"
    }
}
```
---


3. **Get all Departments** 

```http
GET http://localhost:8090/departments
```

> **Response**
```json
{
    "statusCode": 200,
    "success": true,
    "message": "Departments retrieved successfully",
    "data": [
        {
            "id": 1,
            "name": "string",
            "status": "ACTIVE"
        }
    ]
}
```
---


4. **Update Department** 

```http
UPDATE http://localhost:8090/departments/{departmentId}
```
| Parameter | Type | Description |
| :--- | :--- | :--- |
| `departmentId` | `long` | **Required**. The department id |

> **Payload**
```json
{
  "name" : "string"
}
```

> **Response**
```json
{
    "statusCode": 200,
    "success": true,
    "message": "Department updated successfully",
    "data": {
        "id": 1,
        "name": "string",
        "status": "ACTIVE"
    }
}
```
---


5. **Delete Department** 

```http
DELETE http://localhost:8090/departments/{departmentId}
```
| Parameter | Type | Description |
| :--- | :--- | :--- |
| `departmentId` | `long` | **Required**. The department id |

> **Response**
```json
{
    "statusCode": 200,
    "success": true,
    "message": "Department deleted successfully",
    "data": {
        "id": 1,
        "name": "string",
        "status": "DELETED"
    }
}
```


### Employees

1. **Create Employees** 

```http
POST http://localhost:8090/employees
```

> **Payload**
```json
{
	"firstName":"string",
	"lastName":"string",
	"employeeNumber":"string",
	"nationalID":"string",
	"department":{
            "id": 1
        }
}
```

> **Response**
```json
{
    "statusCode": 201,
    "success": true,
    "message": "Employee created successfully",
    "data": {
        "id": 1,
        "firstName": "string",
        "lastName": "string",
        "employeeNumber": "string",
        "nationalID": "string",
        "department": {
            "id": 1,
            "name": "string",
            "status": "ACTIVE"
        },
        "status": "ACTIVE"
    }
}
```
