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
-d '{"aadhaar": "123456789012", "name": "Name", "dob": "2004-09-18"}'
