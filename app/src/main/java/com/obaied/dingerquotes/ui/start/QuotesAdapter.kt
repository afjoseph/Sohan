package com.obaied.dingerquotes.ui.start

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.obaied.colours.Colour
import com.obaied.dingerquotes.R
import com.obaied.dingerquotes.data.model.Quote
import kotlinx.android.synthetic.main.item_quote_card.view.*
import javax.inject.Inject

/**
 * Created by ab on 12.04.17.
 */

class QuotesAdapter
@Inject constructor()
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val VIEW_TYPE_PROGRESS: Int = 333
    }

    var mQuotesList: MutableList<Quote> = mutableListOf()
    var mClickListener: ClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
//        if (viewType == VIEW_TYPE_PROGRESS) {
//            val itemView = LayoutInflater.from(parent?.context)
//                    .inflate(R.layout.layout_progress_bar, parent, false)
//            return ProgressViewHolder(itemView)
//        } else {
        val itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_quote_card, parent, false)

        return QuotesViewHolder(itemView)
//        }
    }

    override fun getItemCount(): Int {
        return mQuotesList.size
//        return if (mIsAppending) mQuotesList.size + 1 else mQuotesList.size
    }

    override fun getItemViewType(position: Int): Int {
//        return if (mIsAppending && position >= itemCount) VIEW_TYPE_PROGRESS else
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
//        if (holder is ProgressViewHolder) {
//            holder.showProgress()
//        } else if (holder is QuotesViewHolder) {
        holder as QuotesViewHolder
        val quote = mQuotesList[position]
        holder.bindQuote(quote)
//        }
//        else {
//           val progressHolder = holder as ProgressViewHolder
//            progressHolder?.showProgress()
//        }
    }

    inner class QuotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindQuote(quote: Quote) {
            itemView.item_quote_textview_quote.text = quote.text
            itemView.setOnClickListener { mClickListener!!.onClick(quote) }

            //Colorfilter value is saved in memory, not synced with DB
            val bgColor: Int
            if (quote.colorFilter != null) {
                bgColor = quote.colorFilter as Int
            } else {
                bgColor = Colour.getRandomNiceColor()
            }

            itemView.item_quote_imageview_shade.setColorFilter(bgColor)
            itemView.item_quote_textview_quote.setTextColor(Colour.blackOrWhiteContrastingColor(bgColor))
            quote.colorFilter = bgColor


//            Glide.with(itemView.context)
//                    .load("https://unsplash.it/200/300/?random")
//                    .centerCrop()
//                    .diskCacheStrategy(DiskCacheStrategy.NONE)
//                    .skipMemoryCache(true)
//                    .placeholder(R.drawable.design_password_eye)
//                    .into(itemView.item_quote_imageview_shade)
        }
    }

    inner class ProgressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun showProgress() {

        }
    }

    interface ClickListener {
        fun onClick(quote: Quote)
    }
}

