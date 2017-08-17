package com.obaied.dingerquotes.ui.start

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.obaied.dingerquotes.R
import com.obaied.dingerquotes.data.model.Quote
import com.obaied.dingerquotes.ui.base.BaseActivity
import com.obaied.dingerquotes.ui.custom.InfiniteScrollListener
import com.obaied.dingerquotes.ui.quote.QuoteActivity
import com.obaied.dingerquotes.util.d
import com.obaied.dingerquotes.util.i
import kotlinx.android.synthetic.main.activity_start.*
import javax.inject.Inject


class StartActivity : BaseActivity(), StartMvpView {
    companion object {
        val QUOTE_LIMIT_PER_PAGE = 30
    }

    @Inject lateinit var mPresenter: StartPresenter
    @Inject lateinit var mQuotesAdapter: QuotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        d { "startActivity: onCreate() " }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        activityComponent()?.inject(this)
        mPresenter.attachView(this)

        setupViews()

        mPresenter.subscribeToDbToFetchQuotes()
    }

    private fun setupViews() {
        mQuotesAdapter.clickListener = object : QuotesAdapter.ClickListener {
            override fun onClick(quote: Quote) {
                startActivity(QuoteActivity.getStartIntent(this@StartActivity, quote))
            }
        }

        val layoutManager: LinearLayoutManager = getLayoutManager() as LinearLayoutManager
        start_recyclerview.layoutManager = layoutManager
        start_recyclerview.setHasFixedSize(true)
        start_recyclerview.adapter = mQuotesAdapter
//        start_recyclerview.addOnScrollListener(object : InfiniteScrollListener(QUOTE_LIMIT_PER_PAGE - ((QUOTE_LIMIT_PER_PAGE * 0.33).toInt()), 1, layoutManager) {
        start_recyclerview.addOnScrollListener(object : InfiniteScrollListener(QUOTE_LIMIT_PER_PAGE, 1, layoutManager) {
            override fun onScrolledToEnd(firstVisibleItemPosition: Int) {
                i { "hit the limit" }
                mPresenter.fetchQuotesFromApi(QUOTE_LIMIT_PER_PAGE)
            }
        })
        start_recyclerview.addItemDecoration(DividerItemDecoration(start_recyclerview.context,
                layoutManager.orientation))
    }

    private fun getLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(this)
    }

    override fun showEmpty() {
        d { ">>>>>>>> showEmpty(): " }
        start_recyclerview.visibility = View.GONE
        Toast.makeText(this, "No quotes to show...", Toast.LENGTH_SHORT).show()

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
        d { "showQuotes(): " }
        val mutableQuotes = quotes.toMutableList()
        mQuotesAdapter.quotesList.clear()
        mQuotesAdapter.quotesList.addAll(mutableQuotes)
        mQuotesAdapter.notifyDataSetChanged()
        start_recyclerview.visibility = View.VISIBLE
    }

    override fun showError(error: String) {
        d { "showError(): " }
        Toast.makeText(this, error, Toast.LENGTH_LONG).show()
    }
}
