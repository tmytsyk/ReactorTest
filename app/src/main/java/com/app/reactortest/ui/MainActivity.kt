package com.app.reactortest.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.app.reactortest.R
import com.app.reactortest.model.GiphyObject


typealias IPresenter = MainContract.Presenter
typealias IView = MainContract.View

class MainActivity : AppCompatActivity(), IView {
    private lateinit var etSearch: EditText
    private lateinit var rvGifList: RecyclerView
    private lateinit var tvNoGifs: TextView
    private lateinit var progressBar: ProgressBar

    private lateinit var presenter: IPresenter
    private lateinit var listAdapter: SearchResultAdapter
    private var scrollListener: EndlessRecyclerViewScrollListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        prepareView()
        preparePresenter()
    }

    override fun onStop() {
        super.onStop()
        presenter.stop()
    }

    override fun displayGifs(list: List<GiphyObject>) =
        runOnUiThread {
            listAdapter.updateData(list, false)

            tvNoGifs.visibility = INVISIBLE
            rvGifList.visibility = VISIBLE
        }

    override fun displayGifsPagination(list: List<GiphyObject>) =
        runOnUiThread {
            listAdapter.updateData(list, true)
        }

    override fun displayNoGifs() =
        runOnUiThread {
            tvNoGifs.visibility = VISIBLE
            rvGifList.visibility = INVISIBLE
        }

    override fun displayMessage(message: String) =
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }


    override fun displayError(message: String) {
        displayMessage(message)
    }

    override fun showLoadingProgress() = runOnUiThread {
        progressBar.visibility = VISIBLE
    }

    override fun hideLoadingProgress() = runOnUiThread {
        progressBar.visibility = INVISIBLE
    }

    private fun prepareView() {
        etSearch = findViewById(R.id.activity_main_et_search)
        rvGifList = findViewById(R.id.activity_main_rv_giflist)
        tvNoGifs = findViewById(R.id.activity_main_tv_no_gifs)
        progressBar = findViewById(R.id.progressBar)

        listAdapter = SearchResultAdapter(listOf())
        val layoutManager = GridLayoutManager(this, 2)

        scrollListener = object : EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                presenter.loadMore()
            }
        }

        rvGifList.layoutManager = layoutManager
        rvGifList.adapter = listAdapter
        rvGifList.addOnScrollListener(scrollListener as EndlessRecyclerViewScrollListener)
        etSearch.addTextChangedListener(textWatcher)
    }

    private fun preparePresenter() {
        presenter = MainPresenter()
        presenter.create(view = this@MainActivity)
    }


    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            presenter.searchGif(s.toString())
            scrollListener?.resetState()
        }
    }

}
