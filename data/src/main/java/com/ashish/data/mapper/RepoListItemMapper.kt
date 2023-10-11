package com.ashish.data.mapper

import com.ashish.apiresponse.RepoItemApiResponse
import com.ashish.data.utils.Mapper
import com.ashish.entity.RepoItemEntity
import javax.inject.Inject

class RepoListItemMapper @Inject constructor() :
    Mapper<RepoItemApiResponse, RepoItemEntity> {
    override fun mapFromApiResponse(type: RepoItemApiResponse): RepoItemEntity {
        return RepoItemEntity(
            joke = type.joke ?: "",
            date = ""
        )
    }
}