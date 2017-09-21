package com.obaied.dingerquotes.ui.start

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.crashlytics.android.Crashlytics
import com.obaied.colours.Colour
import com.obaied.dingerquotes.R
import com.obaied.dingerquotes.data.model.Quote
import com.obaied.dingerquotes.ui.base.BaseActivity
import com.obaied.dingerquotes.ui.custom.ColorScrollListener
import com.obaied.dingerquotes.ui.custom.InfiniteScrollListener
import com.obaied.dingerquotes.ui.quote.QuoteActivity
import com.obaied.dingerquotes.util.d
import com.obaied.dingerquotes.util.i
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_start.*
import javax.inject.Inject

class StartActivity : BaseActivity(), StartMvpView {
    companion object {
        val QUOTE_LIMIT_PER_PAGE = 30
        val KEY_SCROLL_STATE = "KEY_SCROLL_STATE"
    }

    @Inject lateinit var mPresenter: StartPresenter
    @Inject lateinit var mQuotesAdapter: QuotesAdapter

    var gradient: GradientDrawable? = null
    var scrollState: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        d { "startActivity: onCreate() " }

        super.onCreate(savedInstanceState)
        Fabric.with(this, Crashlytics())
        setContentView(R.layout.activity_start)

        activityComponent()?.inject(this)
        mPresenter.attachView(this)

        setupViews()

        mPresenter.subscribeToDbToFetchQuotes()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putParcelable(KEY_SCROLL_STATE, start_recyclerview.layoutManager.onSaveInstanceState())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        val parcelable = savedInstanceState?.getParcelable<Parcelable>(KEY_SCROLL_STATE)
        if (parcelable != null) {
            this.scrollState = parcelable
        }
    }

    private fun setupViews() {
        //Clicklistener for quotes adapter
        mQuotesAdapter.clickListener = object : QuotesAdapter.ClickListener {
            override fun onClick(quote: Quote) {
                startActivity(QuoteActivity.getStartIntent(this@StartActivity, quote))
            }
        }

        //Recyclerview
        val layoutManager: LinearLayoutManager = getLayoutManager() as LinearLayoutManager
        start_recyclerview.layoutManager = layoutManager
        start_recyclerview.setHasFixedSize(true)
        start_recyclerview.adapter = mQuotesAdapter
        start_recyclerview.addOnScrollListener(object : InfiniteScrollListener(QUOTE_LIMIT_PER_PAGE, 1, layoutManager) {
            override fun onScrolledToEnd(firstVisibleItemPosition: Int) {
                i { "hit the limit" }
                mPresenter.fetchQuotesFromApi(QUOTE_LIMIT_PER_PAGE)
            }
        })
        start_recyclerview.addOnScrollListener(ColorScrollListener(object : ColorScrollListener.ColorDyCallback {
            override fun onChangeColor(newStartColor: Int, newCenterColor: Int, newEndColor: Int) {
                gradient?.colors = intArrayOf(newStartColor, newCenterColor, newEndColor)
            }
        }))
        start_recyclerview.addItemDecoration(DividerItemDecoration(start_recyclerview.context,
                layoutManager.orientation))

        //Swipe to refresh
        start_swipetorefresh.setOnRefreshListener {
            d { "refreshing..." }
            mPresenter.fetchQuotesFromDb()
        }

        start_swipetorefresh.setColorSchemeColors(Colour.holoBlueBrightColor(), Colour.holoGreenLightColor(), Colour.holoOrangeLightColor(), Colour.holoRedLightColor())
        start_swipetorefresh.isEnabled = false

        //Gradient
        gradient = GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, intArrayOf(Colour.WHITE, Colour.WHITE, Colour.WHITE))
        start_layout_gradient.background = gradient
    }

    private fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(this)
    }

    override fun showEmpty() {
//        start_recyclerview.visibility = View.GONE

        mPresenter.fetchQuotesFromApi(QUOTE_LIMIT_PER_PAGE)
    }

    override fun showProgress() {
//        d { "showProgress(): " }
//        Toast.makeText(this, "Fetching...", Toast.LENGTH_SHORT).show()
    }

    override fun hideProgress() {
//        d { "hideProgress(): " }
//        Toast.makeText(this, "Done fetching...", Toast.LENGTH_SHORT).show()
    }

    override fun showQuotes(quotes: List<Quote>) {
        start_swipetorefresh.isRefreshing = false

        val mutableQuotes = quotes.toMutableList()
        mQuotesAdapter.quotesList.clear()
        mQuotesAdapter.quotesList.addAll(mutableQuotes)
        mQuotesAdapter.notifyDataSetChanged()
        start_recyclerview.visibility = View.VISIBLE

        if (scrollState != null) {
            start_recyclerview.layoutManager.onRestoreInstanceState(scrollState)
        }
    }

    override fun showError(error: String) {
        start_swipetorefresh.isRefreshing = false
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}
