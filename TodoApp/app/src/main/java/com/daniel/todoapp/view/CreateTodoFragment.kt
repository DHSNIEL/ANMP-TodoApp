package com.daniel.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.daniel.todoapp.R
import com.daniel.todoapp.databinding.FragmentCreateTodoBinding
import com.daniel.todoapp.model.Todo
import com.daniel.todoapp.viewmodel.DetailTodoViewModel
import com.daniel.todoapp.viewmodel.ListTodoViewModel

class CreateTodoFragment : Fragment() {
    private lateinit var viewModel: DetailTodoViewModel

    private lateinit var binding: FragmentCreateTodoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)

        binding.btnAdd.setOnClickListener {
            var title = binding.txtTitle.text.toString()
            var notes = binding.txtNotes.text.toString()

            var radio_id = binding.radioGroupPriority.checkedRadioButtonId
            val radio = it.findViewById<RadioButton>(radio_id)
            val priority = radio.tag.toString().toInt()

            var todo = Todo(title, notes, priority)
            viewModel.addTodo(todo)

            Toast.makeText(it.context, "Todo Added", Toast.LENGTH_SHORT).show()

            Navigation.findNavController(it).popBackStack()

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_todo, container, false)
    }
}