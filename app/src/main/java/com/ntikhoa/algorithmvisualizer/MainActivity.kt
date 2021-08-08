package com.ntikhoa.algorithmvisualizer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import com.ntikhoa.algorithmvisualizer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    private lateinit var sortView: SortView

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        sortView = findViewById<SortView>(R.id.myCustomView)
        sortView.setOnSortListener(object : SortView.OnSortListener {
            override suspend fun onSort(array: List<Int>) {
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

        binding.apply {
            btnStart.setOnClickListener(this@MainActivity)
            btnCancel.setOnClickListener(this@MainActivity)
            btnReset.setOnClickListener(this@MainActivity)
        }
    }

    override fun onClick(v: View?) {
        binding.apply {
            when (v?.id) {
                btnStart.id -> sortView.start()
                btnCancel.id -> sortView.cancel()
                btnReset.id -> sortView.reset()
            }
        }
    }
}