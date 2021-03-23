package web.id.fajar.moviestar.ui.favorite.tvshow

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint
import web.id.fajar.moviestar.data.database.model.TvShowEntity
import web.id.fajar.moviestar.data.mapper.TvShowMapper
import web.id.fajar.moviestar.databinding.FragmentFavoriteTvShowBinding
import web.id.fajar.moviestar.ui.tvshow.detail.DetailTvShowActivity
import web.id.fajar.moviestar.utils.Utils.sheetBehavior

@AndroidEntryPoint
class FavoriteTvShowFragment : Fragment() {

    private val binding : FragmentFavoriteTvShowBinding by lazy {
        FragmentFavoriteTvShowBinding.inflate(layoutInflater)
    }

    private val adapter : FavoriteTvShowAdapter by lazy {
        FavoriteTvShowAdapter {item -> detailTvShow(item)}
    }

    private val viewModel : FavoriteTvShowViewModel by viewModels()

    private lateinit var behavior: BottomSheetBehavior<*>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBottomsheet()
        setupView()
        setupViewModel()
    }

    private fun setupView() {
        with(binding){
            rvTvShow.also {
                it.adapter = adapter
                it.layoutManager = GridLayoutManager(requireContext(), 3)
            }
        }
    }

    private fun setupViewModel() {
        viewModel.data.observe(viewLifecycleOwner, Observer(adapter::submitList))
        viewModel.getFavoriteTvShow()
        viewModel.setupSearch(binding.etSearch)
    }

    private fun setupBottomsheet(){
        behavior = binding.bottomSheet.sheetBehavior()
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        val height = displayMetrics.heightPixels
        behavior.peekHeight = height/2
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun detailTvShow(item: TvShowEntity) {
        val dataMovie = TvShowMapper.mapEntityToResponse(item)
        startActivity(Intent(requireContext(), DetailTvShowActivity::class.java).also {
            it.putExtra("data", dataMovie)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

}