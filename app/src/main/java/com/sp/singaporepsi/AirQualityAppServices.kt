package com.sp.singaporepsi

import com.google.gson.GsonBuilder
import com.sp.singaporepsi.data.remote.PSIDataSourceRemote
import com.sp.singaporepsi.data.remote.PsiAPI
import java.lang.reflect.Modifier

/**
 * This object is used in this small application to manage and instantiate dependencies.
 * In a larger project, I would recommend to use a dependency injection framework like Dagger2.
 * It is also easier to test applications when using Dagger2
 *
 */
object AirQualityAppServices {

    val psiAPI by lazy { PsiAPI.create() }

    val psiGson by lazy { GsonBuilder()
        .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC)
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        .create() }

    val psiDataSourceRemote by lazy { PSIDataSourceRemote(psiAPI) }

}