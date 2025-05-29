package com.example.cbse_enrollment_simulator.service
import com.example.cbse_enrollment_simulator.model.EnrollmentRequest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.time.LocalDate

class EnrollmentServiceTest {


    // Creating an instance of the service to be tested
    private val service = EnrollmentService()

    // Test case 1: New student should get enrolled successfully
    @Test
    fun `enroll a new student successfully`() {
        // Creating a test request for a new student
        val request = EnrollmentRequest(
            aadhaar = "123456789011",
            rollNo = "1001",
            name = "Pragya",
            studentClass = "10",
            school = "ABC Public School",
            dob = LocalDate.of(2012, 12, 12)
        )
        // Calling the service method and blocking to get the result
        val result = service.enrollStudent(request).block()

        // Checking if the response status is HTTP 200 OK
        assertEquals(HttpStatus.OK, result?.first)

        // Checking if the result status is "success"
        assertEquals("success", result?.second?.status)

        // Checking if the message confirms enrollment
        assertEquals("Student Enrolled Successfully", result?.second?.message)
    }

    // Test case 2: Same student should not be enrolled twice
    @Test
    fun `return conflict if student already enrolled`() {
        val request = EnrollmentRequest(
            aadhaar = "123456789012",
            rollNo = "1001",
            name = "Pragya",
            studentClass = "10",
            school = "ABC Public School",
            dob = LocalDate.of(2012, 12, 12)
        )
        // First enrollment should succeed
        service.enrollStudent(request).block()

        // Second attempt should fail with conflict
        val result = service.enrollStudent(request).block()

        assertEquals(HttpStatus.CONFLICT, result?.first)
        assertEquals("failure", result?.second?.status)
        assertEquals("Student Already Enrolled", result?.second?.message)
    }

    // Test case 3: Random server error simulation should happen sometimes
    @RepeatedTest(20)
    fun `occasionally simulate server error`() {
        val request = EnrollmentRequest(
            aadhaar = "123456789013",
            rollNo = "1001",
            name = "Pragya",
            studentClass = "10",
            school = "ABC Public School",
            dob = LocalDate.of(2012, 12, 12)
        )
        val result = service.enrollStudent(request).block()

        // It can be either success/failure/error — just check error path exists
        if (result?.first == HttpStatus.INTERNAL_SERVER_ERROR) {
            assertEquals("error", result.second.status)
            assertEquals("Internal Server Error. Please retry.", result.second.message)
        }
    }
}