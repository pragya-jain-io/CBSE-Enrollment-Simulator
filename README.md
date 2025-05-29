# CBSE Enrollment Simulator (Mock API)

A Kotlin-based Spring WebFlux microservice to simulate CBSE student enrollment verification.

## Features

- Accepts POST requests with Aadhaar info
- Returns:
    - 200 OK: Student enrolled successfully
    - 409 Conflict: Student already enrolled
    - 500 Error: Simulated internal error for retry logic testing
- Stateless, in-memory simulation

## Sample Request

```bash
curl -X POST http://localhost:8081/enroll \
-H "Content-Type: application/json" \
-d '{ "aadhaar": "123456789012","rollNo": "1001","name": "Name","studentClass": "10","dob": "2012-12-31","school": "ABC"}'
