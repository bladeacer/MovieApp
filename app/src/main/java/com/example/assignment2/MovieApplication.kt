package com.example.assignment2

import android.app.Application
import com.example.assignment2.data.InventoryDatabase

class MovieApplication: Application() {
    val database: InventoryDatabase by lazy { InventoryDatabase.getDatabase(this) }
}