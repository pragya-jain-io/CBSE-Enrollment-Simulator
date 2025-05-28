package com.example.cbse_enrollment_simulator.controller
import com.example.cbse_enrollment_simulator.model.EnrollmentRequest
import com.example.cbse_enrollment_simulator.model.EnrollmentResponse
import com.example.cbse_enrollment_simulator.service.EnrollmentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api")
class EnrollmentController(private val service: EnrollmentService) {

    @PostMapping("/enroll")
    fun enrollStudent(@RequestBody request: EnrollmentRequest): Mono<ResponseEntity<EnrollmentResponse>> {
        return service.enrollStudent(request)
            .map { (status, response) -> ResponseEntity.status(status).body(response) }
    }
}
