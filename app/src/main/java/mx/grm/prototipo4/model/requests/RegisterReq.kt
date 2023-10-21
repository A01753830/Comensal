package mx.grm.prototipo4.model.requests

import com.google.gson.annotations.SerializedName

/**
 * Customer's registration Request Model
 * @author Héctor González Sánchez
 * @author Alfredo Azamar López
 */

data class RegisterReq(
    @SerializedName("nombre") var name: String,
    @SerializedName("apellidoP") var p_lastName: String,
    @SerializedName("apellidoM") var m_lastName: String,
    @SerializedName("curp") var curp: String,
    @SerializedName("fechaNacim") var bDate: Int,
    @SerializedName("sexo") var gender: String,
    @SerializedName("nombreCond") var vulSituation: Array<String>
)
