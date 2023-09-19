package com.example.testing.di

import android.content.Context
import androidx.room.Room
import com.example.testing.data.RepositoryImpl
import com.example.testing.data.api.API
import com.example.testing.data.db.UsersDao
import com.example.testing.data.db.UsersDatabase
import com.example.testing.domain.repository.Repository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, json: Json): API {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com")
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(("application/json").toMediaType()))
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun provideUsersDatabase(@ApplicationContext appContext: Context): UsersDatabase {
        return Room.databaseBuilder(
            context = appContext,
            klass = UsersDatabase::class.java,
            name = "users_database"
        ).build()
    }

    @Provides
    fun provideUsersDao(db: UsersDatabase): UsersDao{
        return db.returnUsersDao()
    }

    @Provides
    fun provideRepository(dao: UsersDao, api: API): Repository {
        return RepositoryImpl(api = api, dao = dao)
    }
}