package org.d3if3049.ParkirKu.ui.histori

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.d3if3049.ParkirKu.db.ParkirDao

class HistoriViewModel(private val db: ParkirDao) : ViewModel() {
    val data = db.getLastWarnet()

    fun hapusdata(id: Long) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            db.deleteHistory(id)
        }
}
}