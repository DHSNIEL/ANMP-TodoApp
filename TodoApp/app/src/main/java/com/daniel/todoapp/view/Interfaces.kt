package com.daniel.todoapp.view

import android.view.View
import android.widget.CompoundButton
import com.daniel.todoapp.model.Todo

interface TodoCheckedChangeListener {
    fun onCheckedChange(
        cb: CompoundButton,
        isChecked: Boolean,
        obj: Todo
    )
}

interface TodoEditClickListener {
    fun onTodoEditClick(v: View)
}

interface RadioClickListener{
    fun onRadioClick(v: View)
}