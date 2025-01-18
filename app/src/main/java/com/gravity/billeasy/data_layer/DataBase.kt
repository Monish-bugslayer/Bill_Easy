package com.gravity.billeasy.data_layer

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gravity.billeasy.data_layer.migration.MigrationImpl
import com.gravity.billeasy.domain_layer.ProductEntity
import com.gravity.billeasy.domain_layer.ShopEntity


const val DB_NAME = "app_database"
// TODO need to store db version in preference and show user that new version udates.
const val DB_VERSION = 5

@Database(
    entities = [ProductEntity::class, ShopEntity::class],
    version = DB_VERSION,
    exportSchema = true
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
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
                .build()
            INSTANCE = instance
            instance
        }
    }
}