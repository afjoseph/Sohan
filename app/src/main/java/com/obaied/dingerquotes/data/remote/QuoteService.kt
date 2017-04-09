package com.obaied.dingerquotes.data.remote

import com.google.gson.Gson
import com.obaied.dingerquotes.BuildConfig
import com.obaied.dingerquotes.data.model.Quote
import com.obaied.dingerquotes.util.d
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import timber.log.Timber

/**
 * Created by ab on 02/04/2017.
 */

interface QuoteService {
    object consts {
        const val ENDPOINT = "http://api.forismatic.com/api/1.0/"
    }

    object ApiSettings {
        const val RANDOM_QUOTE = "?method=getQuote&format=json&lang=en"
    }

    @GET(ApiSettings.RANDOM_QUOTE)
    fun getQuote(): Single<Quote>

    object Creator {
        fun newQuoteService(): QuoteService {
            d { "newQuoteService(): " }

            val retrofitBuilder = Retrofit.Builder()
                    .baseUrl(QuoteService.consts.ENDPOINT)
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

