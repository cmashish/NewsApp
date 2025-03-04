package com.example.newsapp.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newsapp.R
import com.example.newsapp.data.model.Article
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsDetailScreen(article: Article) {
    Scaffold(
        topBar = {
            TopAppBar(title = { article.source.name?.let { Text("Source : $it") } })
        }
    ) {paddingValues->
        Column(modifier = Modifier.padding(paddingValues)) {
            GlideImage(
                imageModel = { article.urlToImage ?: R.mipmap.ic_launcher_round },
                modifier = Modifier.fillMaxWidth().height(250.dp)
            )
            Text(text = article.title ?: "Default Title", fontWeight = FontWeight.Bold, fontSize = 20.sp, modifier = Modifier.padding(8.dp))
            Text(text = "By ${article.author ?: "Unknown"}", fontSize = 14.sp, modifier = Modifier.padding(8.dp))
            Text(text = article.description ?: "Full article unavailable", fontSize = 16.sp, modifier = Modifier.padding(8.dp))
        }
    }
}
