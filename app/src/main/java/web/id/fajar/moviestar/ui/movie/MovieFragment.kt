package web.id.fajar.moviestar.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import web.id.fajar.moviestar.data.model.movie.DataMovie
import web.id.fajar.moviestar.databinding.BottomSheetBinding
import web.id.fajar.moviestar.databinding.FragmentMovieBinding
import web.id.fajar.moviestar.state.MovieState
import web.id.fajar.moviestar.ui.favorite.FavoriteActivity
import web.id.fajar.moviestar.ui.movie.adapter.HorizontalMovieAdapter
import web.id.fajar.moviestar.ui.movie.adapter.VerticalMovieAdapter
import web.id.fajar.moviestar.ui.movie.detail.DetailMovieActivity
import web.id.fajar.moviestar.ui.movie.popular.PopularMovieActivity
import web.id.fajar.moviestar.ui.movie.toprated.TopRatedMovieActivity
import web.id.fajar.moviestar.ui.movie.upcoming.UpcomingMovieActivity
import web.id.fajar.moviestar.ui.search.movie.SearchMovieActivity
import web.id.fajar.moviestar.utils.Utils.delay

@AndroidEntryPoint
class MovieFragment : Fragment() {

    private val viewModel: MovieViewModel by viewModels()

    private val binding : FragmentMovieBinding by lazy {
        FragmentMovieBinding.inflate(layoutInflater)
    }

    private val upcomingAdapter: HorizontalMovieAdapter by lazy {
        HorizontalMovieAdapter{ item -> detailMovie(item)}
    }

    private val topRatedAdapter: HorizontalMovieAdapter by lazy {
        HorizontalMovieAdapter{ item -> detailMovie(item)}
    }

    private val popularAdapter: VerticalMovieAdapter by lazy {
        VerticalMovieAdapter{ item -> detailMovie(item)}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        delay()
        setupView()
        setupViewModel()
        setupData()
        setupListener()
    }

    private fun setupData() {
        viewModel.getUpcomingMovie()
        viewModel.getPopularMovie()
        viewModel.getTopRatedMovie()
    }

    private fun setupViewModel() {
        viewModel.stateUpcoming.observe(viewLifecycleOwner, {
            when(it){
                is MovieState.Loading   -> getLoadingUpcoming(true)
                is MovieState.Result    -> successGetDataUpComing(it.data.data)
                is MovieState.Error     -> showError()
            }
        })

        viewModel.stateTopRated.observe(viewLifecycleOwner, {
            when(it){
                is MovieState.Loading   -> getLoadingPopular(true)
                is MovieState.Result    -> successGetDataTopRated(it.data.data)
                is MovieState.Error     -> showError()
            }
        })

        viewModel.statePopular.observe(viewLifecycleOwner, {
            when(it){
                is MovieState.Loading   -> getLoadingPopular(true)
                is MovieState.Result    -> successGetDataPopular(it.data.data)
                is MovieState.Error     -> showError()
            }
        })
    }

    private fun setupView() {
        with(binding) {
            rvUpcoming.also {
                it.adapter = upcomingAdapter
                it.layoutManager = LinearLayoutManager(
                    requireContext(), LinearLayoutManager.HORIZONTAL ,false)
                it.setHasFixedSize(true)
            }

            rvTopRated.also {
                it.adapter = topRatedAdapter
                it.layoutManager = LinearLayoutManager(
                    requireContext(), LinearLayoutManager.HORIZONTAL ,false)
                it.setHasFixedSize(true)
            }

            rvPopular.also {
                it.adapter = popularAdapter
                it.layoutManager = GridLayoutManager(requireContext(), 1)
                it.setHasFixedSize(true)
            }
        }
    }

    private fun successGetDataUpComing(data : List<DataMovie>) {
        getLoadingUpcoming(false)
        upcomingAdapter.setData(data)
    }

    private fun successGetDataTopRated(data : List<DataMovie>) {
        getLoadingTopRated(false)
        topRatedAdapter.setData(data)
    }

    private fun successGetDataPopular(data : List<DataMovie>) {
        getLoadingPopular(false)
        popularAdapter.setData(data)
    }

    private fun getLoadingUpcoming(loading: Boolean) {
        with(binding) {
            if (loading) {
                rvUpcoming.visibility = View.INVISIBLE
                shUpcoming.visibility = View.VISIBLE
            }else {
                rvUpcoming.visibility = View.VISIBLE
                shUpcoming.visibility = View.INVISIBLE
            }
        }
    }

    private fun getLoadingTopRated(loading: Boolean) {
        with(binding) {
            if (loading) {
                rvTopRated.visibility = View.INVISIBLE
                shTopRated.visibility = View.VISIBLE
            }else {
                rvTopRated.visibility = View.VISIBLE
                shTopRated.visibility = View.INVISIBLE
            }
        }
    }

    private fun getLoadingPopular(loading: Boolean) {
        with(binding) {
            if (loading) {
                rvPopular.visibility = View.INVISIBLE
                shPopular.visibility = View.VISIBLE
            }else {
                rvPopular.visibility = View.VISIBLE
                shPopular.visibility = View.INVISIBLE
            }
        }
    }

    private fun showError() {
        val binding = BottomSheetBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(binding.root)
        with(binding) {
            btnOk.setOnClickListener {
                dialog.dismiss()
            }
        }

        dialog.show()
    }

    private fun detailMovie(item: DataMovie) {
        startActivity(Intent(requireContext(), DetailMovieActivity::class.java).also {
            it.putExtra("data", item)
        })
    }

    private fun setupListener() {
        with(binding) {
            tvSeeUpcoming.setOnClickListener {
                startActivity(Intent(requireContext(), UpcomingMovieActivity::class.java))
            }

            tvSeeTopRated.setOnClickListener {
                startActivity(Intent(requireContext(), TopRatedMovieActivity::class.java))
            }

            tvSeePopular.setOnClickListener {
                startActivity(Intent(requireContext(), PopularMovieActivity::class.java))
            }

            search.setOnClickListener {
                startActivity(Intent(requireContext(), SearchMovieActivity::class.java))
            }

            imgFavorite.setOnClickListener {
                startActivity(Intent(requireContext(), FavoriteActivity::class.java))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}