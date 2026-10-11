package bo.uab.comedor.features.menu.domain

import bo.uab.comedor.features.menu.domain.model.WeeklyMenu
import kotlinx.coroutines.flow.Flow

interface MenuRepository {
    /** Emite null cuando el encargado todavía no publicó el menú de la semana. */
    fun observeWeeklyMenu(): Flow<WeeklyMenu?>
}
