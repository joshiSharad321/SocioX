package com.sharad.sociox.Model




import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "products")
data class ProductModel (
    @PrimaryKey
    @NotNull
    val productId:String,
    @ColumnInfo(name = "productName")
    val productName: String?="",
    @ColumnInfo(name = "productImage")
    val productImage: String?="",
    @ColumnInfo(name="productSp")
    val productSp: String?="",

        )