package mx.grm.prototipo4.model

import mx.grm.prototipo4.model.requests.RegisterReq
import mx.grm.prototipo4.model.requests.SurveyReq
import mx.grm.prototipo4.model.responses.DiningItem
import mx.grm.prototipo4.model.responses.DiningNameRes
import mx.grm.prototipo4.model.responses.DiningStatusRes
import mx.grm.prototipo4.model.responses.MenuRes
import mx.grm.prototipo4.model.responses.RegisterRes
import mx.grm.prototipo4.model.responses.SurveyRes
import mx.grm.prototipo4.model.responses.TableStatusItem
import mx.grm.prototipo4.model.responses.vulCondRes
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Services (APIs) Pool
 * @author Héctor González Sánchez
 */


interface ListaServiciosAPI {
    @Headers("Content-Type: application/json")
    @POST("insertaComensalCond")
    fun uploadCustomer(@Body costumer: RegisterReq): Call<RegisterRes>

    @Headers("Content-Type: application/json")
    @POST("insertaEncuesta")
    fun uploadSurvey(@Body costumer: SurveyReq): Call<SurveyRes>

    @GET("condicionComensal")
    fun getVulSituations(): Call<vulCondRes>

    @GET("nombreComedores")
    fun getDiningNames(): Call<DiningNameRes>
    @GET("estadoCom/{nombreCom}")
    fun getDiningStat(@Path("nombreCom") diningName: DiningItem): Call<DiningStatusRes>

    @GET("verMenu/{nombreCom}/{fecha}")
    fun getMenuInfo(@Path("nombreCom") diningName: DiningItem, @Path("fecha") date: String): Call<MenuRes>
}