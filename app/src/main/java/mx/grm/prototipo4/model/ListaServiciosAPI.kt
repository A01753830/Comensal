package mx.grm.prototipo4.model

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface ListaServiciosAPI {
    @Headers("Content-Type: application/json")
    @POST("insertaComensalCond")
    fun uploadCustomer(@Body costumer: RegisterReq): Call<RegisterRes>

    @GET("condicionComensal")
    fun getVulSituations(): Call<vulCondRes>
}