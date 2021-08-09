package com.ntikhoa.algorithmvisualizer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.ntikhoa.algorithmvisualizer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    private val selectedAlgo get() = binding.sortSpinner.selectedItemPosition

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setUpSpinner()

        binding.apply {
            sortView.setOnSortListener(object : SortView.OnSortListener {
                override suspend fun onSort(array: List<Int>) {
                    when (selectedAlgo) {
                        0 -> bubbleSort(array)
                        1 -> insertionSort(array)
                        2 -> selectionSort(array)
                        3 -> quickSort(array, 0, array.size - 1)
                        4 -> heapSort(array)
                    }
                }
            })
//            sortView.setTotalSize(Int.MAX_VALUE)
        }

        //Another way to call custom view
//        Handler(mainLooper).postDelayed({
//            sortView.startSort()
//        }, 1000)

        setOnClickListener()
    }

    private fun setUpSpinner() {
        val sortAlgoList = resources.getStringArray(R.array.sorting_algo_list)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            sortAlgoList
        )
        binding.sortSpinner.adapter = adapter
    }

    private fun setOnClickListener() {
        binding.apply {
            btnStart.setOnClickListener(this@MainActivity)
            btnCancel.setOnClickListener(this@MainActivity)
            btnReset.setOnClickListener(this@MainActivity)
        }
    }

    override fun onClick(v: View?) {
        binding.apply {
            when (v?.id) {
                btnStart.id -> {
                    sortView.start()
                }
                btnCancel.id -> sortView.cancel()
                btnReset.id -> {
                    if (!etTotalSize.text.isEmpty())
                        sortView.setTotalSize(Integer.parseInt(etTotalSize.text.toString()))
                    sortView.reset()
                }
            }
        }
    }

    private suspend fun bubbleSort(array: List<Int>) {
        for (i in 0 until array.size) {
            for (j in i + 1 until array.size) {
                if (array[j] < array[i]) {
                    binding.sortView.swap(i, j)
                }
            }
        }
    }

    private suspend fun quickSort(arr: List<Int>, low: Int, high: Int) {
        if (low < high) {
            val p = partition(arr, low, high)

            quickSort(arr, low, p - 1)
            quickSort(arr, p + 1, high)
        }
    }

    private suspend fun partition(arr: List<Int>, low: Int, high: Int): Int {
        val pivot = arr[high]

        var i = (low - 1)

        var j = low
        while (j <= high - 1) {
            if (arr[j] < pivot) {
                i++
                binding.sortView.swap(i, j)
            }
            j++
        }
        binding.sortView.swap(i + 1, high)
        return (i + 1)
    }

    private suspend fun selectionSort(arr: List<Int>) {
        val n = arr.size

        // One by one move boundary of unsorted subarray
        for (i in 0 until n - 1) {
            var min_idx = i
            for (j in i + 1 until n)
                if (arr[j] < arr[min_idx])
                    min_idx = j;

            // Swap the found minimum element with the first
            // element
            binding.sortView.swap(min_idx, i)
        }
    }

    private suspend fun insertionSort(arr: List<Int>) {
        val n = arr.size
        for (i in 1 until n) {
            val key = arr[i]
            var j = i - 1

            while (j >= 0 && arr[j] > key) {
                binding.sortView.swap(j + 1, j)
                j = j - 1
            }
        }
    }

    private suspend fun heapSort(arr: List<Int>) {
        val n = arr.size

        // Build heap (rearrange array)
        for (i in n / 2 - 1 downTo 0)
            heapify(arr, n, i);

        // One by one extract an element from heap
        for (i in n - 1 downTo 1) {
            // Move current root to end
            binding.sortView.swap(0, i)

            // call max heapify on the reduced heap
            heapify(arr, i, 0);
        }
    }

    // To heapify a subtree rooted with node i which is
    // an index in arr[]. n is size of heap
    private suspend fun heapify(arr: List<Int>, n: Int, i: Int) {
        var largest = i; // Initialize largest as root
        val l = 2 * i + 1; // left = 2*i + 1
        val r = 2 * i + 2; // right = 2*i + 2

        // If left child is larger than root
        if (l < n && arr[l] > arr[largest])
            largest = l;

        // If right child is larger than largest so far
        if (r < n && arr[r] > arr[largest])
            largest = r;

        // If largest is not root
        if (largest != i) {
            binding.sortView.swap(largest, i)
            // Recursively heapify the affected sub-tree
            heapify(arr, n, largest);
        }
    }
}