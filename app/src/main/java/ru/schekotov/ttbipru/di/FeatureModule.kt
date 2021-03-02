package ru.schekotov.ttbipru.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.schekotov.ttbipru.data.db.DBHelper
import ru.schekotov.ttbipru.data.db.dao.VehicleDAO
import ru.schekotov.ttbipru.data.repositorys.IVehicleRepository
import ru.schekotov.ttbipru.data.repositorys.impl.VehicleRepository
import ru.schekotov.ttbipru.domain.IVehicleInteractor
import ru.schekotov.ttbipru.domain.impl.VehicleInteractor
import javax.inject.Singleton

@Module (includes = [AppModule::class])
class FeatureModule {

    @Provides
    fun provideVehicleInteractor(vehicleRepository: IVehicleRepository): IVehicleInteractor {
        return VehicleInteractor(vehicleRepository)
    }

    @Provides
    fun provideVehicleRepository(vehicleDAO: VehicleDAO): IVehicleRepository {
        return VehicleRepository(vehicleDAO)
    }

    @Provides
    fun provideVehicleDAO(dbHelper: DBHelper): VehicleDAO {
        return VehicleDAO(dbHelper)
    }

    @Provides
    @Singleton
    fun provideDBHelper(context: Context): DBHelper {
        return DBHelper(context)
    }

}