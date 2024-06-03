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
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class ListTodoViewModel(application: Application) : AndroidViewModel(application), CoroutineScope {
    val debugTodo = MutableLiveData<List<Todo>>()
    val todoLD = MutableLiveData<List<Todo>>()
    val todoLoadErrorLD = MutableLiveData<Boolean>()
    val loadingLD = MutableLiveData<Boolean>()

    private var job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.IO

    fun refresh() {
        loadingLD.value = true
        todoLoadErrorLD.value = false
        launch {
            val db = buildDb(getApplication())

            val allTodo = db.todoDao().selectAllTodo()
            val activeTodo = allTodo.filter { it.isDone == 0 }
//            withContext(Dispatchers.Main){
//                todoLD.value = activeTodo
//                loadingLD.value = false
//            }

            todoLD.postValue(activeTodo)
////            todoLD.postValue(db.todoDao().selectAllTodo())
            loadingLD.postValue(false)
        }
    }

    fun taskFinished(todo: Todo) {
        launch {
            val db = buildDb(getApplication())
            if (todo.isDone == 1) {
                todo.isDone = 0
            } else {
                todo.isDone = 1
            }

            db.todoDao().updateTodo(todo)
            todoLD.postValue(db.todoDao().selectAllTodo())
//            refresh()
        }
    }

//    fun clearTask(todo: Todo) {
//        launch {
//            val db = buildDb(getApplication())
//            db.todoDao().deleteTodo(todo)
//            todoLD.postValue(db.todoDao().selectAllTodo())
//        }
//    }

    fun debugFetch() {
        launch {
            val db = buildDb(getApplication())
            debugTodo.postValue(db.todoDao().selectAllTodo())
        }
    }
}




