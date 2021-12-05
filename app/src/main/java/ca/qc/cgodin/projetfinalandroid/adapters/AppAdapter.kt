package ca.qc.cgodin.projetfinalandroid.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ca.qc.cgodin.projetfinalandroid.models.utilisateur.Utilisateur
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import ca.qc.cgodin.projetfinalandroid.R
import com.bumptech.glide.Glide

class AppAdapter: RecyclerView.Adapter<AppAdapter.UtilisateurViewHolder>() {

    inner class UtilisateurViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    }

    private val differCallback = object : DiffUtil.ItemCallback<Utilisateur>() {
        override fun areItemsTheSame(oldItem: Utilisateur, newItem: Utilisateur): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Utilisateur, newItem: Utilisateur): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UtilisateurViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_util_preview, parent, false)
        return UtilisateurViewHolder(view)
    }

    override fun onBindViewHolder(holder: UtilisateurViewHolder, position: Int) {
        val utilisateur = differ.currentList[position]
        holder.itemView.apply {
            val ivImageView: ImageView = findViewById(R.id.ivUserImage)
            val tvNom: TextView = findViewById(R.id.tvNom)
            val tvCourriel: TextView = findViewById((R.id.tvCourriel))
            val tvAPropos: TextView = findViewById((R.id.tvAPropos))
            Glide.with(this).asBitmap().load(utilisateur.avatar).into(ivImageView)
            tvNom.text = utilisateur.nom
            tvCourriel.text = utilisateur.courriel
            tvAPropos.text = utilisateur.aProposDeMoi
            setOnItemClickListener {
                onItemClickListener?.let { it(utilisateur) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Utilisateur) -> Unit)? = null

    fun setOnItemClickListener(listener: (Utilisateur) -> Unit) {
        onItemClickListener = listener
    }
}