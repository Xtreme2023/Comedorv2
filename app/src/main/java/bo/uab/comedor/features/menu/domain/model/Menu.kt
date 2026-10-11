package bo.uab.comedor.features.menu.domain.model

import java.time.LocalDate

/** Un plato: su nombre y, si hace falta, con qué se acompaña. */
data class Dish(
    val name: String,
    val description: String? = null,
)

/**
 * Menú de un día.
 * - [dessert]: solo existe los jueves, por eso es nullable.
 * - [vegetarian]: solo lo ven los pensionados que lo eligieron (regla 5).
 *   El caso de uso lo deja en null para los demás.
 */
data class DayMenu(
    val date: LocalDate,
    val soup: Dish,
    val main: Dish,
    val second: Dish,
    val dessert: Dish? = null,
    val saladBar: List<String>,
    val vegetarian: Dish? = null,
)

data class WeeklyMenu(
    val weekStart: LocalDate,
    val days: List<DayMenu>,
)
