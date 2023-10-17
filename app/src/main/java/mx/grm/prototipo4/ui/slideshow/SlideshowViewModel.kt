package mx.grm.prototipo4.ui.slideshow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.grm.prototipo4.model.ListaServiciosAPI
import mx.grm.prototipo4.model.MyDate
import mx.grm.prototipo4.model.RetrofitManager
import mx.grm.prototipo4.model.requests.SurveyReq
import mx.grm.prototipo4.model.responses.DiningItem
import mx.grm.prototipo4.model.responses.DiningNameRes
import mx.grm.prototipo4.model.responses.SurveyRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Survey Frag ViewModel
 * @author Héctor González Sánchez
 */

class SlideshowViewModel : ViewModel() {

    val _diningNames = MutableLiveData<List<DiningItem>>()
    val diningNames: LiveData<List<DiningItem>> get() = _diningNames

    private val apiCall: ListaServiciosAPI = RetrofitManager.apiService


    fun downloadDiningNames() {

        apiCall.getDiningNames().enqueue(object: Callback <DiningNameRes> {
            override fun onResponse(call: Call<DiningNameRes>, response: Response<DiningNameRes>) {
                if (response.isSuccessful) {
                    val diningNameRes = response.body()
                    diningNameRes?.let {
                        _diningNames.postValue(it.table)
                    }
                } else {
                    println("Falla: ${response.code()}")
                    println("Error: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<DiningNameRes>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
            }

        })
    }

    fun uploadSurvey(diningName: String, question: String,
                     comments: String, score: Float){

        val date = MyDate().getCurrentDate()
        val requestBody = SurveyReq(diningName, question, comments, score, date)

        apiCall.uploadSurvey(requestBody).enqueue(object: Callback <SurveyRes> {

            override fun onResponse(call: Call<SurveyRes>, response: Response<SurveyRes>) {
                if (response.isSuccessful) {
                    println("Mensaje: ${response.body()}")
                } else{
                    println("Falla: ${response.code()}")
                    println("Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<SurveyRes>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
                t.printStackTrace()
            }

        })
    }
}