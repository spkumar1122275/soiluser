package com.campuscoders.posterminalapp.di

import android.content.Context
import androidx.room.Room
import com.campuscoders.posterminalapp.data.locale.*
import com.campuscoders.posterminalapp.data.remote.api.AuthApiService
import com.campuscoders.posterminalapp.data.repository.locale.AuthRepositoryImpl
import com.campuscoders.posterminalapp.data.repository.locale.CompanyRepositoryImpl
import com.campuscoders.posterminalapp.data.repository.locale.SecondAuthRepositoryImpl
import com.campuscoders.posterminalapp.domain.repository.AuthRepository
import com.campuscoders.posterminalapp.domain.repository.CompanyRepository
import com.campuscoders.posterminalapp.domain.repository.SecondAuthRepository
import com.campuscoders.posterminalapp.domain.use_case.login.HandleLoginUseCase
import com.campuscoders.posterminalapp.domain.use_case.login.LoginWithApiUseCase
import com.campuscoders.posterminalapp.domain.use_case.login.FetchCompanyDataUseCase
import com.campuscoders.posterminalapp.utils.CustomSharedPreferences
import com.campuscoders.posterminalapp.utils.SecondLoginSharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext ctx: Context) =
        Room.databaseBuilder(ctx, PosDatabase::class.java, "PosDatabase").build()

    @Provides
    @Singleton
    fun provideCompanyDao(db: PosDatabase) = db.companyDao()

    @Provides
    @Singleton
    fun provideLicenseDao(db: PosDatabase) = db.licenseDao()

    @Provides
    @Singleton
    fun provideDepartmentDao(db: PosDatabase) = db.departmentDao()

    @Provides
    @Singleton
    fun provideMainUserDao(db: PosDatabase) = db.mainUserDao()

    @Provides
    @Singleton
    fun provideTerminalUsersDao(db: PosDatabase) = db.terminalUsersDao()

    @Provides
    @Singleton
    fun provideTerminalUserLicenseDao(db: PosDatabase) = db.terminalUserLicenseDao()

    @Provides
    @Singleton
    fun provideCategoriesDao(db: PosDatabase) = db.categoriesDao()

    @Provides
    @Singleton
    fun provideProductsDao(db: PosDatabase) = db.productsDao()

    @Provides
    @Singleton
    fun provideOrdersDao(db: PosDatabase) = db.ordersDao()

    @Provides
    @Singleton
    fun provideCustomersDao(db: PosDatabase) = db.customersDao()

    @Provides
    @Singleton
    fun provideOrdersProductsDao(db: PosDatabase) = db.ordersProductsDao()

    @Provides
    @Singleton
    fun provideCustomSharedPreferences(@ApplicationContext context: Context): CustomSharedPreferences {
        return CustomSharedPreferences(context)
    }

    @Provides
    @Singleton
    fun provideSecondLoginSharedPreferences(@ApplicationContext context: Context): SecondLoginSharedPreferences {
        return SecondLoginSharedPreferences(context)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        mainUserDao: MainUserDao,
        terminalUsersDao: TerminalUsersDao,
        api: AuthApiService,
        prefs: CustomSharedPreferences
    ): AuthRepository {
        return AuthRepositoryImpl(
            mainUserDao,
            terminalUsersDao,
            api,
            prefs
        )
    }


    @Provides
    @Singleton
    fun provideCompanyRepository(api: AuthApiService, db: PosDatabase): CompanyRepository = CompanyRepositoryImpl(api, db)

    @Provides
    @Singleton
    fun provideSecondAuthRepository(
        terminalUsersDao: TerminalUsersDao,
        mainUserDao: MainUserDao,
        prefs: SecondLoginSharedPreferences
    ): SecondAuthRepository = SecondAuthRepositoryImpl(terminalUsersDao, mainUserDao, prefs)

    @Provides
    fun provideLoginWithApiUseCase(repository: AuthRepository) = LoginWithApiUseCase(repository)

    @Provides
    fun provideFetchCompanyDataUseCase(repository: CompanyRepository) = FetchCompanyDataUseCase(repository)

    @Provides
    fun provideHandleLoginUseCase(authRepo: AuthRepository, companyRepo: CompanyRepository) =
        HandleLoginUseCase(authRepo, companyRepo)
}
