package com.example.cbse_enrollment_simulator.service

import com.example.cbse_enrollment_simulator.model.EnrollmentRequest
import com.example.cbse_enrollment_simulator.model.EnrollmentResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import kotlin.random.Random
import reactor.core.publisher.Mono

@Service
class EnrollmentService {
    private val enrolledAadhaars = ConcurrentHashMap.newKeySet<String>()

    fun enrollStudent(request: EnrollmentRequest): Mono<Pair<HttpStatus, EnrollmentResponse>> {
        if (Random.nextInt(100) < 10) {
            return Mono.just(HttpStatus.INTERNAL_SERVER_ERROR to
                    EnrollmentResponse("error", "Internal Server Error. Please retry."))
        }

        return if (enrolledAadhaars.contains(request.aadhaar)) {
            Mono.just(HttpStatus.CONFLICT to
                    EnrollmentResponse("failure", "Student Already Enrolled"))
        } else {
            enrolledAadhaars.add(request.aadhaar)
            Mono.just(HttpStatus.OK to
                    EnrollmentResponse("success", "Student Enrolled Successfully"))
        }
    }
}