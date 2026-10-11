package bo.uab.comedor.features.subscription.data

import bo.uab.comedor.features.subscription.domain.SubscriptionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FakeSubscriptionRepository @Inject constructor() : SubscriptionRepository {

    // Cambia a true para probar cómo se ve el menú de un pensionado vegetariano
    override fun observeCanSeeVegetarianMenu(): Flow<Boolean> = flowOf(false)
}
