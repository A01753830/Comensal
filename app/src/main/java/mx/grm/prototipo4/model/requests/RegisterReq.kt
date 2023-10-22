package mx.grm.prototipo4.model.requests

import com.google.gson.annotations.SerializedName

/**
 * Customer's Registration Request Model
 *
 * This data class represents the request model for registering a customer. It includes various
 * details such as the customer's name, last name, date of birth, gender, and vulnerable situations.
 *
 * @property name The first name of the customer.
 * @property p_lastName The primary last name of the customer.
 * @property m_lastName The secondary last name of the customer.
 * @property curp The CURP (Clave Única de Registro de Población) of the customer.
 * @property bDate The date of birth of the customer in the format "yyyy-MM-dd".
 * @property gender The gender of the customer.
 * @property vulSituation An array of vulnerable situations or conditions.
 *
 * @constructor Creates a `RegisterReq` request with the specified customer details.
 * @param name The first name of the customer.
 * @param p_lastName The primary last name of the customer.
 * @param m_lastName The secondary last name of the customer.
 * @param curp The CURP of the customer.
 * @param bDate The date of birth of the customer in the format "yyyy-MM-dd".
 * @param gender The gender of the customer.
 * @param vulSituation An array of vulnerable situations or conditions.
 *
 * @authors Héctor González Sánchez
 * @authors Alfredo Azamar López
 */

data class RegisterReq(
    @SerializedName("nombre") var name: String,
    @SerializedName("apellidoP") var p_lastName: String,
    @SerializedName("apellidoM") var m_lastName: String,
    @SerializedName("curp") var curp: String,
    @SerializedName("fechaNacim") var bDate: String,
    @SerializedName("sexo") var gender: String,
    @SerializedName("nombreCond") var vulSituation: Array<String>
)
