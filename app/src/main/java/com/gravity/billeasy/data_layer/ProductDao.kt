package com.gravity.billeasy.data_layer

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.gravity.billeasy.domain_layer.CategoryEntity
import com.gravity.billeasy.domain_layer.CategoryWithProduct
import com.gravity.billeasy.domain_layer.ProductEntity
import com.gravity.billeasy.domain_layer.UnitEntity
import com.gravity.billeasy.domain_layer.UnitWithProduct
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert
    suspend fun addProduct(product: ProductEntity)

    @Insert
    suspend fun addUnit(unitEntity: UnitEntity)

    @Insert
    suspend fun addCategory(categoryEntity: CategoryEntity)

    @Update
    suspend fun updateProduct(productEntity: ProductEntity)

    @Delete
    suspend fun deleteProduct(product: ProductEntity)

    @Query("SELECT categoryId FROM categories WHERE category = :category")
    fun getCategoryId(category: String): Flow<Long>

    @Query("SELECT category from categories WHERE categoryId = :categoryId")
    fun getCategoryFromId(categoryId: Long): Flow<String>

    @Query("SELECT unit from units WHERE unitId = :unitId")
    fun getUnitFromId(unitId: Long): Flow<String>

    @Query("SELECT unitId FROM units WHERE unit = :unit")
    fun getUnitId(unit: String): Flow<Long>

    @Query("SELECT * FROM products")
    fun getAllProducts(): Flow<List<ProductEntity>>

    @Transaction
    @Query("SELECT * FROM categories")
    fun getCategoriesWithProducts(): Flow<List<CategoryWithProduct>>

    @Transaction
    @Query("SELECT * FROM units")
    fun getUnitsWithProducts(): Flow<List<UnitWithProduct>>

}