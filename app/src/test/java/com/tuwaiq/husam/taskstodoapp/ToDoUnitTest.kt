package com.tuwaiq.husam.taskstodoapp

import com.google.common.truth.Truth
import com.tuwaiq.husam.taskstodoapp.data.repositories.MockRepo
import com.wajahatkarim3.easyvalidation.core.view_ktx.validEmail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ToDoUnitTest {

    @Before
    fun startUp(){

    }
    @Test
     fun emailInvalid() {
        val emailNotValid = "email.com"
        Truth.assertThat(emailNotValid.validEmail()).isFalse()
    }
    @Test
    fun emailValid() {
        val email = "email@em.com"
        Truth.assertThat(email.validEmail()).isTrue()
    }
}