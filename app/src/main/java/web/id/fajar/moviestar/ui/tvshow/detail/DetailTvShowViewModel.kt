package web.id.fajar.moviestar.ui.tvshow.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import web.id.fajar.moviestar.data.database.model.TvShowEntity
import web.id.fajar.moviestar.data.repository.Repository
import web.id.fajar.moviestar.state.DetailTvShowState

class DetailTvShowViewModel @ViewModelInject constructor(
        private val repository: Repository
): ViewModel() {

    val state : MutableLiveData<DetailTvShowState> by lazy {
        MutableLiveData<DetailTvShowState>()
    }

    val stateFavorite : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun getDetailTvShow(tvId: Int) {
        repository.getDetailTvShow(tvId, state)
    }

    fun addToFavorite(data : TvShowEntity){
        CoroutineScope(Dispatchers.IO).launch {
            val checkData = async { repository.checkDataTvShow(data) }
            if(checkData.await().isEmpty()){
                repository.addDataTvShow(data)
                withContext(Dispatchers.Main){
                    stateFavorite.postValue(true)
                }
            }else{
                repository.deleteDataTvShow(data)
                withContext(Dispatchers.Main){
                    stateFavorite.postValue(false)
                }
            }
        }
    }

    fun checkFavorite(data : TvShowEntity){
        CoroutineScope(Dispatchers.IO).launch {
            val checkData = async { repository.checkDataTvShow(data) }
            if(checkData.await().isNotEmpty()){
                withContext(Dispatchers.Main){
                    stateFavorite.postValue(true)
                }
            }else{
                withContext(Dispatchers.Main){
                    stateFavorite.postValue(false)
                }
            }
        }
    }
}