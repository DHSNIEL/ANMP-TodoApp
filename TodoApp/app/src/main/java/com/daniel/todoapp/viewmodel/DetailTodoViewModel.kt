package com.daniel.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.daniel.todoapp.model.Todo
import com.daniel.todoapp.model.TodoDatabase
import com.daniel.todoapp.util.buildDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailTodoViewModel (application: Application): AndroidViewModel(application), CoroutineScope {
    private val job = Job()

    val todoLD = MutableLiveData<Todo>()

    fun fetch(uuid: Int){
        launch{
            val db = buildDB(getApplication())
            todoLD.postValue(db.todoDao().selectTodo(0))
        }
    }

    fun addTodo(todo: Todo){
        launch {
            val db = TodoDatabase.buildDatabase(getApplication())
            db.todoDao().insertAll(todo)
        }
    }

    fun updateTodo(todo: Todo){
        launch{
            buildDB(getApplication()).todoDao().updateTodo(todo)
        }
    }

    fun update(title: String, notes:String, priority:Int, uuid: Int){
        launch{
            val db = buildDB(getApplication())
            db.todoDao().update(title, notes, priority, uuid)
        }
    }

//    fun addTodo(list:List<Todo>){
//        launch {
//            val db = TodoDatabase.buildDatabase(getApplication())
//            db.todoDao().insertAll()
//        }
//
//    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO



}