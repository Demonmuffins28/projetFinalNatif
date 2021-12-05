package ca.qc.cgodin.projetfinalandroid.adapters

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ca.qc.cgodin.projetfinalandroid.models.utilisateur.Utilisateur
import android.util.Base64
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import ca.qc.cgodin.projetfinalandroid.R
import com.bumptech.glide.Glide

class AppAdapter(): RecyclerView.Adapter<AppAdapter.UtilisateurViewHolder>() {
    private var utilisateurs: List<Utilisateur> = emptyList()

    inner class UtilisateurViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val ivImageView: ImageView = itemView.findViewById(R.id.ivUserImage)
        val tvNom: TextView = itemView.findViewById(R.id.tvNom)

        override fun toString(): String {
            return super.toString() + " '" + tvNom.text + "'"
        }
    }

//    private val differCallback = object : DiffUtil.ItemCallback<Utilisateur>() {
//        override fun areItemsTheSame(oldItem: Utilisateur, newItem: Utilisateur): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: Utilisateur, newItem: Utilisateur): Boolean {
//            return oldItem == newItem
//        }
//    }

//    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UtilisateurViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_util_preview, parent, false)
        return UtilisateurViewHolder(view)
    }

    override fun onBindViewHolder(holder: UtilisateurViewHolder, position: Int) {
        val utilisateur = utilisateurs[position]
        holder.tvNom.text = utilisateur.nom
        holder.itemView.apply {
            Glide.with(this).asBitmap().load(utilisateur.avatar).into(holder.ivImageView)
            holder.tvNom.text = utilisateur.nom
        }
//        val utilisateur = differ.currentList[position]
//        holder.itemView.apply {
//            var userImage = findViewById<ImageView>(R.id.ivUserImage)
//            var nom = findViewById<TextView>(R.id.tvNom)
//            var courriel = findViewById<TextView>(R.id.tvCourriel)
//            var propos = findViewById<TextView>(R.id.tvAPropos)
//
//            val imgBytes = Base64.decode(utilisateur.avatar, Base64.DEFAULT)
//            val decodedImage = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.size)
//            //userImage.setImageBitmap(decodedImage)
//            Glide.with(this).load(decodedImage).into(userImage)
//            nom.text = utilisateur.nom
//            courriel.text = utilisateur.courriel
//            propos.text = utilisateur.aProposDeMoi
//            setOnItemClickListener {
//                onItemClickListener?.let { it(utilisateur) }
//            }
//        }
    }

    override fun getItemCount(): Int = utilisateurs.size

    fun setUtilisateurs(utilisateurs: List<Utilisateur>) {
        this.utilisateurs = utilisateurs
        notifyDataSetChanged()
    }

    private var onItemClickListener: ((Utilisateur) -> Unit)? = null

    fun setOnItemClickListener(listener: (Utilisateur) -> Unit) {
        onItemClickListener = listener
    }
}