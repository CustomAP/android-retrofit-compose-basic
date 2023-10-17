package com.example.simpleretrofitcallnomvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.simpleretrofitcallnomvvm.ui.theme.SimpleRetrofitCallNoMVVMTheme
import com.example.simpleretrofitcallnomvvm.view.PhotoItem
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    var photoListResponse: List<Photo> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getPhotos()

        setContent {
            SimpleRetrofitCallNoMVVMTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (errorMessage.isNotEmpty()) {
                        Text(errorMessage)
                    } else {
                        PhotoList(photoList = photoListResponse)
                    }
                }
            }
        }
    }

    fun getPhotos() {
        lifecycleScope.launch { // Need to add gradle dependency
            val apiService = ApiService.getInstance()
            try {
                val photoList = apiService.getPhotos()
                photoListResponse = photoList
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}

@Composable
fun PhotoList(photoList: List<Photo>) {
    LazyColumn {
        itemsIndexed(items = photoList) { index, item ->
            PhotoItem(photo = item)
        }
    }
}