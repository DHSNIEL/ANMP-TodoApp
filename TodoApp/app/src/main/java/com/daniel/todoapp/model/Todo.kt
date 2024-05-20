package com.daniel.todoapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    @ColumnInfo(name="Title")
    var title:String,
    @ColumnInfo(name="notes")
    var notes: String,
    @ColumnInfo(name="priority")
    var priority: Int
){
    @PrimaryKey(autoGenerate = true)
    var uuid: Int = 0
}