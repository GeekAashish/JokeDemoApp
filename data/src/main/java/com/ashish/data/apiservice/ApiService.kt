package com.ashish.data.apiservice

import com.ashish.apiresponse.RepoItemApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("api?format=json")
    suspend fun getJokes(
    ): Response<RepoItemApiResponse>
}