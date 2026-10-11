package bo.uab.comedor.features.subscription.domain

import kotlinx.coroutines.flow.Flow

interface SubscriptionRepository {
    /**
     * true solo si el empleado es pensionado del mes actual
     * y eligió la opción vegetariana.
     */
    fun observeCanSeeVegetarianMenu(): Flow<Boolean>
}
