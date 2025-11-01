package com.campuscoders.posterminalapp.data.remote.api

import com.campuscoders.posterminalapp.data.remote.dto.LoginResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApiService {
    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("terminalid") terminalId: String,
        @Field("taxid") taxId: String,
        @Field("memberstore") memberId: String,
        @Field("password") password: String
    ): Response<LoginResponse>
}
