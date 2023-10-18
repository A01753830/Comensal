package mx.grm.prototipo4.ui.gallery

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.grm.prototipo4.model.ListaServiciosAPI
import mx.grm.prototipo4.model.MyDate
import mx.grm.prototipo4.model.RetrofitManager
import mx.grm.prototipo4.model.responses.DiningItem
import mx.grm.prototipo4.model.responses.DiningNameRes
import mx.grm.prototipo4.model.responses.DiningStatusRes
import mx.grm.prototipo4.model.responses.MenuRes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Menu Frag ViewModel
 * @author Héctor González Sánchez
 */

class GalleryViewModel : ViewModel() {

    val _diningNames = MutableLiveData<List<DiningItem>>()
    val diningNames: LiveData<List<DiningItem>> get() = _diningNames

    val menuMessage = MutableLiveData<String>()

    val soup = MutableLiveData<String>()
    val mainCourse = MutableLiveData<String>()
    val carbs = MutableLiveData<String>()
    val water = MutableLiveData<String>()
    val beansSauce = MutableLiveData<String>()

    val diningStatus = MutableLiveData<String>()

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

    fun getMenu(diningName: DiningItem) {

        val date = MyDate().getCurrentDate()
        println(date)
        //val date = "2023-09-12"
        apiCall.getMenuInfo(diningName, date).enqueue(object: Callback<MenuRes> {

            override fun onResponse(call: Call<MenuRes>, response: Response<MenuRes>) {
                if (response.isSuccessful) {
                    if (response.body()?.table?.size == 0){
                        menuMessage.value = "No se ha subido el menú"
                        soup.value = ""
                        mainCourse.value = ""
                        carbs.value = ""
                        water.value = ""
                        beansSauce.value = ""
                    } else{
                        menuMessage.value = "Menú del dia:"
                        soup.value = response.body()?.table?.get(0)?.SopaArroz
                        mainCourse.value = response.body()?.table?.get(0)?.PlatoFuerte
                        carbs.value = response.body()?.table?.get(0)?.PanTortilla
                        water.value = response.body()?.table?.get(0)?.AguaDelDia
                        beansSauce.value = response.body()?.table?.get(0)?.FrijolesSalsa
                    }
                    println("ÉXITO")
                } else {
                    println("Falla: ${response.code()}")
                    println("Error: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<MenuRes>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
                t.printStackTrace()
            }

        })
    }

    fun getDiningStatus(diningName: DiningItem) {

        apiCall.getDiningStat(diningName).enqueue(object: Callback<DiningStatusRes> {
            override fun onResponse(call: Call<DiningStatusRes>, response: Response<DiningStatusRes>) {
                if (response.isSuccessful) {
                    diningStatus.value = response.body()?.table?.get(0)?.Nombre
                    println(diningStatus.value)
                } else {
                    println("Falla: ${response.code()}")
                    println("Error: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<DiningStatusRes>, t: Throwable) {
                println("ERROR: ${t.localizedMessage}")
                t.printStackTrace()
            }
        })
    }

}