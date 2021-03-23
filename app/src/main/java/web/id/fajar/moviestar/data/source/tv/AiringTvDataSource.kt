package web.id.fajar.moviestar.data.source.tv

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.reactivex.disposables.CompositeDisposable
import web.id.fajar.moviestar.data.model.tvshow.DataTvShow
import web.id.fajar.moviestar.data.network.ApiService
import web.id.fajar.moviestar.state.TvShowState
import web.id.fajar.moviestar.utils.Constant.AIRING_TODAY
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AiringTvDataSource @Inject constructor(
    private val apiService: ApiService
) : PageKeyedDataSource<Int, DataTvShow>() {

    lateinit var liveData: MutableLiveData<TvShowState>
    
    private val disposable by lazy {
        CompositeDisposable()
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, DataTvShow>
    ) {
        apiService.getAllTvShow(AIRING_TODAY, 1)
            .map<TvShowState>{
                callback.onResult(it.data.toMutableList(), 1, 2)
                TvShowState.Result(it)
            }
            .onErrorReturn(TvShowState::Error)
            .toFlowable()
            .startWith(TvShowState.Loading)
            .subscribe(liveData::postValue)
            .let { return@let disposable::add }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, DataTvShow>) {
        apiService.getAllTvShow(AIRING_TODAY, params.key)
            .map<TvShowState>{
                callback.onResult(it.data.toMutableList(), params.key + 1)
                TvShowState.Result(it)
            }
            .onErrorReturn(TvShowState::Error)
            .toFlowable()
            .subscribe(liveData::postValue)
            .let { return@let disposable::add }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, DataTvShow>) {

    }
}