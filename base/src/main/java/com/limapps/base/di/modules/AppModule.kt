package com.limapps.base.di.modules

import android.app.Application
import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.gson.Gson
import com.limapps.base.InjectableApplication
import com.limapps.base.RappiBroadcastImpl
import com.limapps.base.interfaces.RappiBroadcast
import com.limapps.base.others.IResourceProvider
import com.limapps.base.persistence.db.PersistenceManager
import com.limapps.base.persistence.preferences.PreferencesManager
import com.limapps.base.utils.BaseDataProvider
import com.limapps.base.utils.RappiBaseDataProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Singleton

const val LOCALE = "LOCALE"
const val HAS_PRIME_ACTIVE = "HAS_PRIME_ACTIVE"

@Module
abstract class AppModule {

    /*@Binds
    abstract fun provideCountryDataProvider(provider: RappiCountryDataProvider): CountryDataProvider*/

    @Binds
    abstract fun provideRappiBroadcast(provider: RappiBroadcastImpl): RappiBroadcast

    @Module
    companion object {



        @Provides
        @Singleton
        @JvmStatic
        fun providesContext(app: InjectableApplication): Context = app.applicationContext

        @Provides
        @Singleton
        @JvmStatic
        fun providesApplication(app: InjectableApplication): Application = app

        @Provides
        @Singleton
        @JvmStatic
        fun provideBaseDataProvider(context: Context, gson: Gson): BaseDataProvider {

            //TODO add an additional check to prevent this being called on production builds
            val mockClass = try {
                Class.forName("com.limapps.qa.CountryDataMocksProvider")
            } catch (e: Exception) {
                null
            }

            val defaultDataProvider = RappiBaseDataProvider(PreferencesManager, gson)

            return mockClass?.let { clazz ->
                val constructor = clazz.getConstructor(BaseDataProvider::class.java, Context::class.java)
                constructor.newInstance(defaultDataProvider, context) as BaseDataProvider
            } ?: kotlin.run {
                defaultDataProvider
            }
        }


        /*@Provides
        @Named(LOCALE)
        @JvmStatic
        fun providesCurrentLocal(context: Context): String {
            val tm = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            return tm.networkCountryIso
        }*/

        @Provides
        @Singleton
        @JvmStatic
        fun userSettings(context: Context, gson: Gson): PreferencesManager {
            PreferencesManager.init(context, gson)
            return PreferencesManager
        }

        @Provides
        @Singleton
        @JvmStatic
        fun providePersistenceManager(): PersistenceManager {
            return PersistenceManager
        }

        /*@Provides
        @Singleton
        @JvmStatic
        fun foregroundBackgroundListener(appRepository: AppRepository, countryDataProvider: CountryDataProvider): ForegroundBackgroundListener {
            return ForegroundBackgroundListener(appRepository, countryDataProvider)
        }

        @Provides
        @Singleton
        @JvmStatic
        fun providesStateServiceManager(dataProvider: BaseDataProvider, resourceProvider: IResourceProvider): StateServiceManager {
            return StateServiceManager(dataProvider, resourceProvider)
        }

        @Provides
        @Named(HAS_PRIME_ACTIVE)
        @JvmStatic
        fun hasPrimeActive(userController: UserController): Boolean {
            return userController.getSubscription().active
        }*/

        @Provides
        @Reusable
        @JvmStatic
        fun resourceProvider(context: Context): IResourceProvider {
            return object : IResourceProvider {

                override fun getString(id: Int, vararg args: Any?): String = context.getString(id, *args)

                override fun getStringArray(id: Int): Array<String> = context.resources.getStringArray(id)

                override fun getString(id: Int): String = context.getString(id)

                override fun getQuantityString(stringRes: Int, quantity: Int, vararg formatArgs: Any): String {
                    return context.resources.getQuantityString(stringRes, quantity, *formatArgs)
                }

                override fun getFontTypeFace(id: Int): Typeface? = ResourcesCompat.getFont(context, id)

                override fun getColor(colorRes: Int): Int = ContextCompat.getColor(context, colorRes)

                override fun getDrawable(drawableRes: Int): Drawable {
                    return requireNotNull(ContextCompat.getDrawable(context, drawableRes))
                }

                override fun getDimensionPixelSize(dimenRes: Int): Int {
                    return context.resources.getDimensionPixelSize(dimenRes)
                }

                override fun getIdentifier(name: String, defType: String): Int {
                    return context.resources.getIdentifier(name, defType, context.packageName)
                }
            }
        }

        /*@Provides
        @Singleton
        @JvmStatic
        fun providesFirebaseHandler(googlePlayServicesController: GooglePlayServicesController, context: Context): FirebaseHandler {
            return FirebaseHandler(googlePlayServicesController, context)
        }

        @Provides
        @Singleton
        @JvmStatic
        fun providesGoogleApiAvailability(): GoogleApiAvailability {
            return GoogleApiAvailability.getInstance()
        }*/
    }
}