package bo.uab.comedor.features.menu.data

import bo.uab.comedor.features.menu.domain.model.DayMenu
import bo.uab.comedor.features.menu.domain.model.Dish
import bo.uab.comedor.features.menu.domain.model.WeeklyMenu
import java.time.LocalDate

/** Datos de ejemplo (gastronomía cochabambina + pastas). Se reemplazan por Firestore después. */
internal fun sampleWeek(monday: LocalDate): WeeklyMenu {
    val saladBar = listOf(
        "lechuga", "tomate", "zanahoria rallada", "pepino",
        "cebolla en pluma", "choclo desgranado", "remolacha", "llajwa aparte",
    )
    return WeeklyMenu(
        weekStart = monday,
        days = listOf(
            DayMenu(
                date = monday,
                soup = Dish("Sopa de maní", "Con papa frita y fideo, al estilo cochabambino"),
                main = Dish("Silpancho", "Carne apanada sobre arroz y papa, con huevo frito, tomate y cebolla"),
                second = Dish("Tallarines a la boloñesa", "Salsa de carne molida con queso rallado"),
                saladBar = saladBar,
                vegetarian = Dish("Milanesa de soya", "Con arroz, papa y ensalada de tomate"),
            ),
            DayMenu(
                date = monday.plusDays(1),
                soup = Dish("Sopa de quinua", "Con verduras y un toque de apio"),
                main = Dish("Pique macho", "Carne, salchicha, papa frita, tomate, locoto y huevo duro"),
                second = Dish("Canelones de pollo", "Rellenos de pollo y espinaca con salsa blanca"),
                saladBar = saladBar,
                vegetarian = Dish("Locro de zapallo", "Con queso fresco, papa y arroz"),
            ),
            DayMenu(
                date = monday.plusDays(2),
                soup = Dish("Crema de zapallo", "Con crutones de pan"),
                main = Dish("Charque kan", "Charque con mote, papa, huevo y queso frito"),
                second = Dish("Ñoquis al pomodoro", "Ñoquis de papa con albahaca y queso parmesano"),
                saladBar = saladBar,
                vegetarian = Dish("Tarwi con llajwa", "Tarwi aliñado, papa cocida y mote"),
            ),
            DayMenu(
                date = monday.plusDays(3), // jueves: único día con postre
                soup = Dish("Sopa de pollo", "Con papa, zanahoria y fideo cabello de ángel"),
                main = Dish("Chicharrón de cerdo", "Con mote, papa, llajwa y cebolla"),
                second = Dish("Espagueti al pesto", "Con albahaca, ajo y queso"),
                dessert = Dish("Flan de leche", "Con caramelo"),
                saladBar = saladBar,
                vegetarian = Dish("Ají de fideo sin carne", "Fideo en salsa de ají con papa, arveja y queso"),
            ),
            DayMenu(
                date = monday.plusDays(4),
                soup = Dish("Sopa de fideo con verduras"),
                main = Dish("Picante de pollo", "Pollo en salsa de ají con papa, chuño y arroz"),
                second = Dish("Fettuccini en salsa blanca", "Con pollo y champiñones"),
                saladBar = saladBar,
                vegetarian = Dish("Tortilla de verduras", "Con arroz y ensalada fresca"),
            ),
        ),
    )
}
