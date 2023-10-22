package mx.grm.prototipo4.model.requests

import com.google.gson.annotations.SerializedName

/**
 * Survey Request Model
 *
 * This data class represents the request model for submitting a survey. It includes various
 * details such as the dining location name, the survey question, comments, score, and the date of
 * submission.
 *
 * @property diningName The name of the dining location.
 * @property question The survey question.
 * @property comments Any additional comments or feedback from the survey.
 * @property score The numeric score or rating given in the survey.
 * @property date The date of the survey submission in the format "yyyy-MM-dd".
 *
 * @constructor Creates a `SurveyReq` request with the specified survey details.
 * @param diningName The name of the dining location.
 * @param question The survey question.
 * @param comments Any additional comments or feedback from the survey.
 * @param score The numeric score or rating given in the survey.
 * @param date The date of the survey submission in the format "yyyy-MM-dd".
 *
 * @authors Héctor González Sánchez
 * @authors Alfredo Azamar López
 */

data class SurveyReq(
    @SerializedName("nombreCom") var diningName: String,
    @SerializedName("pregunta") var question: String,
    @SerializedName("comentarios") var comments: String,
    @SerializedName("cali") var score: Float,
    @SerializedName("fecha") var date: String,
)
