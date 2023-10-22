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
 * GalleryViewModel - ViewModel for the Menu Fragment.
 *
 * This ViewModel is responsible for managing data and business logic related to displaying the menu and dining status
 * information in the Menu Fragment. It interacts with the API to retrieve dining names, menu data, and dining status.
 *
 * @property _diningNames - A MutableLiveData that holds a list of DiningItem objects.
 * @property diningNames - A LiveData property that exposes the list of dining location names to observers.
 * @property menuMessage - A MutableLiveData that holds a message about the menu availability.
 * @property soup - A MutableLiveData that holds the soup of the day.
 * @property mainCourse - A MutableLiveData that holds the main course of the day.
 * @property carbs - A MutableLiveData that holds the type of carbohydrates (e.g., bread) of the day.
 * @property water - A MutableLiveData that holds the type of water of the day.
 * @property beansSauce - A MutableLiveData that holds the type of beans or sauce of the day.
 * @property diningStatus - A MutableLiveData that holds the dining status information.
 * @property apiCall - An instance of the ListaServiciosAPI interface to make API calls.
 *
 * @author Héctor González Sánchez
 * @author Alfredo Azamamr López
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
     * Retrieves and updates the menu items for a specific dining location and date from the API.
     *
     * @param diningName - The selected dining location.
     */
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

    /**
     * Retrieves and updates the dining status information for a specific dining location from the API.
     *
     * @param diningName - The selected dining location.
     */
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