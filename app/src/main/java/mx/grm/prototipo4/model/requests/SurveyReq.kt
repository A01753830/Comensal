package mx.grm.prototipo4.model.requests

import com.google.gson.annotations.SerializedName

/**
 * Survey Request Model
 * @author Héctor González Sánchez
 */

data class SurveyReq(
    @SerializedName("nombreCom") var diningName: String,
    @SerializedName("pregunta") var question: String,
    @SerializedName("comentarios") var comments: String,
    @SerializedName("cali") var score: Float,
    @SerializedName("fecha") var date: String,
)
