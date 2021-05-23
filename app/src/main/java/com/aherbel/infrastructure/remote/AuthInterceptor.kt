package com.aherbel.infrastructure.remote

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    
    companion object {
        
        private const val API_KEY = "819e9f091e420dba760b324825b34b35"
    }
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        
        val url = chain.request().url
        val urlBuilder = url.newBuilder()
        if (url.queryParameter("api_key") == null) {
            urlBuilder.addQueryParameter("api_key", API_KEY)
        }
        
        return chain.proceed(
            requestBuilder
                .url(urlBuilder.build())
                .build()
        )
    }
    
}