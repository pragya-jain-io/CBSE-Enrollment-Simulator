package com.example.cbse_enrollment_simulator.service

import com.example.cbse_enrollment_simulator.model.EnrollmentRequest
import com.example.cbse_enrollment_simulator.model.EnrollmentResponse
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import kotlin.random.Random
import reactor.core.publisher.Mono
import org.slf4j.LoggerFactory


@Service
class EnrollmentService {

    private val logger = LoggerFactory.getLogger(EnrollmentService::class.java)

    // A thread-safe set to store Aadhaar IDs of students who are already enrolled
    private val enrolledAadhaars = ConcurrentHashMap.newKeySet<String>()

    // Function to handle student enrollment asynchronously
    // Takes an EnrollmentRequest object and returns a Mono of Pair (HTTP status and response body)
    fun enrollStudent(request: EnrollmentRequest): Mono<Pair<HttpStatus, EnrollmentResponse>> {

        logger.info("Received enrollment request: Aadhaar=${request.aadhaar}, Name=${request.name}, DOB=${request.dob}")


        // Randomly simulate a 10% chance of a server error to test retry logic in clients
        if (Random.nextInt(100) < 10) {
            logger.warn("Simulated internal server error for Aadhaar=${request.aadhaar}")
            return Mono.just(HttpStatus.INTERNAL_SERVER_ERROR to
                    EnrollmentResponse("error", "Internal Server Error. Please retry."))
        }

        // Check if the Aadhaar is already in the enrolled set
        return if (enrolledAadhaars.contains(request.aadhaar)) {
            logger.info("Aadhaar=${request.aadhaar} is already enrolled")
            // If already enrolled, respond with 409 Conflict and a failure message
            Mono.just(HttpStatus.CONFLICT to
                    EnrollmentResponse("failure", "Student Already Enrolled"))
        } else {
            logger.info("Aadhaar=${request.aadhaar} enrolled successfully")
            // If not enrolled, add Aadhaar to the set and respond with 200 OK and success message
            enrolledAadhaars.add(request.aadhaar)
            Mono.just(HttpStatus.OK to
                    EnrollmentResponse("success", "Student Enrolled Successfully"))
        }
    }
}