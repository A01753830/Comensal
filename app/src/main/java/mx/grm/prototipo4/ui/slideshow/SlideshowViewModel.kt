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
 * SlideshowViewModel - ViewModel for the Survey Fragment.
 *
 * This ViewModel is responsible for managing data and business logic related to the survey functionality
 * in the Survey Fragment. It interacts with the API to retrieve dining location names and to upload
 * survey responses.
 *
 * @property _diningNames - A MutableLiveData that holds a list of DiningItem objects.
 * @property diningNames - A LiveData property that exposes the list of dining location names to observers.
 * @property apiCall - An instance of the ListaServiciosAPI interface to make API calls.
 *
 * @authors Héctor González Sánchez
 * @authors Alfredo Azamar López
 */

class SlideshowViewModel : ViewModel() {

    val _diningNames = MutableLiveData<List<DiningItem>>()
    val diningNames: LiveData<List<DiningItem>> get() = _diningNames

    private val apiCall: ListaServiciosAPI = RetrofitManager.apiService

    /**
     * Downloads the list of dining location names from the API.
     */
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

    /**
     * Uploads a survey response to the API.
     *
     * @param diningName - The name of the dining location.
     * @param question - The survey question.
     * @param comments - User comments (or "N/A" if empty).
     * @param score - The user's rating for the question.
     */
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