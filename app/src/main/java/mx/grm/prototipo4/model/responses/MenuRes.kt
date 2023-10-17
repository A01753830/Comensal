package mx.grm.prototipo4.model.responses

/**
 * Menu's Response Model
 * @author Héctor González Sánchez
 */

data class MenuRes(
    var table: List<TableItemMenu>
)

data class TableItemMenu(
    var SopaArroz: String,
    var PlatoFuerte: String,
    var PanTortilla: String,
    var AguaDelDia: String,
    var FrijolesSalsa: String,
)
