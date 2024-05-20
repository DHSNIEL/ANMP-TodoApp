package com.daniel.todoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.daniel.todoapp.databinding.FragmentTodoListBinding
import com.daniel.todoapp.viewmodel.ListTodoViewModel

class TodoListFragment : Fragment() {
    private lateinit var viewModel: ListTodoViewModel
    private lateinit var binding: FragmentTodoListBinding
    private val adapter = TodoListAdapter(arrayListOf(), {
        item -> viewModel.clearTask(item)
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodoListBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ListTodoViewModel::class.java)

        viewModel.refresh()

        binding.recViewTodo.layoutManager = LinearLayoutManager(context)

        binding.recViewTodo.adapter = adapter

        binding.btnFab.setOnClickListener{
            val action = TodoListFragmentDirections.actionCreateTodo()
            Navigation.findNavController(it).navigate(action)
        }

        fun observeViewModel(){
            viewModel.todoLD.observe(viewLifecycleOwner,
                Observer {
                    adapter.updateTodoList(it)

                    if(it.isEmpty()){
                        binding.recViewTodo.visibility = View.GONE

                        binding.txtError.text = "Your todo is still empty"
                    }else{
                        binding.recViewTodo.visibility = View.VISIBLE
                        binding.txtError.visibility = View.GONE
                    }
            })

            viewModel.loadingLD.observe(viewLifecycleOwner, Observer {
                if(it ==false){
                    binding.progressBar.visibility = View.GONE
                }else{
                    binding.progressBar.visibility = View.VISIBLE
                }
            })

            viewModel.todoLoadErrorLD.observe(viewLifecycleOwner, Observer {
                if(it ==false){
                    binding.txtError.visibility = View.GONE
                }else{
                    binding.txtError.visibility = View.VISIBLE
                }
            })
        }
    }
}