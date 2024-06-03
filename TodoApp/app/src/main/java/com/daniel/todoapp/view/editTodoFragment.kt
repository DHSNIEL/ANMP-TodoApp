package com.daniel.todoapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.daniel.todoapp.databinding.FragmentEditTodoBinding
import com.daniel.todoapp.view.RadioClickListener
import com.daniel.todoapp.view.TodoEditClickListener
import com.daniel.todoapp.viewmodel.DetailTodoViewModel

class EditTodoFragment : Fragment(), RadioClickListener, TodoEditClickListener {
    private lateinit var binding: FragmentEditTodoBinding
    private lateinit var viewModel: DetailTodoViewModel

    @SuppressLint("SuspiciousIndentation")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailTodoViewModel::class.java)
        binding.txtJudulTodo.text = "Edit Todo"
        val uuid = EditTodoFragmentArgs.fromBundle(
            requireArguments()
        ).uuid
        viewModel.fetch(uuid)

/*
//        binding.btnAdd.setOnClickListener {
//            val radioID = binding.radioGroupPriority.checkedRadioButtonId
//            val radio = view.findViewById<RadioButton>(radioID)
//            val priority = radio.tag.toString().toInt()
//
//            viewModel.update(
//                binding.txtTitle.text.toString(), binding.txtNotes.text.toString(),
//                priority, uuid
//            )
//
//            Toast.makeText(context, "Todo updated", Toast.LENGTH_SHORT).show()
//            Navigation.findNavController(it).popBackStack()
//        }*/

        binding.radiolistener = this
        binding.saveListener = this
        observeViewModel()
    }

    fun observeViewModel() {
        viewModel.todoLD.observe(viewLifecycleOwner,
            Observer {
                binding.todo = it
                /*
                region old method
                binding.txtTitle.setText(it.title)
                binding.txtNotes.setText(it.notes)

                when (it.priority) {
                1 -> binding.radioLow.isChecked = true
                2 -> binding.radioMedium.isChecked = true
                else -> binding.radioHigh.isChecked = true
                }
                endregion method
                */
            })
    }

    override fun onRadioClick(v: View) {
        binding.todo!!.priority = v.tag.toString().toInt()
    }

    override fun onTodoEditClick(v: View) {
        viewModel.updateTodo(binding.todo!!)
        Toast.makeText(context, "Todo updated", Toast.LENGTH_SHORT).show()
    }

}