package com.example.main_screen.model

import com.example.domain.IServiceController
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ServiceModule(){

    @Binds
    @Singleton
    abstract fun bindServiceController(serviceController: ForegroundTestServiceController): IServiceController

}