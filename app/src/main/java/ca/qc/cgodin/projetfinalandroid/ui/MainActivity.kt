package ca.qc.cgodin.projetfinalandroid.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import ca.qc.cgodin.projetfinalandroid.databinding.ActivityMainBinding
import ca.qc.cgodin.projetfinalandroid.util.SharedPref

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    companion object {
        const val URL_API = "https://petits-gazouillis-api.herokuapp.com/api/"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        SharedPref.init(applicationContext);
        if (SharedPref.read(SharedPref.USER_TOKEN, null)?.isNotEmpty() == true)
            startActivity(Intent(this, AppActivity::class.java))
        else
            startActivity(Intent(this, LoginActivity::class.java))
    }
}