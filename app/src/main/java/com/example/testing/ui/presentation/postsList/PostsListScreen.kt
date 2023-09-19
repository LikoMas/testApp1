package com.example.testing.ui.presentation.postsList

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.testing.domain.model.Post

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostsListScreen(
    navigateToSinglePostScreen: (Int) -> Unit,
    navigateToLoginScreen: () -> Unit,
    viewModel: PostsListScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text("Назва додатку") },
                actions = {
                    IconButton(
                        onClick = {
                            viewModel.loadPostsList()
                        }
                    ) {
                        Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                    IconButton(
                        onClick = {
                            navigateToLoginScreen()
                        }
                    ) {
                        Icon(imageVector = Icons.Default.ExitToApp, contentDescription = "Exit")
                    }
                })
        },
    ) {
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Завантаження...", style = MaterialTheme.typography.headlineLarge)
            }
        } else if (uiState.errorMessage != null) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(uiState.errorMessage!!, style = MaterialTheme.typography.headlineLarge)
            }
        } else {
            DrawPostsList(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it),
                posts = uiState.postsList,
                columns = 1,
                navigateToSinglePostScreen = navigateToSinglePostScreen
            )
        }
    }
}

@Composable
fun DrawPost(post: Post, navigateToSinglePostScreen: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navigateToSinglePostScreen(post.id) }
            .padding(16.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = post.title,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = post.body,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun DrawPostsList(
    modifier: Modifier = Modifier,
    posts: List<Post>,
    columns: Int = 2,
    navigateToSinglePostScreen: (Int) -> Unit
) {
    val totalColumns: Int = if (columns <= 0) 1 else columns
    Row(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        for (i in 0 until totalColumns) {
            Column(modifier = Modifier.weight(weight = 1f)) {
                posts.filterIndexed { index, _ ->
                    index % totalColumns == i
                }.forEach { post ->
                    DrawPost(post = post, navigateToSinglePostScreen = navigateToSinglePostScreen)
                }
            }
        }
    }
}