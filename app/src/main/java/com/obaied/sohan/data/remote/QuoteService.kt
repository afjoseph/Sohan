package com.obaied.sohan.data.remote

import com.google.gson.Gson
import com.obaied.sohan.data.model.Quote
import com.obaied.sohan.util.d
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by ab on 02/04/2017.
 */

interface QuoteService {
    companion object {
        const val ENDPOINT = "http://api.forismatic.com/api/1.0/"
    }

    object ApiSettings {
        const val QUOTE = "?method=getQuote&format=json&lang=en"
    }

    @GET(ApiSettings.QUOTE)
    fun getQuote(@Query("key") seed: String): Single<Quote>

    object Builder {
        fun newService(): QuoteService {
            d { "newService(): " }

            val retrofitBuilder = Retrofit.Builder()
                    .baseUrl(QuoteService.ENDPOINT)
                    .addConverterFactory(GsonConverterFactory.create(Gson()))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())

            //If we're on debug, attach an interceptor
//            if (BuildConfig.DEBUG) {
//                val interceptor = HttpLoggingInterceptor();
//                interceptor.level = HttpLoggingInterceptor.Level.BODY;
//                val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
//
//                retrofitBuilder?.client(client)
//            }

            return retrofitBuilder
                    .build()
                    .create(QuoteService::class.java)
        }
    }


}

