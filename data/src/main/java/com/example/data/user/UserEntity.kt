package com.example.data.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.User

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @ColumnInfo(name = "first_name")
    val firstName: String,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    @ColumnInfo(name = "email")
    val email: String,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "imagePath")
    var imagePath: String?
){
    fun toDomain() = User(
        id, firstName, lastName, email, password, imagePath
    )
}

fun User.toData() = UserEntity(
    id, firstName, lastName, email, password, imagePath
)