package com.example.newsapp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.newsapp.R
import com.example.newsapp.data.model.Article
import com.example.newsapp.ui.viewmodel.NewsViewModel
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsScreen(navController: NavController, viewModel: NewsViewModel = hiltViewModel()) {

    val news by viewModel.news.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(true)
    val errorMessage by viewModel.errorMessage.observeAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("News App") }) }
    ) { paddingValues ->
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
            )
        } else if (!errorMessage.isNullOrEmpty()) {
            Text(
                text = errorMessage ?: "Error",
                modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                LazyColumn {
                    items(news) { article ->
                        NewsItem(article = article) {
                            viewModel.selectArticle(article)
                            navController.navigate("detail")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NewsItem(article: Article, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                onClick()
            },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            article.urlToImage.let {
                GlideImage(
                    imageModel = { it ?: R.drawable.logo },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    failure = {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "dummy image"
                        )
                    },
                )
                Spacer(modifier = Modifier.height(8.dp))
            }

            Text(text = article.title ?: "Not found", style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Author : ${article.author ?: "Unknown"}",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = article.description ?: "No Data found",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}