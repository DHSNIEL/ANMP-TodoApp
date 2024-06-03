package com.daniel.todoapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.daniel.todoapp.databinding.TodoItemLayoutBinding
import com.daniel.todoapp.model.Todo

class TodoListAdapter(
    val todoList: ArrayList<Todo>, val adapterOnClick: (Todo) -> Unit
) : RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>(), TodoCheckedChangeListener,
    TodoEditClickListener {
    class TodoViewHolder(var binding: TodoItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        var binding = TodoItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TodoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.binding.todo = todoList[position]
        holder.binding.listener = this
        holder.binding.editlistener = this


        holder.binding.checkTask.setOnCheckedChangeListener(null)
        holder.binding.checkTask.isChecked = todoList[position].isDone == 1

        holder.binding.checkTask.tag = todoList[position]

        holder.binding.checkTask.setOnCheckedChangeListener{ buttonView, isChecked ->
            if(buttonView.isPressed){
                val currentTodo = buttonView.tag as Todo
                currentTodo.isDone = if(isChecked) 1 else 0
                adapterOnClick(currentTodo)
            }
        }

        /* sempat berhasil
//        holder.binding.checkTask.setOnCheckedChangeListener { _, isChecked ->
//            if (isChecked) {
//                adapterOnClick(todoList[position])
//                (holder.itemView.context as? FragmentActivity)?.supportFragmentManager?.fragments?.forEach {
//                    if (it is TodoListFragment) {
//                        it.refreshTodoList()
//                    }
//                }
//            }
//        }
*/
//        holder.binding.checkTask.isChecked = false
    }

    fun updateTodoList(newtodolist: List<Todo>) {
        todoList.clear()
        todoList.addAll(newtodolist)
        notifyDataSetChanged()
    }

    override fun onCheckedChange(cb: CompoundButton, isChecked: Boolean, obj: Todo) {
        if (cb.isPressed) {
            adapterOnClick(obj)
        }
    }

    override fun onTodoEditClick(v: View) {
        val uuid = v.tag.toString().toInt()
        val action = TodoListFragmentDirections.actionEditTodo(uuid)
        Navigation.findNavController(v).navigate(action)
    }


}