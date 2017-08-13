package com.obaied.dingerquotes.ui.quote

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.obaied.colours.Colour
import com.obaied.dingerquotes.R
import com.obaied.dingerquotes.data.model.Quote
import com.obaied.dingerquotes.ui.base.BaseActivity
import com.obaied.dingerquotes.util.e
import kotlinx.android.synthetic.main.activity_quote.*
import javax.inject.Inject

class QuoteActivity : BaseActivity(), QuoteMvpView {
    @Inject lateinit var mPresenter: QuotePresenter
    private var quote: Quote? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quote)

        activityComponent()?.inject(this)
        mPresenter.attachView(this)

        quote = intent.getParcelableExtra<Quote>(EXTRA_QUOTE)
                ?: throw IllegalArgumentException("Quote activity requires a quote instance")

        val colorFilter = intent.getIntExtra(EXTRA_COLOR_FILTER, Colour.getRandomNiceColor())

        quote_fap.setOnClickListener {
            if (quote != null) shareQuote()
        }

        mPresenter.fetchRandomImage()

        fillQuote(quote!!, colorFilter)
    }

    //TODO: put both takeScreenshot() and shareQuote() inside Presenter
    private fun takeScreenshot(): Bitmap {
        val view = window.decorView.rootView
        val bitmap = Bitmap.createBitmap(view.width,
                view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun shareQuote() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse) {
                        quote_fap.visibility = View.GONE

                        val bitmapPath = MediaStore.Images.Media.insertImage(contentResolver,
                                takeScreenshot(), quote?.author, null)

                        quote_fap.visibility = View.VISIBLE

                        val bitmapUri = Uri.parse(bitmapPath)

                        val intent = Intent()
                        intent.action = Intent.ACTION_SEND
                        intent.type = "image/*"

                        intent.putExtra(Intent.EXTRA_STREAM, bitmapUri)

                        startActivity(intent)
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse) {/* ... */
                        e { "onPermissionDenied" }
                    }

                    override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {/* ... */
                        e { "onPermissionRationaleShouldBeShown" }
                    }
                }).check()
    }

    private fun fillQuote(quote: Quote, colorFilter: Int) {
//        quote_imageview_shade.setColorFilter(colorFilter)

        quote_textview_author.text = quote.author
        quote_textview_quote.text = quote.text
//        quote_textview_quote.setTextColor(Colour.blackOrWhiteContrastingColor(colorFilter))
//
//        val (width, height) = DisplayMetricsUtil.getScreenMetrics()
//        Glide.with(this)
//                .load("https://unsplash.it/g/600/400")
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .placeholder(R.drawable.abc_ab_share_pack_mtrl_alpha)
//                .into(quote_imageview_shade)
    }

    override fun showImage(imageUrl: String) {
        if (!isAttachToWindow) return

        Glide.with(this)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .centerCrop()
                .into(quote_imageview_cover)
    }

    //TODO: maybe do something here??
    override fun showError(error: String) {

    }

    companion object {
        val EXTRA_QUOTE = "com.obaied.dingerquotes.ui.quote.EXTRA_QUOTE"
        val EXTRA_COLOR_FILTER = "com.obaied.dingerquotes.ui.quote.EXTRA_COLOR_FILTER"

        fun getStartIntent(context: Context, quote: Quote): Intent {
            val intent = Intent(context, QuoteActivity::class.java)
            intent.putExtra(EXTRA_QUOTE, quote)
            intent.putExtra(EXTRA_COLOR_FILTER, quote.colorFilter)

            return intent
        }
    }
}
