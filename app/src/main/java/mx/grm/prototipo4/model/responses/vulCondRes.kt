package mx.grm.prototipo4.model.responses

data class vulCondRes(
    val table: List<vulCondItem>
)

data class vulCondItem(
    val Nombre: String
)
