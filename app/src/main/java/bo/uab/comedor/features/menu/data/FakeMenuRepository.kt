package bo.uab.comedor.features.menu.data

import bo.uab.comedor.features.menu.domain.MenuRepository
import bo.uab.comedor.features.menu.domain.model.WeeklyMenu
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject
import javax.inject.Singleton

private val LA_PAZ: ZoneId = ZoneId.of("America/La_Paz")

/** Implementación temporal con datos de ejemplo. Luego se cambia por FirestoreMenuRepository. */
@Singleton
class FakeMenuRepository @Inject constructor() : MenuRepository {

    override fun observeWeeklyMenu(): Flow<WeeklyMenu?> = flowOf(sampleWeek(currentMonday()))

    private fun currentMonday(): LocalDate {
        val today = LocalDate.now(LA_PAZ)
        val monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        // Sábado y domingo se muestra la semana que viene
        return if (today.dayOfWeek >= DayOfWeek.SATURDAY) monday.plusWeeks(1) else monday
    }
}
