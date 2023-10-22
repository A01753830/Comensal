package mx.grm.prototipo4.model.responses

/**
 * Customer's Registration Response Model
 *
 * This data class represents the response model for a customer's registration operation. It typically
 * contains a token or identifier as a result of a successful registration.
 *
 * @param token The registration token or identifier.
 *
 * @constructor Creates a `RegisterRes` with the provided registration token.
 * @sample token
 *
 * @authors Héctor González Sánchez
 * @authors Alfredo Azamar López
 */
data class RegisterRes(
    var token: Int
)
