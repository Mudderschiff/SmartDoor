package ee.ut.cs.homesecure.model

import com.squareup.moshi.Json

data class Sensor (
    val id: String,
    @Json(name = "alerts") val alerts: String
    )
