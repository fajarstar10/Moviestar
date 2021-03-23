package web.id.fajar.moviestar.ui.movie.detail

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import web.id.fajar.moviestar.BuildConfig.imageUrl
import web.id.fajar.moviestar.R
import web.id.fajar.moviestar.data.mapper.MovieMapper
import web.id.fajar.moviestar.data.model.detailmovie.ResponseDetailMovie
import web.id.fajar.moviestar.data.model.movie.DataMovie
import web.id.fajar.moviestar.data.model.videos.DataVideo
import web.id.fajar.moviestar.databinding.ActivityDetailMovieBinding
import web.id.fajar.moviestar.databinding.BottomSheetBinding
import web.id.fajar.moviestar.state.DetailMovieState
import web.id.fajar.moviestar.state.VideoState
import web.id.fajar.moviestar.ui.movie.detail.adapter.ProductionCompanyAdapter
import web.id.fajar.moviestar.ui.movie.detail.adapter.VideoAdapter
import web.id.fajar.moviestar.utils.Constant.MOVIE
import web.id.fajar.moviestar.utils.Utils.dateFormat
import web.id.fajar.moviestar.utils.Utils.delay
import java.lang.Exception


@AndroidEntryPoint
class DetailMovieActivity : AppCompatActivity() {

    private val binding : ActivityDetailMovieBinding by lazy {
        ActivityDetailMovieBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailMovieViewModel by viewModels()

    private val data : DataMovie? by lazy {
        intent.getParcelableExtra("data")
    }

    private val adapterVideo : VideoAdapter by lazy {
        VideoAdapter { item -> playVideo(item)}
    }

    private val adapterProduction : ProductionCompanyAdapter by lazy {
        ProductionCompanyAdapter()
    }

    private val dataLocal by lazy {
        MovieMapper.mapResponseToEntity(data!!)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        delay()
        setupStatusBar()
        setupView()
        setupViewModel()
        setupListener()
    }

    private fun setupViewModel() {
        viewModel.state.observe(this, {
            when (it) {
                is DetailMovieState.Loading -> getLoadingDetailMovie(true)
                is DetailMovieState.Result -> successGetDetailMovie(it.data)
                is DetailMovieState.Error -> showError()
            }
        })

        viewModel.stateVideo.observe(this, {
            when (it) {
                is VideoState.Loading -> getLoadingDetailMovie(true)
                is VideoState.Result -> successGetDataVideos(it.data.results)
                is VideoState.Error -> showError()
            }
        })

        viewModel.stateFavorite.observe(this, {
            when (it) {
                true -> setDrawableIsFavorite()
                false -> setDrawableNotFavorite()
            }
        })

        viewModel.checkFavorite(dataLocal)
        viewModel.getDetailMovie(data!!.id)
        viewModel.getVideos(MOVIE, data!!.id)
    }

    private fun setupView() {
        with(binding) {
            rvVideo.also {
                it.adapter = adapterVideo
                it.layoutManager = LinearLayoutManager(
                    this@DetailMovieActivity,
                    LinearLayoutManager.HORIZONTAL, false
                )
                it.setHasFixedSize(true)
            }

            rvProduction.also {
                it.adapter = adapterProduction
                it.layoutManager = LinearLayoutManager(
                    this@DetailMovieActivity,
                    LinearLayoutManager.HORIZONTAL, false
                )
                it.setHasFixedSize(true)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun successGetDetailMovie(response: ResponseDetailMovie) {
        getLoadingDetailMovie(false)
        with(binding) {
            tvTitle.text = response.title
            tvRelease.text = dateFormat(
                response.release_date,
                "yyyy-mm-dd",
                "dd MMMM yyyy"
            )
            tvPopularity.text = response.popularity.toString() + getString(R.string.title_viewers)
            tvRating.text = response.vote_average.toString()
            tvDescription.text = response.overview

            Glide.with(this@DetailMovieActivity)
                    .load(imageUrl + response.poster_path)
                    .into(imgPoster)

            Glide.with(this@DetailMovieActivity)
                    .load(imageUrl + response.backdrop_path)
                    .into(imgBackground)

            btnFavorite.setOnClickListener {
                viewModel.addToFavorite(dataLocal)
            }

            if(response.genres.isNotEmpty()){
                tvGenres.text = response.genres[0].name
            } else {
                tvGenres.text = "-"
            }
            adapterProduction.setData(response.production_companies)
        }
    }

    private fun successGetDataVideos(response: List<DataVideo>) {
        getLoadingDetailMovie(false)
        adapterVideo.setData(response)
        try {
            binding.btnTrailer.setOnClickListener {
                val intent = Intent(
                    Intent.ACTION_VIEW, Uri.parse("vnd.youtube://${response[0].key}")
                )
                startActivity(intent)
            }
        } catch (e : Exception) {
            showError()
        }
    }

    private fun getLoadingDetailMovie(loading: Boolean) {
        with(binding) {
            if (loading) {
                pgLoading.visibility = View.VISIBLE
            } else {
                pgLoading.visibility = View.GONE
            }
        }
    }

    private fun showError() {
        val binding = BottomSheetBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(this)
        dialog.setContentView(binding.root)
        with(binding) {
            btnOk.setOnClickListener {
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun setDrawableIsFavorite() {
        binding.btnFavorite.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_baseline_favorite_24
            )
        )
    }

    private fun setDrawableNotFavorite() {
        binding.btnFavorite.setImageDrawable(
            ContextCompat.getDrawable(
                this,
                R.drawable.ic_baseline_favorite_border_24
            )
        )
    }

    private fun setupListener() {
        binding.imgBack.setOnClickListener {
            finish()
        }
    }

    private fun playVideo(item: DataVideo) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://${item.key}"))
        startActivity(intent)
    }

    private fun setupStatusBar() {
        with(window) {
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }
    }
}