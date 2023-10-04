package com.sharad.sociox.roomDB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sharad.sociox.Model.ProductModel

@Dao
interface ProductDao {
    @Insert
suspend fun insertProduct(product: ProductModel)
@Delete
suspend fun deleteProduct(product: ProductModel)
@Query("SELECT * FROM products")
fun getAllProduct():LiveData<List<ProductModel>>
@Query("SELECT * FROM products WHERE productId=:id" )
fun isExit(id:String):ProductModel
}