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
    entities = [ProductEntity::class],
    version = 4,
    exportSchema = true
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}

object DatabaseInstance {
    private var INSTANCE: AppDatabase? = null

    val MIGRATION_1_3 = object : Migration(1,3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE products ADD COLUMN category TEXT NOT NULL DEFAULT 'Unknown'")
            database.execSQL("ALTER TABLE products ADD COLUMN unit TEXT NOT NULL DEFAULT 'Unknown'")

            database.execSQL("""
            UPDATE products 
            SET category = (
                SELECT category 
                FROM categories 
                WHERE categoryId = productCategoryId
            )
        """)

            database.execSQL("""
            UPDATE products 
            SET unit = (
                SELECT unit 
                FROM units 
                WHERE unitId = productUnitId
            )
        """)

            database.execSQL("""
            CREATE TABLE products_new (
                productId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                productName TEXT NOT NULL,
                availableStock INTEGER NOT NULL,
                buyingPrice REAL NOT NULL,
                retailPrice REAL NOT NULL,
                wholeSalePrice REAL NOT NULL,
                quantity INTEGER NOT NULL,
                category TEXT NOT NULL,
                unit TEXT NOT NULL
            )
        """)

            database.execSQL("""
            INSERT INTO products_new (
                productId,
                productName,
                availableStock,
                buyingPrice,
                retailPrice,
                wholeSalePrice,
                quantity,
                category,
                unit
            )
            SELECT 
                productId,
                productName,
                availableStock,
                buyingPrice,
                retailPrice,
                wholeSalePrice,
                quantity,
                category,
                unit
            FROM products
        """)

            database.execSQL("DROP TABLE products")
            database.execSQL("ALTER TABLE products_new RENAME TO products")
        }

    }

    val MIGRATION_3_4 = object: Migration(3,4) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("DROP TABLE units")
            db.execSQL("DROP TABLE categories")
        }
    }

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DB_NAME
            ).addMigrations(MIGRATION_1_3).addMigrations(MIGRATION_3_4).build()
            INSTANCE = instance
            instance
        }
    }
}