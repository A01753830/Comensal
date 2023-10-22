package mx.grm.prototipo4.model.responses

/**
 * Dining Names Response Model
 *
 * This data class represents the response model for retrieving a list of dining locations' names.
 * It includes a list of `DiningItem` objects, where each object holds the name of a dining location.
 *
 * @property table A list of dining items, each containing the name of a dining location.
 *
 * @constructor Creates a `DiningNameRes` response with the provided list of dining items.
 * @param table A list of dining items, each containing the name of a dining location.
 *
 * @authors Héctor González Sánchez
 * @authors Alfredo Azamar López
 */
data class DiningNameRes (
    val table: List<DiningItem>
)

/**
 * Dining Item Model
 *
 * This data class represents a dining location item with its name.
 *
 * @property Nombre The name of the dining location.
 *
 * @constructor Creates a `DiningItem` with the provided dining location name.
 * @param Nombre The name of the dining location.
 *
 * @authors Héctor González Sánchez
 * @authors Alfredo Azamar López
 */
data class DiningItem(
    var Nombre: String
){
    /**
     * Returns a string representation of the dining location item, which is its name.
     */
    override fun toString(): String {
        return Nombre
    }
}
