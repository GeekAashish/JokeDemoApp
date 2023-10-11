package com.ashish.domain.usecase

import com.ashish.domain.repository.GithubRepository
import com.ashish.domain.utils.ApiUseCaseParams
import com.ashish.domain.utils.Result
import com.ashish.entity.RepoItemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepoListUseCase @Inject constructor(
    private val repository: GithubRepository
):ApiUseCaseParams<RepoListUseCase.Params, RepoItemEntity>{
    override suspend fun execute(params: Params): Flow<Result<RepoItemEntity>> {
        return repository.fetchRepoList(params)
    }
    data class Params(val joke:String)
}