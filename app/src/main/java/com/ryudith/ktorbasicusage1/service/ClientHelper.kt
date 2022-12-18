package com.ryudith.ktorbasicusage1.service

import android.content.SharedPreferences
import android.util.Log
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.cookies.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*

class ClientHelper
{
    companion object
    {
        private var instance: ClientHelper? = null
        fun createInstance (
            storagePrefixName: String? = null,
            sharedPreferences: SharedPreferences? = null
        ): ClientHelper
        {
            if (instance == null)
            {
                instance = ClientHelper()
                instance!!.initialize(storagePrefixName, sharedPreferences)
            }
            return instance!!
        }
    }

    private var client: HttpClient? = null
    fun initialize (storagePrefixName: String?, sharedPreferences: SharedPreferences?)
    {
        client = HttpClient(OkHttp) {
            install(ContentNegotiation) {
                json()
            }

            install(HttpCookies) {
                if (storagePrefixName == null || sharedPreferences == null)
                {
                    storage = AcceptAllCookiesStorage()
                }
                else
                {
                    storage = SharedPreferencesCookieStorage(storagePrefixName, sharedPreferences)
                }
            }
        }
    }


    suspend fun sendGetRequest (): String?
    {
        try
        {
            val response = client!!.get {
                url("http://10.0.2.2/simple_php/method_get.php")
            }

            return response.body()
        }
        catch (e: Exception)
        {
            return null
        }
    }

    suspend fun getListMovie (page: Int = 1, limit: Int = 10): ListMovieEntity?
    {
        try
        {
            val response = client!!.get {
                url("http://10.0.2.2/simple_php/method_get.php")
                parameter("page", page)
                parameter("limit", limit)
            }

            Log.d("DEBUG_DATA", "debug response: ${response.body<String>()}")

            return response.body()
        }
        catch (e: Exception)
        {
            return null
        }
    }

    suspend fun sendCustomHeader (): String?
    {
        try
        {
            val response = client!!.get {
                url("http://10.0.2.2/simple_php/method_get.php")
                headers {
                    append("CustomHeader", "Hello PHP!")
                }
            }

            return response.body()
        }
        catch (e: Exception)
        {
            return null
        }
    }
}






