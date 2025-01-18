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

//    val MIGRATION_1_3 = object : Migration(1,3) {
//        override fun migrate(database: SupportSQLiteDatabase) {
//            database.execSQL("ALTER TABLE products ADD COLUMN category TEXT NOT NULL DEFAULT 'Unknown'")
//            database.execSQL("ALTER TABLE products ADD COLUMN unit TEXT NOT NULL DEFAULT 'Unknown'")
//
//            database.execSQL("""
//            UPDATE products
//            SET category = (
//                SELECT category
//                FROM categories
//                WHERE categoryId = productCategoryId
//            )
//        """)
//
//            database.execSQL("""
//            UPDATE products
//            SET unit = (
//                SELECT unit
//                FROM units
//                WHERE unitId = productUnitId
//            )
//        """)
//
//            database.execSQL("""
//            CREATE TABLE products_new (
//                productId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
//                productName TEXT NOT NULL,
//                availableStock INTEGER NOT NULL,
//                buyingPrice REAL NOT NULL,
//                retailPrice REAL NOT NULL,
//                wholeSalePrice REAL NOT NULL,
//                quantity INTEGER NOT NULL,
//                category TEXT NOT NULL,
//                unit TEXT NOT NULL
//            )
//        """)
//
//            database.execSQL("""
//            INSERT INTO products_new (
//                productId,
//                productName,
//                availableStock,
//                buyingPrice,
//                retailPrice,
//                wholeSalePrice,
//                quantity,
//                category,
//                unit
//            )
//            SELECT
//                productId,
//                productName,
//                availableStock,
//                buyingPrice,
//                retailPrice,
//                wholeSalePrice,
//                quantity,
//                category,
//                unit
//            FROM products
//        """)
//
//            database.execSQL("DROP TABLE products")
//            database.execSQL("ALTER TABLE products_new RENAME TO products")
//        }
//
//    }
//
//    val MIGRATION_3_4 = object: Migration(3,4) {
//        override fun migrate(db: SupportSQLiteDatabase) {
//            db.execSQL("DROP TABLE units")
//            db.execSQL("DROP TABLE categories")
//        }
//    }
//
//    val Migration_4_5 = object: Migration(4,5) {
//        override fun migrate(db: SupportSQLiteDatabase) {
//            db.execSQL("""
//               CREATE TABLE IF NOT EXISTS shop (
//                    shopId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
//                    shopName TEXT NOT NULL,
//                    shopAddress TEXT NOT NULL,
//                    shopEmailAddress TEXT NOT NULL,
//                    shopMobileNumber TEXT NOT NULL,
//                    gstNumber TEXT NOT NULL,
//                    tinNumber TEXT NOT NULL,
//                    ownerName TEXT NOT NULL,
//                    ownerAddress TEXT NOT NULL,
//                    ownerMobileNumber TEXT NOT NULL
//               )
//            """.trimIndent())
//
//            db.execSQL("""
//                CREATE TABLE products_temp (
//                    productId INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
//                    productName TEXT NOT NULL,
//                    availableStock INTEGER NOT NULL,
//                    buyingPrice REAL NOT NULL,
//                    retailPrice REAL NOT NULL,
//                    wholeSalePrice REAL NOT NULL,
//                    quantity INTEGER NOT NULL,
//                    category TEXT NOT NULL,
//                    unit TEXT NOT NULL,
//                    shopId INTEGER NOT NULL,
//                    FOREIGN KEY (shopId) REFERENCES shop(shopId) ON DELETE CASCADE
//                )
//            """)
//
//            db.execSQL("""
//                INSERT INTO shop (
//                    shopName, shopAddress, shopEmailAddress, shopMobileNumber, gstNumber,
//                    tinNumber, ownerName, ownerAddress, ownerMobileNumber
//                )
//                Values(
//                    'Default Shop', 'Default Address', 'default@email.com', '0000000000',
//                    'DEFAULT_GST', 'DEFAULT_TIN', 'Default Owner', 'Default Owner Address', '0000000000'
//                )
//            """)
//
//            val cursor = db.query("SELECT shopId from shop LIMIT 1")
//            cursor.moveToFirst()
//            val defaultShopID = cursor.getLong(0)
//            cursor.close()
//
//            db.execSQL("""
//               INSERT INTO products_temp (
//                    productId,
//                    productName,
//                    availableStock,
//                    buyingPrice,
//                    retailPrice,
//                    wholeSalePrice,
//                    quantity,
//                    category,
//                    unit,
//                    shopId
//               )
//               SELECT
//                    productId,
//                    productName,
//                    availableStock,
//                    buyingPrice,
//                    retailPrice,
//                    wholeSalePrice,
//                    quantity,
//                    category,
//                    unit,
//                    $defaultShopID
//               FROM products
//            """)
//
//            db.execSQL("""DROP TABLE products""")
//            db.execSQL("""ALTER TABLE products_temp RENAME TO products""")
//        }
//    }

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