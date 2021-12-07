package ca.qc.cgodin.projetfinalandroid.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ca.qc.cgodin.projetfinalandroid.models.publications.Publication
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import ca.qc.cgodin.projetfinalandroid.R
import ca.qc.cgodin.projetfinalandroid.repository.AppRepository
import ca.qc.cgodin.projetfinalandroid.ui.viewModels.AppViewModel
import ca.qc.cgodin.projetfinalandroid.util.Resource
import ca.qc.cgodin.projetfinalandroid.util.SharedPref
import com.bumptech.glide.Glide

class PublicationAdapter: RecyclerView.Adapter<PublicationAdapter.PublicationsViewHolder>() {
    lateinit var viewModel: AppViewModel
    lateinit var appRepository: AppRepository

    private var abonne: MutableList<Int>? = mutableListOf()

    inner class PublicationsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

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

    override fun onBindViewHolder(holder: PublicationsViewHolder, position: Int) {
        val publication = differ.currentList[position]
        appRepository = AppRepository()
        holder.itemView.apply {
            val ivImageView: ImageView = findViewById(R.id.ivImage)
            val tvNom: TextView = findViewById(R.id.tvNom)
            val tvContenu: TextView = findViewById((R.id.tvContenu))
            val tvDate: TextView = findViewById((R.id.tvDate))
            val ibSuivre: ImageButton = findViewById(R.id.ibSuivre)
            Glide.with(this).asBitmap().load(publication.avatar).into(ivImageView)
            tvNom.text = publication.nom
            tvContenu.text = publication.corps
            tvDate.text = publication.horodatage

            var utilId = SharedPref.read(SharedPref.USER_ID, 0)
            if (publication.utilisateurId == utilId)
                ibSuivre.visibility = View.INVISIBLE
            else {
                var boolSuivi = false
                // check si utilisateur est abonner et changer couleur du bouton
                if (abonne?.find { id -> publication.utilisateurId == id} != null) {
                    ibSuivre.setColorFilter(Color.rgb(127, 0, 255))
                    boolSuivi = true
                }

                ibSuivre.setOnClickListener {
                    var token = "Bearer ${SharedPref.read(SharedPref.USER_TOKEN, null)}"

                    if (!boolSuivi) {
                        viewModel.setAbonner(token, publication.utilisateurId)
                        boolSuivi = true
                        //abonne?.add(publication.utilisateurId)
                        ibSuivre.setColorFilter(Color.rgb(127, 0, 255))
                        Toast.makeText(this.context, "Vous suivez maintenant ${publication.nom}", Toast.LENGTH_SHORT).show()

                    } else {
                        viewModel.setDesabonner(token, publication.utilisateurId)
                        boolSuivi = false
                        //abonne?.remove(publication.utilisateurId)
                        ibSuivre.colorFilter = null
                        Toast.makeText(this.context, "Vous ne suivez plus ${publication.nom}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun setAppViewModel(viewModel: AppViewModel) {
        this.viewModel = viewModel
    }

    fun setAbonner(abonne: List<Int>?) {
        this.abonne = abonne as MutableList<Int>?
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}