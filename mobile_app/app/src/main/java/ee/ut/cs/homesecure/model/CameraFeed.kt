package ee.ut.cs.homesecure.model

import com.squareup.moshi.Json

data class CameraFeed (
    val id: String,
    @Json(name = "cameraIp") val cameraIp: String
    )
