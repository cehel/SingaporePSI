package com.sp.singaporepsi.data.remote

import com.google.gson.GsonBuilder
import java.lang.reflect.Modifier

object APIServices {

    val psiAPI by lazy { PsiAPI.create() }

    val psiGson by lazy { GsonBuilder()
        .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.STATIC)
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        .create() }

    val psiDataSourceRemote by lazy { PSIDataSourceRemote() }

}