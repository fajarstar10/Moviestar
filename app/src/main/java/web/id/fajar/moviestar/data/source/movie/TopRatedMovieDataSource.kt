package web.id.fajar.moviestar.data.source.movie

import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import io.reactivex.disposables.CompositeDisposable
import web.id.fajar.moviestar.data.model.movie.DataMovie
import web.id.fajar.moviestar.data.network.ApiService
import web.id.fajar.moviestar.state.MovieState
import web.id.fajar.moviestar.utils.Constant.TOP_RATED
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TopRatedMovieDataSource @Inject constructor(
    private val apiService: ApiService
) : PageKeyedDataSource<Int, DataMovie>() {

    lateinit var liveData: MutableLiveData<MovieState>
    
    private val disposable by lazy {
        CompositeDisposable()
    }

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, DataMovie>
    ) {
        apiService.getAllMovie(TOP_RATED, 1)
            .map<MovieState>{
                callback.onResult(it.data.toMutableList(), 1, 2)
                MovieState.Result(it)
            }
            .onErrorReturn(MovieState::Error)
            .toFlowable()
            .startWith(MovieState.Loading)
            .subscribe(liveData::postValue)
            .let { return@let disposable::add }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, DataMovie>) {
        apiService.getAllMovie(TOP_RATED, params.key)
            .map<MovieState>{
                callback.onResult(it.data.toMutableList(), params.key + 1)
                MovieState.Result(it)
            }
            .onErrorReturn(MovieState::Error)
            .toFlowable()
            .subscribe(liveData::postValue)
            .let { return@let disposable::add }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, DataMovie>) {

    }
}