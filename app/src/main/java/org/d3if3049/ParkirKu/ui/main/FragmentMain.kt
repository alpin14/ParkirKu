package org.d3if3049.ParkirKu.ui.main


import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.d3if3049.ParkirKu.R
import org.d3if3049.ParkirKu.databinding.FragmentMainBinding
import org.d3if3049.ParkirKu.db.ParkirDb
import org.d3if3049.ParkirKu.model.HasilHitung


class FragmentMain : Fragment() {
    private lateinit var binding: FragmentMainBinding

    private val viewModel: MainViewModel by lazy {
        val db = ParkirDb.getInstance(requireContext())
        val factory = MainViewModelFactory(db.dao)
        ViewModelProvider(this,factory)[MainViewModel::class.java]
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.options_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_histori -> {
                findNavController().navigate(
                R.id.action_fragmentMain_to_historiFragment)
                return true
            }
        R.id.menu_about -> {
            findNavController().navigate(
                R.id.action_fragmentMain_to_aboutFragment)
            return true
                }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnHitung.setOnClickListener { hitungWarnet() }
        viewModel.getUserPassWarnet().observe(requireActivity()) {showResult(it)}
    }

    private fun hitungWarnet() {
        val jam = binding.jamInp.text.toString()

        if (TextUtils.isEmpty(jam)) {
            Toast.makeText(context, R.string.jam_invalid, Toast.LENGTH_LONG).show()
            return
        }
        val selectedValue = binding.tipeSpinner.selectedItem.toString()

        viewModel.tampungWarnet(jam.toInt(), selectedValue)

        Toast.makeText(context, "Biaya Parkir Ditampilkan", Toast.LENGTH_SHORT).show()
    }

    fun showResult(result: HasilHitung?){
        if (result == null) return
        binding.bayarTxtview.text = getString(R.string.harga_x, result.harga)

    }

}
