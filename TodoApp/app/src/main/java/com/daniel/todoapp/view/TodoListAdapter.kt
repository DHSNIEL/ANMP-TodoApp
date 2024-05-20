package com.daniel.todoapp.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.daniel.todoapp.databinding.TodoItemLayoutBinding
import com.daniel.todoapp.model.Todo

class TodoListAdapter(
    val todoList: ArrayList<Todo>, val adapterOnClick: (Todo) -> Unit
) : RecyclerView.Adapter<TodoListAdapter.TodoViewHolder>() {
    class TodoViewHolder(var binding: TodoItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        var binding =
            TodoItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TodoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.binding.checkTask.text = todoList[position].title

        holder.binding.imgEdit.setOnClickListener {
            val action = TodoListFragmentDirections.actionEditTodoFragment(todoList[position].uuid)

            Navigation.findNavController(it).navigate(action)
        }

        holder.binding.checkTask.isChecked = false

        holder.binding.checkTask.setOnCheckedChangeListener { compoundButton, b ->
            if (compoundButton.isPressed) {
                adapterOnClick(todoList[position])
            }

        }
    }

    fun updateTodoList(newtodolist: List<Todo>) {
        todoList.clear()
        todoList.addAll(newtodolist)
        notifyDataSetChanged()
    }
}