package mx.grm.prototipo4.model.responses

/**
 * Menu Response Model
 *
 * This data class represents the response model for retrieving the daily menu of a dining location.
 * It includes a list of `TableItemMenu` objects, where each object holds the components of the menu.
 *
 * @property table A list of menu items, each containing the components of the daily menu.
 *
 * @constructor Creates a `MenuRes` response with the provided list of menu items.
 * @param table A list of menu items, each containing the components of the daily menu.
 */

data class MenuRes(
    var table: List<TableItemMenu>
)

/**
 * Menu Item Model
 *
 * This data class represents a menu item with its components.
 *
 * @property SopaArroz The description of the soup or rice.
 * @property PlatoFuerte The description of the main course.
 * @property PanTortilla The description of the bread or tortilla.
 * @property AguaDelDia The description of the daily water.
 * @property FrijolesSalsa The description of beans or sauce.
 *
 * @constructor Creates a `TableItemMenu` with the provided descriptions of menu components.
 * @param SopaArroz The description of the soup or rice.
 * @param PlatoFuerte The description of the main course.
 * @param PanTortilla The description of the bread or tortilla.
 * @param AguaDelDia The description of the daily water.
 * @param FrijolesSalsa The description of beans or sauce.
 *
 * @authors Héctor González Sánchez
 * @authors Alfredo Azamar López
 */
data class TableItemMenu(
    var SopaArroz: String,
    var PlatoFuerte: String,
    var PanTortilla: String,
    var AguaDelDia: String,
    var FrijolesSalsa: String,
)
