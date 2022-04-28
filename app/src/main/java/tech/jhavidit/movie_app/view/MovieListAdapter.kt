package tech.jhavidit.movie_app.view


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kotlinx.android.synthetic.main.movie_item.view.*
import tech.jhavidit.movie_app.R
import tech.jhavidit.movie_app.model.Result
import tech.jhavidit.movie_app.utilities.IMAGE_BASE_URL

class MovieListAdapter : RecyclerView.Adapter<MovieListAdapter.MyViewHolder>() {

    private var movieData = listOf<Result?>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(result: Result) {
            itemView.movie_name.text = result.title
            val str = "$IMAGE_BASE_URL${result.posterPath}"
            itemView.img_poster.load(str)
            Log.d("path", "$IMAGE_BASE_URL${result.posterPath}")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.movie_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        movieData[position]?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int {
        return movieData.size
    }

    fun setMovieData(movieData: List<Result?>) {
        this.movieData = movieData
        notifyDataSetChanged()
    }
}