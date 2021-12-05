package ca.qc.cgodin.projetfinalandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ca.qc.cgodin.projetfinalandroid.R
import ca.qc.cgodin.projetfinalandroid.databinding.ActivityAppBinding
import ca.qc.cgodin.projetfinalandroid.db.AppDatabase
import ca.qc.cgodin.projetfinalandroid.repository.UtilisateursRepository

class AppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAppBinding

    lateinit var viewModel: UtilisateursViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val utilRepository = UtilisateursRepository(AppDatabase(this))
        val viewModelProviderFactory = UtilisateursViewModelProviderFactory(utilRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(UtilisateursViewModel::class.java)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.appNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}