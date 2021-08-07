package com.ntikhoa.algorithmvisualizer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val customView = findViewById<CustomView>(R.id.myCustomView)
        customView.setOnSortListener(object: CustomView.OnSortListener {
            override suspend fun onSort(array: List<Int>) {
                for (i in 0 until array.size) {
                    for (j in i + 1 until array.size) {
                        if (array[j] < array[i]) {
                            customView.swap(i, j)
                        }
                    }
                }
            }
        })

    }
}