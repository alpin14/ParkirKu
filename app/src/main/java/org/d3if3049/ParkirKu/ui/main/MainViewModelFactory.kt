package org.d3if3049.ParkirKu.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3049.ParkirKu.db.ParkirDao

class MainViewModelFactory(private val db: ParkirDao): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel> create(modelClass: Class<T>): T{
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(db) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
    }
}