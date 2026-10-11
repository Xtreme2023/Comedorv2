package bo.uab.comedor.features.menu.presentation

import bo.uab.comedor.features.menu.domain.model.DayMenu
import java.time.LocalDate

sealed interface MenuUiState {
    data object Loading : MenuUiState

    /** HU5: el encargado todavía no cargó el menú. */
    data object NotAvailable : MenuUiState

    data class Error(val message: String) : MenuUiState

    data class Success(
        val days: List<DayMenu>,
        val selectedDate: LocalDate,
    ) : MenuUiState {
        val selectedDay: DayMenu get() = days.first { it.date == selectedDate }
    }
}
