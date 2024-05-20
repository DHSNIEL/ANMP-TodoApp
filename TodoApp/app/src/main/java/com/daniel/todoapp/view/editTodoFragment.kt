package com.daniel.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.daniel.todoapp.R
import com.daniel.todoapp.databinding.FragmentCreateTodoBinding
import com.daniel.todoapp.model.Todo
import com.daniel.todoapp.viewmodel.DetailTodoViewModel


class editTodoFragment : Fragment() {

    private lateinit var binding: FragmentCreateTodoBinding
    private lateinit var viewModel: DetailTodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)

        binding.textView.text = "edit todo"

        val uuid = editTodoFragmentArgs.fromBundle(requireArguments()).uuid
        viewModel.fetch(uuid)

        binding.btnAdd.setOnClickListener{
            var title = binding.txtTitle.text.toString()
            var notes = binding.txtNotes.text.toString()

            var radio_id = binding.radioGroupPriority.checkedRadioButtonId
            val radio = view.findViewById<RadioButton>(radio_id)
            val priority = radio.tag.toString().toInt()

            viewModel.update(title, notes, priority, uuid)

            Toast.makeText(context, "Todo updated", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).popBackStack()
        }

        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.todoLD.observe(viewLifecycleOwner, Observer {
            binding.txtTitle.setText(it.title)
            binding.txtNotes.setText(it.notes)
            when (it.priority) {
                1       -> binding.radioLow.isChecked       = true
                2       -> binding.radioMedium.isChecked    = true
                else    -> binding.radioHigh.isChecked      = true
            }
        })


    }

}