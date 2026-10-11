package bo.uab.comedor.features.menu.domain.usecase

import bo.uab.comedor.features.menu.domain.MenuRepository
import bo.uab.comedor.features.menu.domain.model.WeeklyMenu
import bo.uab.comedor.features.subscription.domain.SubscriptionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

/**
 * Entrega el menú semanal ya filtrado para el usuario actual.
 * La regla "el vegetariano solo lo ve el inscrito" vive aquí (domain),
 * así ninguna pantalla puede olvidarla.
 */
class ObserveWeeklyMenuUseCase @Inject constructor(
    private val menuRepository: MenuRepository,
    private val subscriptionRepository: SubscriptionRepository,
) {
    operator fun invoke(): Flow<WeeklyMenu?> = combine(
        menuRepository.observeWeeklyMenu(),
        subscriptionRepository.observeCanSeeVegetarianMenu(),
    ) { menu, canSeeVegetarian ->
        if (menu == null || canSeeVegetarian) {
            menu
        } else {
            menu.copy(days = menu.days.map { it.copy(vegetarian = null) })
        }
    }
}
