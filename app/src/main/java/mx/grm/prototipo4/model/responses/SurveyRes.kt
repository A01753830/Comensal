package mx.grm.prototipo4.model.responses

/**
 * Survey Response Model
 *
 * This data class represents the response model for submitting a survey or feedback.
 * It includes a simple message indicating the result of the survey submission.
 *
 * @property message A message indicating the result of the survey submission.
 *
 * @constructor Creates a `SurveyRes` response with the provided message.
 * @param message A message indicating the result of the survey submission.
 *
 * @authors Héctor González Sánchez
 * @authors Alfredo Azamar López
 */

data class SurveyRes(
    var message: String
)
