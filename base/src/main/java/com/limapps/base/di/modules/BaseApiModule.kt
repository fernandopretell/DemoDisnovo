package com.limapps.base.di.modules

import com.limapps.base.di.qualifiers.BasePath
import com.limapps.base.di.qualifiers.MicroService
import com.limapps.base.di.qualifiers.SwitchServices
import com.limapps.base.rest.retrofit.services.OrderHistoryService
import com.limapps.base.rest.retrofit.services.PlacesService
import com.limapps.base.rest.services.ChangeCountryService
import com.limapps.base.rest.services.CountriesAndCitiesServices
import com.limapps.base.rest.services.HasCoverageService
import com.limapps.base.rest.services.InformationService
import com.limapps.base.users.services.AbProxyService
import com.limapps.base.users.services.UserMicroService
import com.limapps.base.users.services.UserService
import dagger.Module
import dagger.Provides
import dagger.Reusable
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object BaseApiModule {

    @Provides
    @Singleton
    @BasePath
    @JvmStatic
    fun informationApi(@BasePath retrofit: Retrofit): InformationService {
        return retrofit.create(InformationService::class.java)
    }

    @Provides
    @Singleton
    @MicroService
    @JvmStatic
    fun informationMicroServiceApi(@MicroService retrofit: Retrofit): InformationService {
        return retrofit.create(InformationService::class.java)
    }

    @Provides
    @Singleton
    @SwitchServices
    @JvmStatic
    fun informationGeneralClientApi(@SwitchServices retrofit: Retrofit): InformationService {
        return retrofit.create(InformationService::class.java)
    }

    @Provides
    @BasePath
    @JvmStatic
    fun userApi(@BasePath retrofit: Retrofit): UserService {
        return retrofit.create(UserService::class.java)
    }

    @Provides
    @MicroService
    @JvmStatic
    fun userMicroServiceApi(@MicroService retrofit: Retrofit): UserMicroService {
        return retrofit.create(UserMicroService::class.java)
    }

    @Provides
    @MicroService
    @JvmStatic
    fun provideAbProxyServiceApi(@MicroService retrofit: Retrofit): AbProxyService {
        return retrofit.create(AbProxyService::class.java)
    }

    @Provides
    @Singleton
    @BasePath
    @JvmStatic
    fun countriesAndCitiesServices(@BasePath retrofit: Retrofit): CountriesAndCitiesServices {
        return retrofit.create(CountriesAndCitiesServices::class.java)
    }

    @Provides
    @Singleton
    @MicroService
    @JvmStatic
    fun countriesAndCitiesMicroServices(@MicroService retrofit: Retrofit): CountriesAndCitiesServices {
        return retrofit.create(CountriesAndCitiesServices::class.java)
    }

    @Provides
    @Singleton
    @SwitchServices
    @JvmStatic
    fun hasCoverageService(@SwitchServices retrofit: Retrofit): HasCoverageService {
        return retrofit.create(HasCoverageService::class.java)
    }

    @Provides
    @MicroService
    @JvmStatic
    fun provideCoverageService(@MicroService retrofit: Retrofit): HasCoverageService {
        return retrofit.create(HasCoverageService::class.java)
    }

    @Provides
    @Singleton
    @MicroService
    @JvmStatic
    fun changeCountryService(@MicroService retrofit: Retrofit): ChangeCountryService {
        return retrofit.create(ChangeCountryService::class.java)
    }

    @Provides
    @Singleton
    @SwitchServices
    @JvmStatic
    fun placesServices(@SwitchServices retrofit: Retrofit): PlacesService {
        return retrofit.create(PlacesService::class.java)
    }

    @Provides
    @Reusable
    @MicroService
    @JvmStatic
    fun orderHistoryService(@MicroService retrofit: Retrofit): OrderHistoryService {
        return retrofit.create(OrderHistoryService::class.java)
    }

}