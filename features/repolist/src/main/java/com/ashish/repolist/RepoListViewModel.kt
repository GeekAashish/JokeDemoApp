package com.ashish.repolist

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashish.domain.usecase.RepoListUseCase
import com.ashish.domain.utils.Result
import com.ashish.entity.RepoItemEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject

@HiltViewModel
class RepoListViewModel @Inject constructor(
    private val repoListUseCase: RepoListUseCase
) : ViewModel() {
    val action: (RepoListUiAction) -> Unit

    //    var jokesList: SnapshotStateList<RepoItemEntity> = mutableStateListOf()
    private var jokesList = mutableStateListOf<RepoItemEntity>()
    private val viewModelState = MutableStateFlow(RepoListViewModelState(isLoading = true))
    val uiState = viewModelState
        .map(RepoListViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        action = {
            when (it) {
                RepoListUiAction.FetchRepoList -> fetchRepoList()
            }
        }

        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                fetchRepoList()
            }
        }, 0, 5000)

    }

    private fun fetchRepoList() {
        viewModelScope.launch {
            repoListUseCase.execute(RepoListUseCase.Params(joke = "joke")).collect { response ->
                when (response) {
                    is Result.Error -> viewModelState.update {
                        it.copy(error = response.message)
                    }

                    is Result.Loading -> viewModelState.update {
                        it.copy(error = "", isLoading = response.loading)
                    }

                    is Result.Success -> viewModelState.update {
                        it.copy(repoList = updatedList(response.data))
                    }
                }
            }
        }
    }

    private fun updatedList(joke: RepoItemEntity): List<RepoItemEntity> {
        when (jokesList.size) {
            10 -> {
                jokesList.removeAt(jokesList.size - 1)
            }
        }
        joke.date = getCurrentDate(System.currentTimeMillis())
        if (jokesList.isEmpty()) jokesList.add(joke) else jokesList.add(0, joke)
        return jokesList
    }

    private fun getCurrentDate(currentTimeMillis: Long): String {
        val formatter = SimpleDateFormat("MMM dd, yyyy hh:mm:ss a")
        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = currentTimeMillis
        return formatter.format(calendar.time)
    }

}

private data class RepoListViewModelState(
    val isLoading: Boolean = false,
    val error: String = "",
    val repoList: List<RepoItemEntity> = listOf()
) {
    fun toUiState(): RepoListUiState =
        if (repoList.isEmpty()) RepoListUiState.RepoListEmpty(isLoading = isLoading, error = error)
        else RepoListUiState.HasRepoList(isLoading = isLoading, error = error, repoList = repoList)
}

sealed interface RepoListUiState {
    val isLoading: Boolean
    val error: String

    data class HasRepoList(
        val repoList: List<RepoItemEntity>,
        override val isLoading: Boolean,
        override val error: String
    ) : RepoListUiState

    data class RepoListEmpty(
        override val isLoading: Boolean,
        override val error: String
    ) : RepoListUiState
}

sealed class RepoListUiAction {
    object FetchRepoList : RepoListUiAction()
}