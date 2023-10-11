package com.ashish.repolist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ashish.common.compose.ApplicationAppbar
import com.ashish.common.compose.NetworkErrorMessage
import com.ashish.entity.RepoItemEntity

@Composable
fun RepoListScreen(
    uiState: RepoListUiState,
    onRefreshRepoList: () -> Unit
) {
    RepoListScreen(
        uiState = uiState,
        hasRepoList = { repoItem, modifier ->
            RepoListItem(
                modifier = modifier,
                repoItem = repoItem
            )
        },
        onRefreshRepoList = onRefreshRepoList
    )
}

@Composable
private fun RepoListScreen(
    uiState: RepoListUiState,
    hasRepoList: @Composable (repoItem: RepoItemEntity, modifier: Modifier) -> Unit,
    onRefreshRepoList: () -> Unit
) {
    Scaffold(
        topBar = { ApplicationAppbar(title = "Unlimint Jokes") },
    ) {
        val modifier = Modifier
            .padding(it)
        FullScreenLoading(
            isLoading = uiState.isLoading,
            loadingContent = { /*CircularProgressBar()*/ },
            content = {
                when (uiState) {

                    is RepoListUiState.HasRepoList -> {
                        LazyColumn {
                            items(items = uiState.repoList) { repoItem ->
                                hasRepoList(repoItem = repoItem, modifier = modifier)
                            }
                        }
                    }

                    is RepoListUiState.RepoListEmpty -> {
                        if (uiState.error.isNotEmpty()) {
                            NetworkErrorMessage(
                                message = uiState.error,
                                onClickRefresh = onRefreshRepoList
                            )
                        } else {
                            Text(text = "You have no joke list right now")
                        }
                    }
                }
            }
        )


    }
}

@Composable
private fun FullScreenLoading(
    isLoading: Boolean,
    loadingContent: @Composable () -> Unit,
    content: @Composable () -> Unit,
) {
    if (isLoading) loadingContent()
    else content()
}

@Composable
private fun RepoListItem(
    modifier: Modifier = Modifier,
    repoItem: RepoItemEntity
) {
    Card(
        modifier = modifier
            .padding(start = 12.dp, end = 12.dp, top = 3.dp, bottom = 3.dp)
            .clickable {
            }
            .background(Color.Green),
        backgroundColor = Color.Green,
        shape = RoundedCornerShape(size = 12.dp)
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = modifier.width(16.dp))
                Column {
                    Text(
                        text = repoItem.joke,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.DarkGray
                    )
                    Spacer(modifier = modifier.padding(top = 5.dp))
                    Text(
                        text = repoItem.date,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}



