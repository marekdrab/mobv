package com.example.zadanie.helpers

import android.content.Context
import com.example.zadanie.api.ApiService
import com.example.zadanie.api.model.RefreshTokenRequest
import com.example.zadanie.data.PreferenceData
import com.example.zadanie.model.User
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import java.net.Authenticator

class TokenAuthenticator(val context: Context) : okhttp3.Authenticator {
    override fun authenticate(route: Route?, response: okhttp3.Response): Request? {

        if (response.request.url.toUrl().path.contains("/user/create.php", true)
            || response.request.url.toUrl().path.contains("/user/login.php", true)
            || response.request.url.toUrl().path.contains("/user/refresh.php", true)
        ) {
            //here we do not need a authorization token
        } else {
            //if the authorization token was required, but it was rejected from REST API, it is probably outdated
            if (response.code == 401) {
                val userItem = PreferenceData.getInstance().getUser(context)
                userItem?.let { user ->
                    val tokenResponse = ApiService.create(context).refreshTokenBlocking(
                        RefreshTokenRequest(user.refresh)
                    ).execute()

                    if (tokenResponse.isSuccessful) {
                        tokenResponse.body()?.let {
                            val new_user = User(
                                user.username,
                                user.email,
                                user.id,
                                it.access,
                                it.refresh,
                                user.photo
                            )
                            PreferenceData.getInstance().putUser(context, new_user)
                            return response.request.newBuilder()
                                .header("Authorization", "Bearer ${new_user.access}")
                                .build()
                        }
                    }
                }
                //if there was no success of refresh token we logout user and clean any data
                PreferenceData.getInstance().clearData(context)
                return null
            }
        }
        return null
    }
}