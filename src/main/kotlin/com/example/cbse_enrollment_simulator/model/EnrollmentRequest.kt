package com.example.cbse_enrollment_simulator.model

import java.time.LocalDate

// Data structure for the request when a student wants to enroll
data class EnrollmentRequest(
    val aadhaar: String, // Unique Aadhaar ID of the student, acts like an identity card number
    val rollNo: String,
    val name: String, // Student's full name
    val studentClass: String,
    val school: String,
    val dob: LocalDate // Date of birth of the student in format "yyyy-MM-dd"
)
