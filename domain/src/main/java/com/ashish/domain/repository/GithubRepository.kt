package com.ashish.domain.repository

import com.ashish.domain.usecase.RepoListUseCase
import com.ashish.domain.utils.Result
import com.ashish.entity.RepoItemEntity
import kotlinx.coroutines.flow.Flow

interface GithubRepository {
    suspend fun fetchRepoList(params: RepoListUseCase.Params): Flow<Result<RepoItemEntity>>
}