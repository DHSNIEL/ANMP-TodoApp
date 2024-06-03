package com.daniel.todoapp.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg todo: Todo)
    @Query("SELECT * from todo ORDER BY priority DESC")
    fun selectAllTodo():List<Todo>
    @Query("SELECT * from todo WHERE uuid = :id")
    fun selectTodo(id:Int):Todo
    @Delete
    fun deleteTodo(todo: Todo)
    @Update
    fun updateTodo(todo:Todo)
    @Query("UPDATE todo " +
            "SET title = :title, notes = :notes, priority = :priority, is_done= :isDone " +
            "WHERE uuid = :id")
    fun update(title:String, notes:String, priority:Int, id:Int, isDone:Int)
}