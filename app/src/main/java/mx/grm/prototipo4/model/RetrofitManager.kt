package mx.grm.prototipo4.model

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Retrofit Manager
 *
 * This object manages the creation and configuration of the Retrofit instance, which is used
 * for making network requests to a remote server. It provides access to the API service interface
 * for communication with the server.
 *
 * @property retrofit The Retrofit instance used for network communication.
 * @property apiService The API service interface for defining API endpoints and making network requests.
 * @sample retrofit2.Retrofit
 * @sample retrofit2.converter.gson.GsonConverterFactory
 * @sample com.google.gson.GsonBuilder
 *
 * @see ListaServiciosAPI
 * @see retrofit2.Retrofit.Builder
 * @see retrofit2.Retrofit.Builder.baseUrl
 * @see retrofit2.Retrofit.Builder.addConverterFactory
 *
 * @return Unit
 *
 * @authors Héctor González Sánchez
 * @authors Alfredo Azamar López
 */

object RetrofitManager {
    /**
     * Retrofit instance initialization
     */
    private val retrofit: Retrofit by lazy {
        // Create and configure the Retrofit instance
        Retrofit.Builder()
            .baseUrl("https://platopatodose3.ddns.net:8080/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }

    /**
     * API service interface
     *
     * Provides access to the API service interface for making network requests.
     */
    val apiService: ListaServiciosAPI by lazy {
        // Create the API service
        retrofit.create(ListaServiciosAPI::class.java)
    }
}