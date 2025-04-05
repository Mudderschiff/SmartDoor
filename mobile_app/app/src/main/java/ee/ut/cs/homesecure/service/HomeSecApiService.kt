package ee.ut.cs.homesecure.service

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import ee.ut.cs.homesecure.model.UnLock
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT


private const val BASE_URL = "http://172.17.90.111:8080/"

/**
 * Build the Moshi object with Kotlin adapter factory that Retrofit will be using.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

/**
 * The Retrofit object with the Moshi converter and scala converter.
 */
private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface HomeSecApiService {
    @GET("cameraAddress")
    suspend fun getCameraAddress() : String

    @PUT("home/unlock")
    fun unLockDoor( @Body unLock: UnLock): Call<UnLock?>?


    @GET("alerts/motion")
    suspend fun getMotionAlerts() : String

    @GET("alerts/knock")
    suspend fun getKnockAlerts() : String

    @GET("alerts/rfid")
    suspend fun getRfidAlerts() : String
}

object HomeSecureApi {
    val retrofitService: HomeSecApiService by lazy { retrofit.create(HomeSecApiService::class.java) }
}
