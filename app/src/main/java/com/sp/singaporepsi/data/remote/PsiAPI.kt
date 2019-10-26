package com.sp.singaporepsi.data.remote

import com.sp.singaporepsi.model.PSIInfo
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface PsiAPI {

    @GET("environment/psi")
    fun getPsiInfo(): Call<PSIInfo>


    companion object {

        fun create(): PsiAPI {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.data.gov.sg/v1/")
                .addConverterFactory(GsonConverterFactory.create(APIServices.psiGson))
                .build()

            return retrofit.create(PsiAPI::class.java)
        }
    }

}