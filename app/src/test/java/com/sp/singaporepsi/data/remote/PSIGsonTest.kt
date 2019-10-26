package com.sp.singaporepsi.data.remote

import com.sp.singaporepsi.model.PSIInfo
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


internal class PSIGsonTest {

    val testSubject = APIServices.psiGson


    @Test
    fun `When using the gsonParser the given string should be parsed`(){
        //given
        val json = "{\"region_metadata\":[{\"name\":\"west\",\"label_location\":{\"latitude\":1.35735,\"longitude\":103.7}},{\"name\":\"national\",\"label_location\":{\"latitude\":0,\"longitude\":0}},{\"name\":\"east\",\"label_location\":{\"latitude\":1.35735,\"longitude\":103.94}},{\"name\":\"central\",\"label_location\":{\"latitude\":1.35735,\"longitude\":103.82}},{\"name\":\"south\",\"label_location\":{\"latitude\":1.29587,\"longitude\":103.82}},{\"name\":\"north\",\"label_location\":{\"latitude\":1.41803,\"longitude\":103.82}}],\"items\":[{\"timestamp\":\"2019-10-26T08:00:00+08:00\",\"update_timestamp\":\"2019-10-26T08:08:54+08:00\",\"readings\":{\"o3_sub_index\":{\"west\":1,\"national\":1,\"east\":1,\"central\":1,\"south\":1,\"north\":1},\"pm10_twenty_four_hourly\":{\"west\":30,\"national\":33,\"east\":32,\"central\":24,\"south\":33,\"north\":26},\"pm10_sub_index\":{\"west\":30,\"national\":33,\"east\":32,\"central\":24,\"south\":33,\"north\":26},\"co_sub_index\":{\"west\":7,\"national\":11,\"east\":10,\"central\":6,\"south\":8,\"north\":11},\"pm25_twenty_four_hourly\":{\"west\":15,\"national\":18,\"east\":15,\"central\":15,\"south\":18,\"north\":15},\"so2_sub_index\":{\"west\":12,\"national\":15,\"east\":4,\"central\":3,\"south\":15,\"north\":7},\"co_eight_hour_max\":{\"west\":0.7,\"national\":1.06,\"east\":1.02,\"central\":0.58,\"south\":0.79,\"north\":1.06},\"no2_one_hour_max\":{\"west\":21,\"national\":47,\"east\":40,\"central\":21,\"south\":47,\"north\":34},\"so2_twenty_four_hourly\":{\"west\":19,\"national\":24,\"east\":7,\"central\":5,\"south\":24,\"north\":11},\"pm25_sub_index\":{\"west\":54,\"national\":58,\"east\":55,\"central\":54,\"south\":58,\"north\":54},\"psi_twenty_four_hourly\":{\"west\":54,\"national\":58,\"east\":55,\"central\":54,\"south\":58,\"north\":54},\"o3_eight_hour_max\":{\"west\":2,\"national\":3,\"east\":2,\"central\":3,\"south\":2,\"north\":1}}}],\"api_info\":{\"status\":\"healthy\"}}"

        //when
        val psiInfo = testSubject.fromJson(json, PSIInfo::class.java)

        //then
        assertEquals(psiInfo.api_info.status,"healthy")
    }

}