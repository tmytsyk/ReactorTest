package com.app.reactortest.ui

import com.app.reactortest.model.PaginationObject
import com.app.reactortest.networking.GiphyApiClient
import com.app.reactortest.networking.SearchResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

typealias ResponseFunction = (SearchResponse?) -> Unit


class MainPresenter : MainContract.Presenter {
    private companion object {
        const val UNKNOWN_ERROR = "Unknown Error"
        const val DEFAULT_DELAY_MILLIS = 1500L
        const val INITIAL_OFFSET = 0
    }

    private var view: MainContract.View? = null
    private var subscription: CompositeDisposable? = null
    private var paginationInfo: PaginationObject? = null
    private lateinit var lastQuery: String


    override fun create(view: MainContract.View) {
        this.view = view
        subscription = CompositeDisposable()
    }

    override fun searchGif(param: String) {
        subscription?.clear()
        subscription?.add(requestDisposable(param, INITIAL_OFFSET, searchResponse))
        lastQuery = param
    }

    override fun loadMore() {
        paginationInfo?.let {
            subscription?.add(requestDisposable(lastQuery, it.offset + it.count, paginationResponse))
        }
    }

    override fun stop() {
        subscription?.clear()
    }

    private val onError: (Throwable) -> Unit = {
        it.printStackTrace()
        view?.displayError(it.message ?: UNKNOWN_ERROR)
    }

    private val searchResponse: ResponseFunction =
        { response ->
            if (response?.data == null || response.data.isEmpty()) {
                view?.displayNoGifs()
            } else {
                view?.displayGifs(response.data)
                paginationInfo = response.pagination
            }
        }

    private val paginationResponse: ResponseFunction =
        { response ->
            response?.let {
                if (!it.data.isEmpty()) {
                    view?.displayGifsPagination(it.data)
                    paginationInfo = it.pagination
                }
            }
        }

    private val requestDisposable: (query: String, offset: Int, response: ResponseFunction) -> Disposable =
        { query, offset, function ->
            GiphyApiClient.apiAppClient.searchGifs(query = query, offset = offset)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { view?.showLoadingProgress() }
                .doOnComplete { view?.hideLoadingProgress() }
                .doOnError { view?.hideLoadingProgress() }
                .debounce(DEFAULT_DELAY_MILLIS, TimeUnit.MILLISECONDS)
                .subscribe(function, onError)
        }

}
