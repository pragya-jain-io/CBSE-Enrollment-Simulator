package com.example.cbse_enrollment_simulator.controller

import com.example.cbse_enrollment_simulator.model.EnrollmentRequest
import com.example.cbse_enrollment_simulator.model.EnrollmentResponse
import com.example.cbse_enrollment_simulator.service.EnrollmentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

// It will handle web API requests
@RestController
// All requests handled here will be relative to the root path "/"
@RequestMapping("/")
class EnrollmentController(private val service: EnrollmentService) {

    // Handles POST requests to the "/enroll" URL, used for student enrollment
    @PostMapping("/enroll")

    fun enrollStudent(@RequestBody request: EnrollmentRequest): Mono<ResponseEntity<EnrollmentResponse>> {
        // Calls the service's enrollStudent method with the request data
        // Maps the result (a pair of HTTP status and response body) to a ResponseEntity object
        return service.enrollStudent(request)
            .map { (status, response) -> ResponseEntity.status(status).body(response) }
    }       // Creates the HTTP response with the correct status code and response body
}
