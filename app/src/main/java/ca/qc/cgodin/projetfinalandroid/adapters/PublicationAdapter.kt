package ca.qc.cgodin.projetfinalandroid.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ca.qc.cgodin.projetfinalandroid.models.publications.Publication
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import ca.qc.cgodin.projetfinalandroid.R
import com.bumptech.glide.Glide

class PublicationAdapter: RecyclerView.Adapter<PublicationAdapter.PublicationsViewHolder>() {

    inner class PublicationsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    }

    private val differCallback = object : DiffUtil.ItemCallback<Publication>() {
        override fun areItemsTheSame(oldItem: Publication, newItem: Publication): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Publication, newItem: Publication): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicationsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pub_preview, parent, false)
        return PublicationsViewHolder(view)
    }

    // Changer pour publication item_util
    override fun onBindViewHolder(holder: PublicationsViewHolder, position: Int) {
        val publication = differ.currentList[position]
        val utilisateur = publication.utilisateurId
        holder.itemView.apply {
            val ivImageView: ImageView = findViewById(R.id.ivImage)
            val tvNom: TextView = findViewById(R.id.tvNom)
            val tvContenu: TextView = findViewById((R.id.tvContenu))
            val tvDate: TextView = findViewById((R.id.tvDate))
            //Glide.with(this).asBitmap().load(utilisateur.avatar).into(ivImageView)
            tvNom.text = publication.utilisateurId.toString()
            tvContenu.text = publication.corps
            tvDate.text = publication.horodatage
            setOnItemClickListener {
                onItemClickListener?.let { it(publication) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Publication) -> Unit)? = null

    fun setOnItemClickListener(listener: (Publication) -> Unit) {
        onItemClickListener = listener
    }
}