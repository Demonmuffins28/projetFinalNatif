package ca.qc.cgodin.projetfinalandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import ca.qc.cgodin.projetfinalandroid.SessionManager
import ca.qc.cgodin.projetfinalandroid.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sessionManager: SessionManager

    companion object {
        const val URL_API = "https://petits-gazouillis-api.herokuapp.com/api/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sessionManager = SessionManager(this)

        // Verifier si le token est encore actif
        val currentTime = Calendar.getInstance().time
        var sessionIsActive = sessionManager.isSessionActive(currentTime, this)

        // Fausse validation pour le moment
        if (sessionManager.fetchAuthToken()?.isNotEmpty() == true) sessionIsActive = true

        // Si la session est active on va au publications
        if (sessionIsActive) startActivity(Intent(this, PublicationsActivity::class.java))
        else
            // Si la session n'est pas active on va a la connexion
            startActivity(Intent(this, LoginActivity::class.java))
    }
}