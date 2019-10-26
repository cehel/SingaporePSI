package com.sp.singaporepsi.data.remote

import com.sp.singaporepsi.model.PSIInfo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import retrofit2.Response


internal class PSIServivceTest {

    val testSubject = APIServices.psiAPI

    @Test
    @Disabled //This test is just helpful for implementation, a test should not call a remote API
    fun `When PSIApi call is executed`(){
        //given
        val callPsi = testSubject.getPsiInfo()

        //when
        val response: Response<PSIInfo> = callPsi.execute()

        //then
        val psiInfo: PSIInfo = response.body() as PSIInfo
        assertEquals("healthy", psiInfo.api_info.status)
    }

}