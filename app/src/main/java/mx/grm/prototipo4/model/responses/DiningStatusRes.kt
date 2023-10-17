package mx.grm.prototipo4.model.responses

data class DiningStatusRes(
    var table: List<TableStatusItem>
)

data class TableStatusItem(
    var Nombre: String
)
