package com.limapps.base.di.modules

import com.limapps.base.di.qualifiers.MicroService
import com.limapps.base.rest.retrofit.services.StoresService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit

@Module
class StoreApiModule {

    @Provides
    @MicroService
    fun storeService(@MicroService retrofit: Retrofit): StoresService {
        return retrofit.create(StoresService::class.java)
    }
}