package bo.uab.comedor.core.di

import bo.uab.comedor.features.menu.data.FakeMenuRepository
import bo.uab.comedor.features.menu.domain.MenuRepository
import bo.uab.comedor.features.subscription.data.FakeSubscriptionRepository
import bo.uab.comedor.features.subscription.domain.SubscriptionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/** Módulo temporal: al pasar a Firestore solo cambias estas dos líneas. */
@Module
@InstallIn(SingletonComponent::class)
abstract class FakeRepositoriesModule {

    @Binds
    abstract fun bindMenuRepository(impl: FakeMenuRepository): MenuRepository

    @Binds
    abstract fun bindSubscriptionRepository(impl: FakeSubscriptionRepository): SubscriptionRepository
}
