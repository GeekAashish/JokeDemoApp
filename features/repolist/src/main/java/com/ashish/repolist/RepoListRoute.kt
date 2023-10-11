package com.ashish.repolist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun RepoListRoute(
    viewModel:RepoListViewModel,
    onNavigateProfile:()->Unit
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val uiAction = remember(viewModel){
        val action:(RepoListUiAction)->Unit = { viewModel.action(it) }
        action
    }

    RepoListRoute(
        uiState = uiState,
        onRefreshRepoList = {
            uiAction(RepoListUiAction.FetchRepoList)
        }
    )
}

@Composable
private fun RepoListRoute(
    uiState: RepoListUiState,
    onRefreshRepoList:()->Unit
){
    RepoListScreen(
        uiState = uiState,
        onRefreshRepoList = onRefreshRepoList
    )
}