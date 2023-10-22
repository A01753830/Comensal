package mx.grm.prototipo4.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.grm.prototipo4.model.ListaServiciosAPI
import mx.grm.prototipo4.model.requests.RegisterReq
import mx.grm.prototipo4.model.responses.RegisterRes
import mx.grm.prototipo4.model.RetrofitManager
import mx.grm.prototipo4.model.responses.vulCondItem
import mx.grm.prototipo4.model.responses.vulCondRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * HomeViewModel - ViewModel for the Customer's Auto-Registration Fragment.
 *
 * This ViewModel manages data and business logic related to the customer's auto-registration process.
 * It communicates with the API to upload customer registration data and retrieve vulnerable conditions data.
 *
 * @property apiCall - An instance of the ListaServiciosAPI interface for making API calls.
 * @property vulCondList - A MutableLiveData that holds a list of vulnerable condition items.
 * @property customerToken - A MutableLiveData that holds the customer's authentication token.
 *
 * @authors Héctor González Sánchez
 * @authors Alfredo Azamar López
 */

class HomeViewModel : ViewModel() {

    private val apiCall: ListaServiciosAPI = RetrofitManager.apiService
    val vulCondList = MutableLiveData<List<vulCondItem>>()
    var customerToken = MutableLiveData<String>()

    /**
     * Uploads customer registration data to the API.
     *
     * @param name - The customer's first name.
     * @param p_lastName - The customer's paternal last name.
     * @param m_lastName - The customer's maternal last name.
     * @param curp - The customer's CURP (Clave Única de Registro de Población).
     * @param bDate - The customer's birthdate.
     * @param gender - The customer's gender.
     * @param vulSituation - An array of vulnerable situations.
     * @param callback - A callback function to handle the result of the registration process.
     */
    fun uploadCostumer(name: String, p_lastName: String, m_lastName: String,
                       curp: String, bDate: String, gender: String,
                       vulSituation: Array<String>, callback: (Boolean) -> Unit) {

        val requestBody = RegisterReq(name, p_lastName, m_lastName, curp, bDate, gender, vulSituation)

        apiCall.uploadCustomer(requestBody).enqueue(object : Callback<RegisterRes> {

            override fun onResponse(call: Call<RegisterRes>, response: Response<RegisterRes>) {
                if(response.isSuccessful) {
                    println("Mensaje: ${response.body()}")
                    customerToken.value = response.body()?.token.toString()
                    callback(true)
                } else {
                    println("Falla: ${response.code()}")
                    println("Error: ${response.errorBody()?.string()}")
                    callback(false)
                }
            }

            override fun onFailure(call: Call<RegisterRes>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
                t.printStackTrace()
                callback(false)
            }

        })
    }

    /**
     * Retrieves vulnerable condition data from the API.
     */
    fun getVulSituations() {
        val call = apiCall.getVulSituations()
        call.enqueue(object: Callback<vulCondRes> {
            override fun onResponse(call: Call<vulCondRes>, response: Response<vulCondRes>) {
                if(response.isSuccessful) {
                    val vulConRes = response.body()
                    vulConRes?.let {
                        vulCondList.value = it.table
                    }
                    println(vulCondList)
                } else {
                    println("Falla: ${response.code()}")
                    println("Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<vulCondRes>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
                t.printStackTrace()
            }

        })
    }

}