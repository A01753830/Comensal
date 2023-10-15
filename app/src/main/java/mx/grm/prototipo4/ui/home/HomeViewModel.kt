package mx.grm.prototipo4.ui.home

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.grm.prototipo4.model.ListaServiciosAPI
import mx.grm.prototipo4.model.RegisterReq
import mx.grm.prototipo4.model.RegisterRes
import mx.grm.prototipo4.model.RetrofitManager
import mx.grm.prototipo4.model.vulCondItem
import mx.grm.prototipo4.model.vulCondRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val apiCall: ListaServiciosAPI = RetrofitManager.apiService
    val vulCondList = MutableLiveData<List<vulCondItem>>()

    fun uploadCostumer(name: String, p_lastName: String, m_lastName: String,
                       curp: String, bDate: String, gender: String, vulSituation: Array<String>) {

        val requestBody = RegisterReq(name, p_lastName, m_lastName, curp, bDate, gender, vulSituation)

        apiCall.uploadCustomer(requestBody).enqueue(object : Callback<RegisterRes> {

            override fun onResponse(call: Call<RegisterRes>, response: Response<RegisterRes>) {
                if(response.isSuccessful) {
                    println("Mensaje: ${response.body()}")
                } else {
                    println("Falla: ${response.code()}")
                    println("Error: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<RegisterRes>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
                t.printStackTrace()
            }

        })
    }

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