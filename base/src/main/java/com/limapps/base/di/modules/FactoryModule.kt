package com.limapps.base.di.modules

import androidx.lifecycle.ViewModelProvider
import com.limapps.base.di.factory.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class FactoryModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}