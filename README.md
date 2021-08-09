# Algorithm Visualizer
Demo link: https://youtu.be/aV2xouxZJtk
# Introduction
Algorithm Visualizer is an app for visualizing sorting algorithm. 
You can use the SortView widget in the source code to implement your own sorting algorithm.
This app visualizes sorting algorithm only. I am planing on impletemting PathFindingView widget for visualizaing path finding algorithm.
# Technique uses
* Kotlin
* Custom View
* Coroutines
# How does it work

### Implement your own sorting algorithm

You can implementing your own sorting algorithm in the setOnSortListener callback
```
sortView.setOnSortListener(object : SortView.OnSortListener {
                override suspend fun onSort(array: List<Int>) {
                    //bubble sort
                    for (i in 0 until array.size) {
                      for (j in i + 1 until array.size) {
                        if (array[j] < array[i]) {
                          binding.sortView.swap(i, j)
                      }
                    }
                  }
                }
            })
``` 
### Start Method

This function will start the animation
```
sortView.start()
```
### Cancel Method

This method will cancel the animation. Note that once you cancel, you cannot start it again, you have to call the function reset
```
sortView.cancel()
```
### Reset Method

This method will reset the SortView.
```
sortView.reset()
```
## SetTotalSize Method

This method is used to set the number of columns in the SortView. Note that each SortView will have the maximum number of columns associate with the size of the view.
Therefore, if you set the total size larger the maximum number of columns, it will automatically set to the maximum number.
For example, the maximum number of 800x800px SortView is 40, so if you set the total size larger than 40, the total size will be 40.
Note that the setTotalSize method is only apply when you call the reset method only.
```
setTotalSize(Integer.MAX_VALUE)
```
