package com.gravity.billeasy.data_layer

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.gravity.billeasy.domain_layer.CategoryEntity
import com.gravity.billeasy.domain_layer.ProductEntity
import com.gravity.billeasy.domain_layer.ShopEntity
import com.gravity.billeasy.domain_layer.ShopOwnerEntity
import com.gravity.billeasy.domain_layer.UnitEntity


const val DB_NAME = "app_database"

@Database(
    entities = [
        ProductEntity::class,
        CategoryEntity::class,
        UnitEntity::class,
        ShopEntity::class,
        ShopOwnerEntity::class
    ],
    version = 2,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}

object DatabaseInstance {
    private var INSTANCE: AppDatabase? = null

    val MIGRATION_1_2 = object : Migration(1,2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("CREATE TABLE `ShopEntity` (`shopId` LONG, `shopName` TEXT, `shopAddress` TEXT, `shopMobileNumber` TEXT, `gstNumber` TEXT, `tinNumber` TEXT, " +
                    "PRIMARY KEY(`shopId`))")

            db.execSQL("CREATE TABLE `ShopOwnerEntity` (`ownerId` LONG, `ownerName` TEXT, `ownerAddress` TEXT, `ownerMobileNumber` TEXT, " +
                    "PRIMARY KEY(`ownerId`))")
        }

    }

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DB_NAME
            ).addMigrations(MIGRATION_1_2).build()
            INSTANCE = instance
            instance
        }
    }
}