package org.d3if3049.ParkirKu.ui.histori

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import org.d3if3049.ParkirKu.databinding.FragmentHistoriBinding
import org.d3if3049.ParkirKu.db.ParkirDb

class HistoriFragment : Fragment() {
    private lateinit var binding: FragmentHistoriBinding
    private lateinit var myAdapter: HistoriAdapter


    private val viewModel: HistoriViewModel by lazy {
        val db = ParkirDb.getInstance(requireContext())
        val factory = HistoriViewModelFactory(db.dao)
        ViewModelProvider(this, factory)[HistoriViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoriBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        myAdapter = HistoriAdapter()
        with(binding.recyclerView) {
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = myAdapter
            setHasFixedSize(true)
        }
        viewModel.data.observe(viewLifecycleOwner, {
            binding.emptyView.visibility = if (it.isEmpty())
                View.VISIBLE else View.GONE
            myAdapter.submitList(it)
        })
    }

}