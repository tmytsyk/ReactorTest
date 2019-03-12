package com.app.reactortest.ui

import com.app.reactortest.model.GiphyObject


interface MainContract {
    interface View {
        fun displayGifs(list: List<GiphyObject>)
        fun displayNoGifs()
        fun displayGifsPagination(list: List<GiphyObject>)
        fun displayMessage(message: String)
        fun displayError(message: String)
        fun showLoadingProgress()
        fun hideLoadingProgress()
    }

    interface Presenter {
        fun create(view: View)
        fun searchGif(param: String)
        fun loadMore()
        fun stop()
    }
}
