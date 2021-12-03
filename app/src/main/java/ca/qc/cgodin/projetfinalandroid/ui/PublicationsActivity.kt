package ca.qc.cgodin.projetfinalandroid.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ca.qc.cgodin.projetfinalandroid.R
import ca.qc.cgodin.projetfinalandroid.SessionManager
import ca.qc.cgodin.projetfinalandroid.databinding.ActivityPublicationsBinding

class PublicationsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPublicationsBinding

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPublicationsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sessionManager = SessionManager(this)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.appNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        /*
        binding.btnRevoke.setOnClickListener {
            sessionManager.endUserSession(this)

            startActivity(Intent(this, MainActivity::class.java))
        }
         */
    }
}