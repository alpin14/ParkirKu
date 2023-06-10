package org.d3if3049.ParkirKu.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3049.ParkirKu.db.ParkirDao
import org.d3if3049.ParkirKu.db.ParkirEntity
import org.d3if3049.ParkirKu.model.HasilHitung
import org.d3if3049.ParkirKu.model.Parkir

class MainViewModel(private val db: ParkirDao): ViewModel() {
    private val hasilHitung = MutableLiveData<HasilHitung>()

    fun tampungWarnet(jam: Int, tipe: String){
        val hitungWarnet = Parkir (
            jam = jam,
            tipe = tipe)
        hasilHitung.value = hitungWarnet.hitungPemakaian()
    }

    fun Parkir.hitungPemakaian(): HasilHitung {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val dataWarnet = ParkirEntity(
                    jam = jam,
                    tipe = tipe
                )
                db.insert(dataWarnet)
            }
        }
        val jam = jam
        val tipekomputer = if (tipe.equals("Motor" , ignoreCase = true)) {
            jam * 2000
        } else if (tipe.equals("Mobil" , ignoreCase = true)) {
            jam * 5000
        } else {
            0
        }
        return HasilHitung(jam, tipekomputer)
    }

    fun getUserPassWarnet(): LiveData<HasilHitung?> = hasilHitung
}