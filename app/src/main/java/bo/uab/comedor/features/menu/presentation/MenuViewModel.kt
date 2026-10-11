package bo.uab.comedor.features.menu.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import bo.uab.comedor.features.menu.domain.model.WeeklyMenu
import bo.uab.comedor.features.menu.domain.usecase.ObserveWeeklyMenuUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject

private val LA_PAZ: ZoneId = ZoneId.of("America/La_Paz")

@HiltViewModel
class MenuViewModel @Inject constructor(
    observeWeeklyMenu: ObserveWeeklyMenuUseCase,
) : ViewModel() {

    // null = "todavía no eligió": se abre el día de hoy (o el primero de la semana)
    private val selectedDate = MutableStateFlow<LocalDate?>(null)

    val uiState: StateFlow<MenuUiState> =
        combine<WeeklyMenu?, LocalDate?, MenuUiState>(observeWeeklyMenu(), selectedDate) { menu, selected ->
            if (menu == null || menu.days.isEmpty()) {
                MenuUiState.NotAvailable
            } else {
                val date = selected?.takeIf { s -> menu.days.any { it.date == s } }
                    ?: defaultDay(menu)
                MenuUiState.Success(days = menu.days, selectedDate = date)
            }
        }
            .catch { emit(MenuUiState.Error("No pudimos cargar el menú. Revisa tu conexión e intenta de nuevo.")) }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), MenuUiState.Loading)

    fun onDaySelected(date: LocalDate) {
        selectedDate.value = date
    }

    private fun defaultDay(menu: WeeklyMenu): LocalDate {
        val today = LocalDate.now(LA_PAZ)
        return menu.days.firstOrNull { it.date == today }?.date ?: menu.days.first().date
    }
}
