package mx.grm.prototipo4.model.responses

/**
 * Dining Status Response Model
 *
 * This data class represents the response model for retrieving a list of dining locations' status.
 * It includes a list of `TableStatusItem` objects, where each object holds the status of a dining location.
 *
 * @property table A list of dining status items, each containing the status of a dining location.
 *
 * @constructor Creates a `DiningStatusRes` response with the provided list of dining status items.
 * @param table A list of dining status items, each containing the status of a dining location.
 *
 * @authors Héctor González Sánchez
 * @authors Alfredo Azamar López
 */
data class DiningStatusRes(
    var table: List<TableStatusItem>
)

/**
 * Dining Status Item Model
 *
 * This data class represents a dining location status item with its name.
 *
 * @property Nombre The status of the dining location.
 *
 * @constructor Creates a `TableStatusItem` with the provided dining location status.
 * @param Nombre The status of the dining location.
 *
 * @authors Héctor González Sánchez
 * @authors Alfredo Azamar López
 */
data class TableStatusItem(
    var Nombre: String
)
