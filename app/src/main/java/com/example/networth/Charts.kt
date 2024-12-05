package com.example.networth

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

fun getBarData(asset: Float, liability: Float, savings: Float, barColors: List<Int>): BarData {
    // Create bar entries for the data
    val entries = listOf(
        BarEntry(0f, asset),       // Bar for assets
        BarEntry(1f, liability),  // Bar for liabilities
        BarEntry(2f, savings)     // Bar for savings
    )

    // Create a dataset and customize its appearance
    val dataSet = BarDataSet(entries, "Net Worth Breakdown").apply {
        colors = barColors  // Apply dynamic colors
        valueTextColor = android.graphics.Color.BLACK
        valueTextSize = 12f
    }

    return BarData(dataSet).apply {
        barWidth = 0.5f  // Adjust bar width as needed
    }
}





fun getPieData(items: List<Item>, chartColors: List<Int>): PieData {
    val entries = items.map { PieEntry(it.amount, it.name) }
    val dataSet = PieDataSet(entries, "Item Breakdown").apply {
        colors = chartColors
        valueTextColor = android.graphics.Color.WHITE
        valueTextSize = 12f
    }
    return PieData(dataSet)
}




fun generateChartColors(themeColors: List<Color>, itemCount: Int): List<Int> {
    val baseColors = themeColors.map { it.toArgb() }
    val additionalColors = mutableListOf<Int>()

    // Generate additional colors using a hue variation
    val hueStep = 360f / itemCount
    for (i in 0 until itemCount) {
        val hue = (i * hueStep) % 360
        val newColor = Color.hsl(hue, 0.7f, 0.6f).toArgb() // Adjust saturation and lightness as needed
        additionalColors.add(newColor)
    }

    return (baseColors + additionalColors).distinct().take(itemCount)
}