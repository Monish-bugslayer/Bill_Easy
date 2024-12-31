package com.gravity.billeasy.data_layer

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gravity.billeasy.domain_layer.CategoryEntity
import com.gravity.billeasy.domain_layer.ProductEntity
import com.gravity.billeasy.domain_layer.UnitEntity


const val DB_NAME = "app_database"

@Database(entities = [ProductEntity::class, CategoryEntity::class, UnitEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}

object DatabaseInstance {
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DB_NAME
            ).build()
            INSTANCE = instance
            instance
        }
    }
}