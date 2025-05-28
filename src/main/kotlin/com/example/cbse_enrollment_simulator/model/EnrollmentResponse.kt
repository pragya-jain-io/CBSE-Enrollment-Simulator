package com.example.cbse_enrollment_simulator.model

    // Data structure for the response after enrollment attempt
data class EnrollmentResponse(
    val status: String, // Status of enrollment like "success", "failure", or "error"
    val message: String // Message explaining the status (e.g. "Student Enrolled Successfully")
)
