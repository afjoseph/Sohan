package com.obaied.dingerquotes.data.remote

import com.google.gson.Gson
import com.obaied.dingerquotes.data.model.RandomImage
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

/**
 * Created by ab on 10.08.17.
 */
interface RandomImageService {
    companion object {
        const val ENDPOINT = "http://www.splashbase.co/api/v1/"
    }

    object ApiSettings {
        const val RANDOM_IMAGE = "images/random"
    }

    @GET(ApiSettings.RANDOM_IMAGE)
    fun getRandomImage(): Single<RandomImage>

    object Builder {
        fun newService(): RandomImageService {
            val retrofitBuilder = Retrofit.Builder()
                    .baseUrl(RandomImageService.ENDPOINT)
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
                    .create(RandomImageService::class.java)
        }
    }


}
