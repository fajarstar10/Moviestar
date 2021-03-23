package web.id.fajar.moviestar.ui.movie.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import web.id.fajar.moviestar.BuildConfig.imageUrl
import web.id.fajar.moviestar.R
import web.id.fajar.moviestar.data.model.movie.DataMovie
import web.id.fajar.moviestar.databinding.AdapterMovieBinding
import web.id.fajar.moviestar.utils.Constant
import web.id.fajar.moviestar.utils.Utils

class VerticalMovieAdapter (
    private val showDetail: (DataMovie) -> Unit
) : RecyclerView.Adapter<VerticalMovieAdapter.ViewHolder>() {

    private var data = ArrayList<DataMovie>()

    fun setData(movieList: List<DataMovie>?) {
        if (movieList.isNullOrEmpty()) return
        data.clear()
        data.addAll(movieList)
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.view) {
            tvTitle.text = data[position].title
            tvRating.text = data[position].vote_average.toString()

            if(data[position].genreIds?.isNotEmpty() == true) {
                tvGenres.text = data[position].genreIds?.get(0)?.let { Constant.Genres.getValue(it) }
            } else {
                tvGenres.visibility = View.GONE
            }

            tvRelease.text = data[position].release_date?.let {
                Utils.dateFormat(
                    it,
                    "yyyy-mm-dd",
                    "yyyy"
                )
            }

            holder.itemView.also {
                Glide.with(it.context)
                        .load( imageUrl + data[position].poster_path)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imgPoster)
                tvPopularity.text = data[position].popularity.toString() +
                        it.context.getString(R.string.title_viewers)
            }

            root.setOnClickListener {
                showDetail(data[position])
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        AdapterMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    class ViewHolder(val view: AdapterMovieBinding) : RecyclerView.ViewHolder(view.root)

}