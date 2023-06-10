package org.d3if3049.ParkirKu.ui.histori

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.d3if3049.ParkirKu.R
import org.d3if3049.ParkirKu.databinding.FragmentHistoriBinding
import org.d3if3049.ParkirKu.db.ParkirDb

class HistoriFragment : Fragment() {
    private lateinit var binding: FragmentHistoriBinding
    private lateinit var myAdapter: HistoriAdapter
    private lateinit var factory: HistoriViewModelFactory
    private lateinit var dataStoreManager: DataStoreManager

    companion object{
        private var dataStoreManagerInstance: DataStoreManager? = null

        fun getDataStoreManager(context: Context): DataStoreManager {
            if (dataStoreManagerInstance == null) {
                dataStoreManagerInstance = DataStoreManager(context)
            }
            return dataStoreManagerInstance!!
        }
    }


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
        dataStoreManager = getDataStoreManager(requireContext())
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_switch_layout -> {
                val currentLayoutManager = binding.recyclerView.layoutManager
                if (currentLayoutManager is GridLayoutManager) {
                    binding.recyclerView.layoutManager = LinearLayoutManager(context)
                    lifecycleScope.launch {
                        dataStoreManager.saveToDataStore("linear")
                    }
                } else {
                    binding.recyclerView.layoutManager = GridLayoutManager(context, 2)
                    lifecycleScope.launch {
                        dataStoreManager.saveToDataStore("grid")
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myAdapter = HistoriAdapter()
        setHasOptionsMenu(true)
        with(binding.recyclerView) {
            addItemDecoration(DividerItemDecoration(context, RecyclerView.VERTICAL))
            adapter = myAdapter
            setHasFixedSize(true)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            dataStoreManager.getFromDataStore().collectLatest { prefs ->
                updateLayoutManager(prefs)
            }
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(
                context, LinearLayoutManager.VERTICAL,
                false)
            this.adapter = myAdapter
            setHasFixedSize(true)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            dataStoreManager.getFromDataStore().collectLatest { prefs ->
                updateLayoutManager(prefs)
            }
        }

        viewModel.data.observe(viewLifecycleOwner, {
            binding.emptyView.visibility = if (it.isEmpty())
                View.VISIBLE else View.GONE
            myAdapter.submitList(it)
        })
    }

    override fun onStop() {
        super.onStop()

        // Simpan preferensi layout ke DataStore
        lifecycleScope.launch {
            dataStoreManager.saveToDataStore(getCurrentLayoutManager())
        }
    }

    fun getCurrentLayoutManager(): String {
        val layoutManager = binding.recyclerView.layoutManager
        return if (layoutManager is GridLayoutManager) {
            "grid"
        } else {
            "linear"
        }
    }

    fun updateLayoutManager(layout: String) {
        binding.recyclerView.apply {
            layoutManager = if (layout == "grid") {
                GridLayoutManager(context, 2)
            } else {
                LinearLayoutManager(context)
            }
        }
    }

}