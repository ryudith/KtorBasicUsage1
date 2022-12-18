package com.ryudith.ktorbasicusage1.service

import android.content.SharedPreferences
import io.ktor.client.plugins.cookies.*
import io.ktor.http.*
import io.ktor.util.date.*

class SharedPreferencesCookieStorage (
    val storagePrefixName: String,
    val sharedPreferences: SharedPreferences
): CookiesStorage
{
    private val cookieKeySuffix = "|cookieKeys"

    override suspend fun addCookie(requestUrl: Url, cookie: Cookie)
    {
        with(sharedPreferences.edit()) {
            val cookieKeyPrefix = "${storagePrefixName}_${requestUrl.host}"
            val cookieKeyListName = "${cookieKeyPrefix}${cookieKeySuffix}"
            val cookieKey = "${cookieKeyPrefix}_${cookie.name}"

            val cookieKeyList = HashSet<String>(sharedPreferences.getStringSet(cookieKeyListName, HashSet<String>())!!)
            if (cookieKeyList.contains(cookie.name))
            {
                val oldCookie = CookieSerializable.deserializeCookie(sharedPreferences.getString(cookieKey, null))
                if (oldCookie != null && cookie.expires != null)
                {
                    if (oldCookie.expires!!.timestamp < cookie.expires!!.timestamp)
                    {
                        putString(cookieKey, CookieSerializable.serializeCookie(cookie))
                    }
                    else
                    {
                        cookieKeyList.remove(cookie.name)
                        putStringSet(cookieKeyListName, cookieKeyList)
                        remove(cookieKey)
                    }
                }
            }
            else
            {
                cookieKeyList.add(cookie.name)
                putStringSet(cookieKeyListName, cookieKeyList)
                putString(cookieKey, CookieSerializable.serializeCookie(cookie))
            }

            apply()
        }
    }

    override fun close() {
    }

    override suspend fun get(requestUrl: Url): List<Cookie> {
        val cookies = mutableListOf<Cookie>()

        val cookieKeyPrefix = "${storagePrefixName}_${requestUrl.host}"
        val cookieKeyListName = "${cookieKeyPrefix}${cookieKeySuffix}"

        val cookieKeyList = sharedPreferences.getStringSet(cookieKeyListName, null)
        if (cookieKeyList == null) return cookies

        val now = GMTDate()
        for (key in cookieKeyList)
        {
            val cookieKey = "${cookieKeyPrefix}_${key}"
            val oldCookie = CookieSerializable.deserializeCookie(sharedPreferences.getString(cookieKey, null))
            if (oldCookie != null)
            {
                if (oldCookie.expires != null && oldCookie.expires!!.timestamp > now.timestamp)
                {
                    cookies.add(oldCookie)
                }
            }
        }

        return cookies
    }
}










