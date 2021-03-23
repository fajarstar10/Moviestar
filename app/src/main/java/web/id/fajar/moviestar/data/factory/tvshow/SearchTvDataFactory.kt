package web.id.fajar.moviestar.data.factory.tvshow

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import web.id.fajar.moviestar.data.model.tvshow.DataTvShow
import web.id.fajar.moviestar.data.source.tv.SearchTvDataSource
import web.id.fajar.moviestar.state.TvShowState
import javax.inject.Inject

class SearchTvDataFactory @Inject constructor(
    private val tvSearchDataSource: SearchTvDataSource
) : DataSource.Factory<Int, DataTvShow>(){

    lateinit var keyword: String
    lateinit var liveData: MutableLiveData<TvShowState>

    override fun create(): DataSource<Int, DataTvShow> {
        return tvSearchDataSource.also {
            it.keyword = keyword
            it.liveData = liveData
        }
    }
}