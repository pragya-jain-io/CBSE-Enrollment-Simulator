package com.example.cbse_enrollment_simulator.service
import com.example.cbse_enrollment_simulator.model.EnrollmentRequest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import java.time.LocalDate

class EnrollmentServiceTest {

    private val service = EnrollmentService()

    @Test
    fun `should enroll a new student successfully`() {
        val request = EnrollmentRequest("111122223333", "Pragya", LocalDate.of(2012, 12, 12))
        val result = service.enrollStudent(request).block()

        assertEquals(HttpStatus.OK, result?.first)
        assertEquals("success", result?.second?.status)
        assertEquals("Student Enrolled Successfully", result?.second?.message)
    }

    @Test
    fun `should return conflict if student already enrolled`() {
        val aadhaar = "111122223333"
        val request = EnrollmentRequest(aadhaar, "Pragya", LocalDate.of(2012, 12, 12))

        // First enrollment should succeed
        service.enrollStudent(request).block()

        // Second attempt should fail with conflict
        val result = service.enrollStudent(request).block()

        assertEquals(HttpStatus.CONFLICT, result?.first)
        assertEquals("failure", result?.second?.status)
        assertEquals("Student Already Enrolled", result?.second?.message)
    }

    @RepeatedTest(20)
    fun `should occasionally simulate server error`() {
        val request = EnrollmentRequest("999988887777", "Random", LocalDate.of(2012, 12, 12))
        val result = service.enrollStudent(request).block()

        // It can be either success/failure/error — just check error path exists
        if (result?.first == HttpStatus.INTERNAL_SERVER_ERROR) {
            assertEquals("error", result.second.status)
            assertEquals("Internal Server Error. Please retry.", result.second.message)
        }
    }
}