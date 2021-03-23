package web.id.fajar.moviestar.ui.tvshow.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import web.id.fajar.moviestar.BuildConfig.imageUrl
import web.id.fajar.moviestar.data.model.tvshow.DataTvShow
import web.id.fajar.moviestar.databinding.AdapterVerticalTvshowBinding

class VerticalTvShowAdapter (
    private val showDetail: (DataTvShow) -> Unit
) : RecyclerView.Adapter<VerticalTvShowAdapter.ViewHolder>() {

    private var data = ArrayList<DataTvShow>()

    fun setData(movieList: List<DataTvShow>?) {
        if (movieList == null) return
        data.clear()
        data.addAll(movieList)
        notifyDataSetChanged()
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.view) {
            tvTitle.text = data[position].name

            holder.itemView.also {
                Glide.with(it.context)
                    .load(imageUrl + data[position].poster_path)
                    .into(imgPoster)
            }

            root.setOnClickListener {
                showDetail(data[position])
            }
        }
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        AdapterVerticalTvshowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    class ViewHolder(val view: AdapterVerticalTvshowBinding) : RecyclerView.ViewHolder(view.root)

}