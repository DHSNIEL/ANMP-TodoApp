package com.daniel.todoapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.daniel.todoapp.model.Todo
import com.daniel.todoapp.util.buildDb
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DetailTodoViewModel(application: Application):AndroidViewModel(application),CoroutineScope {
    private val job = Job()

    val todoLD = MutableLiveData<Todo>()

    fun fetch(uuid: Int){
        launch {
            val db = buildDb(getApplication())
            todoLD.postValue(db.todoDao()
                .selectTodo(uuid))
        }
    }
    fun addTodo(list:List<Todo>) {
        launch {
            val db = buildDb(getApplication())
            db.todoDao().insertAll(*list.toTypedArray())
        }
    }

    fun update(title:String, notes:String, priority: Int, uuid: Int, isDone: Int){
        launch{
            val db = buildDb(getApplication())
            db.todoDao().update(title, notes, priority, uuid, isDone)
        }
    }
    fun updateTodo(todo:Todo){
        launch{
            buildDb(getApplication()).todoDao().updateTodo(todo)
        }
    }
//    fun addTodo(todo: Todo){
//        launch{
//            val db = TodoDatabase.buildDatabase(getApplication())
//            db.todoDao().insertAll(todo)
//        }
//    }
        override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO
}