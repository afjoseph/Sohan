package com.obaied.sohan.ui.start

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.obaied.sohan.R
import com.obaied.sohan.data.model.Quote
import kotlinx.android.synthetic.main.item_quote_card.view.*
import javax.inject.Inject

/**
 * Created by ab on 12.04.17.
 */

class QuotesAdapter
@Inject constructor()
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var quotesList: MutableList<Quote> = mutableListOf()
    var clickListener: ClickListener? = null
    val savedImageTags = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_quote_card, parent, false)

        return QuotesViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return quotesList.size
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        holder as QuotesViewHolder
        val quote = quotesList[position]
        holder.bindQuote(quote)
    }

    inner class QuotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindQuote(quote: Quote) {
            itemView.item_quote_textview_quote.text = quote.text
            itemView.setOnClickListener { clickListener!!.onClick(quote) }

//            Colorfilter value is saved in memory, not synced with DB
//            val bgColor: Int
//            if (quote.colorFilter != null) {
//                bgColor = quote.colorFilter as Int
//            } else {
//                bgColor = Colour.getRandomNiceColor()
//            }

//            itemView.item_quote_imageview_shade.setColorFilter(bgColor)
//            itemView.item_quote_textview_quote.setTextColor(Colour.blackOrWhiteContrastingColor(bgColor))
//            quote.colorFilter = bgColor
//
//            val (width, height) = DisplayMetricsUtil.getScreenMetrics()

//            Glide.with(itemView.context)
//                    .load("https://unsplash.it/$width/$height/?image=${quote.imageTag}")
//                    .centerCrop()
//                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                    .crossFade()
//                    .listener(object : RequestListener<String, GlideDrawable> {
//                        override fun onResourceReady(resource: GlideDrawable?, model: String?, target: Target<GlideDrawable>?, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
//                            itemView.item_quote_imageview_shade.setColorFilter(Colour.BLACK)
//                            itemView.item_quote_textview_quote.setTextColor(Colour.WHITE)
//                            savedImageTags.add(quote.imageTag!!)
//                            return false
//                        }
//
//                        override fun onException(e: Exception?, model: String?, target: Target<GlideDrawable>?, isFirstResource: Boolean): Boolean {
//                            if (savedImageTags.size <= 1) {
//                                return false
//                            }
//
//                            val randomTag = savedImageTags[Random().nextInt(savedImageTags.size)]
//                            Glide.with(itemView.context)
//                                    .load("https://unsplash.it/$width/$height/?image=$randomTag")
//                                    .into(itemView.item_quote_imageview_cover)
//
//                            return false
//                        }
//
//                    })
//                    .into(itemView.item_quote_imageview_cover)

        }
    }

    interface ClickListener {
        fun onClick(quote: Quote)
    }
}

