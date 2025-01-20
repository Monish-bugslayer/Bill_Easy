package com.gravity.billeasy.data_layer

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gravity.billeasy.data_layer.dao.ProductDao
import com.gravity.billeasy.data_layer.dao.SalesDao
import com.gravity.billeasy.data_layer.dao.ShopDao
import com.gravity.billeasy.data_layer.migration.MigrationImpl
import com.gravity.billeasy.domain_layer.entities.ProductEntity
import com.gravity.billeasy.domain_layer.entities.SaleEntity
import com.gravity.billeasy.domain_layer.entities.ShopEntity


const val DB_NAME = "app_database"
// TODO need to store db version in preference and show user that new version udates.
const val DB_VERSION = 7

@Database(
    entities = [ProductEntity::class, ShopEntity::class, SaleEntity::class],
    version = DB_VERSION,
    exportSchema = true
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun shopDao(): ShopDao
    abstract fun saleDao(): SalesDao
}

object DatabaseInstance {
    private var INSTANCE: AppDatabase? = null
    private var dbMigrationImpl = MigrationImpl()

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DB_NAME
            )
                .addMigrations(dbMigrationImpl.migrateFrom_1_3())
                .addMigrations(dbMigrationImpl.migrateFrom_3_4())
                .addMigrations(dbMigrationImpl.migrateFrom_4_5())
                .addMigrations(dbMigrationImpl.migrateFrom_5_6())
                .build()
            INSTANCE = instance
            instance
        }
    }
}