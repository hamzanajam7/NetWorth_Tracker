package com.example.networth

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NetWorthViewModel @Inject constructor() : ViewModel() {

    // Use state variables to ensure the UI updates automatically
    var itemList = mutableStateListOf<Item>()
        private set

    var netWorth by mutableStateOf(0.0f)
        private set

    var netAssets by mutableStateOf(0.0f)
        private set

    var netLiability by mutableStateOf(0.0f)
        private set

    var netSavings by mutableStateOf(0.0f)
        private set

    private var _name: String = "Guest"
    private var _birthYear: Int = 2000
    private var _birthMonth: String = "Jan"
    private var _birthDay: Int = 1
    private var _retirementAge: Int = 60

    // Profile getters and setters
    fun getName(): String = _name
    fun getBirthYear(): Int = _birthYear
    fun getBirthMonth(): String = _birthMonth
    fun getBirthDay(): Int = _birthDay
    fun getRetirementAge(): Int = _retirementAge

    fun setName(name: String) {
        _name = name
    }

    fun setBirthInfo(year: Int, month: String, day: Int) {
        _birthYear = year
        _birthMonth = month
        _birthDay = day
    }

    fun setRetirementAge(age: Int) {
        _retirementAge = age
    }

    // Update method to calculate net worth and its components
    private fun updateNetWorth() {
        var assetTotal = 0.0f
        var savingTotal = 0.0f
        var liabilityTotal = 0.0f

        for (item in itemList) {
            when (item.category) {
                "assets" -> assetTotal += item.amount
                "savings" -> savingTotal += item.amount
                "liabilities" -> liabilityTotal += item.amount
                else -> Log.d("NetWorthDebug", "Unknown category: ${item.category}")
            }
        }

        netAssets = assetTotal
        netSavings = savingTotal
        netLiability = liabilityTotal
        netWorth = assetTotal + savingTotal - liabilityTotal

        Log.d("NetWorthDebug", "Assets: $netAssets, Savings: $netSavings, Liabilities: $netLiability, NetWorth: $netWorth")

    }

    // Getters for category-specific lists
    fun getCategoryList(type: String): List<Item> {
        return itemList.filter { it.category == type }
    }

    // Add, remove, and clear items in the list
    fun addItem(item: Item) {
        itemList.add(item)
        Log.d("NetWorthDebug", "Current itemList: $itemList")
        Log.d("NetWorthDebug", "Item added: $item")
        updateNetWorth()
    }

    fun removeItem(item: Item) {
        itemList.remove(item)
        updateNetWorth()
    }

    fun clearItems() {
        itemList.clear()
        updateNetWorth()
    }

    // Method to change the status of an item
    fun changeItemStatus(item: Item, value: Boolean) {
        val index = itemList.indexOf(item)
        if (index >= 0) {
            itemList[index] = item.copy(status = value)
        }
    }
}

