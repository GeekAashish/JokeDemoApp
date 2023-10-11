package com.ashish.data.repoimpl

import com.ashish.data.apiservice.ApiService
import com.ashish.data.mapper.RepoListItemMapper
import com.ashish.data.utils.NetworkBoundResource
import com.ashish.data.utils.mapFromApiResponse
import com.ashish.domain.repository.GithubRepository
import com.ashish.domain.usecase.RepoListUseCase
import com.ashish.domain.utils.Result
import com.ashish.entity.RepoItemEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GithubRepoImpl @Inject constructor(
    private val apiService: ApiService,
    private val networkBoundResources: NetworkBoundResource,
    private val repositoryListItemMapper: RepoListItemMapper,
):GithubRepository{

    override suspend fun fetchRepoList(params: RepoListUseCase.Params): Flow<Result<RepoItemEntity>> {
        return mapFromApiResponse(
            result = networkBoundResources.downloadData {
                apiService.getJokes()
            },repositoryListItemMapper
        )
    }

}