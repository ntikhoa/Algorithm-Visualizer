package com.ntikhoa.algorithmvisualizer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.Window
import android.view.WindowManager
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val sortView = findViewById<SortView>(R.id.myCustomView)
        sortView.setOnSortListener(object : SortView.OnSortListener {
            override suspend fun onSort(array: List<Int>) {
//                sortView.swap(0, 1)
                for (i in 0 until array.size) {
                    for (j in i + 1 until array.size) {
                        if (array[j] < array[i]) {
                            sortView.swap(i, j)
                        }
                    }
                }
            }
        })
//        sortView.setTotalSize(Int.MAX_VALUE)

        //Another way to call custom view
//        Handler(mainLooper).postDelayed({
//            sortView.startSort()
//        }, 1000)
//
//        val button = findViewById<Button>(R.id.btnSort)
//        button.setOnClickListener {
//            sortView.startSort()
//        }
    }
}