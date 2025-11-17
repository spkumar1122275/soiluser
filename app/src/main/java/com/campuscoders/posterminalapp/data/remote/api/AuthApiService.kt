package com.campuscoders.posterminalapp.data.remote.api

import com.campuscoders.posterminalapp.data.remote.dto.CompanyApiResponse
import com.campuscoders.posterminalapp.data.remote.dto.LoginResponse
import com.campuscoders.posterminalapp.data.remote.dto.TerminalUsersResponse
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

    @GET("terminal-users")
    suspend fun getTerminalUsers(
        @Query("access_token") accessToken: String,
        @Query("terminal_id") terminalId: String
    ): Response<TerminalUsersResponse>

    @GET("company")
    suspend fun getCompanyData(
        @Query("access_token") accessToken: String,
        @Query("terminal_id") terminalId: String
    ): Response<CompanyApiResponse>


}
