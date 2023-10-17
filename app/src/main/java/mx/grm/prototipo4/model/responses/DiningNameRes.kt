package mx.grm.prototipo4.model.responses

/**
 * Dining Names Response Model
 * @author Héctor González Sánchez
 */

data class DiningNameRes (
    val table: List<DiningItem>
)

data class DiningItem(
    var Nombre: String
){
    override fun toString(): String {
        return Nombre
    }
}
