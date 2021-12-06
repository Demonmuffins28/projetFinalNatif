package ca.qc.cgodin.projetfinalandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ca.qc.cgodin.projetfinalandroid.R
import ca.qc.cgodin.projetfinalandroid.databinding.ActivityAppBinding
import ca.qc.cgodin.projetfinalandroid.db.AppDatabase
import ca.qc.cgodin.projetfinalandroid.repository.AppRepository
import ca.qc.cgodin.projetfinalandroid.ui.viewModels.AppViewModel
import ca.qc.cgodin.projetfinalandroid.ui.viewModels.AppViewModelProviderFactory

class AppActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAppBinding

    lateinit var viewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val utilRepository = AppRepository(AppDatabase(this))
        val viewModelProviderFactory = AppViewModelProviderFactory(utilRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(AppViewModel::class.java)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.appNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}