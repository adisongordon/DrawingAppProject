package com.example.drawingapp

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DrawingDAO {
    @Insert
    suspend fun insert(drawing: DrawingEntity)

    @Query("SELECT * FROM drawing_table")
    suspend fun getAllDrawings(): List<DrawingEntity>

    @Query("SELECT * FROM drawing_table WHERE name = :name")
    suspend fun getDrawingByName(name: String): DrawingEntity?
}