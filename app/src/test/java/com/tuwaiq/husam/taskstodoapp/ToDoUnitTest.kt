package com.tuwaiq.husam.taskstodoapp

import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ToDoUnitTest {

    /*@Before
    fun startUp() {

    }*/
    private val email = "valid@gmail.com"
    private val emailNotValid = "invalid.com"
    private val nullableEmail = ""

    @Test
    fun nullableEmail(){
        assertNotNull(nullableEmail)
    }

    @Test
    fun emailInvalid() {
        assertFalse(emailNotValid.validEmail())
    }

    @Test
    fun emailValid() {
        assertTrue(email.validEmail())
    }
}