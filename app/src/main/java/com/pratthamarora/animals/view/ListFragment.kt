package com.pratthamarora.animals.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.pratthamarora.animals.R
import com.pratthamarora.animals.viewmodel.ListViewModel
import kotlinx.android.synthetic.main.fragment_list.*


class ListFragment : Fragment() {
    private lateinit var viewModel: ListViewModel
    private val listAdapter = AnimalListAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ListViewModel::class.java)

        viewModel.animals.observe(viewLifecycleOwner, Observer {
            it?.let {
                animalList.visibility = View.VISIBLE
                listAdapter.updateAnimalList(it)
            }

        })

        viewModel.loading.observe(viewLifecycleOwner, Observer {
            it?.let {
                loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    listError.visibility = View.GONE
                    animalList.visibility = View.GONE
                }
            }

        })

        viewModel.loadingError.observe(viewLifecycleOwner, Observer {
            it?.let {
                listError.visibility = if (it) View.VISIBLE else View.GONE
            }

        })
        viewModel.refresh()

        animalList.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = listAdapter
        }

        refreshLayout.setOnRefreshListener {
            animalList.visibility = View.GONE
            listError.visibility = View.GONE
            loadingView.visibility = View.VISIBLE
            viewModel.hardRefresh()
            refreshLayout.isRefreshing = false
        }
    }

}
