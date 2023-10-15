package mx.grm.prototipo4.model

import com.google.gson.annotations.SerializedName

data class RegisterReq(
    @SerializedName("nombre") var name: String,
    @SerializedName("apellidoP") var p_lastName: String,
    @SerializedName("apellidoM") var m_lastName: String,
    @SerializedName("curp") var curp: String,
    @SerializedName("fechaNacim") var bDate: String,
    @SerializedName("sexo") var gender: String,
    @SerializedName("nombreCond") var vulSituation: Array<String>
)
