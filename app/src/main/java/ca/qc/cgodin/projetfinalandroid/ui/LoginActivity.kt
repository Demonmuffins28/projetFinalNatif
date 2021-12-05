package ca.qc.cgodin.projetfinalandroid.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import ca.qc.cgodin.projetfinalandroid.api.ApiClient
import ca.qc.cgodin.projetfinalandroid.models.LoginResponse
import ca.qc.cgodin.projetfinalandroid.databinding.ActivityLoginBinding
import ca.qc.cgodin.projetfinalandroid.util.SharedPref
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        apiClient = ApiClient()

        SharedPref.clearStoredData(this)
        SharedPref.read(SharedPref.USER_TOKEN, null)?.let { Log.i("Token", it) }

        binding.btnConnexion.setOnClickListener {
            val utilisateur = binding.txtUtil.text.toString().trim()
            val password = binding.txtPassword.text.toString().trim()

            if (utilisateur.isEmpty()) {
                binding.txtUtil.error = "User required"
                binding.txtUtil.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.txtPassword.error = "Password required"
                binding.txtPassword.requestFocus()
                return@setOnClickListener
            }

            val authPayload = "$utilisateur:$password"
            val data = authPayload.toByteArray()
            val base64 = Base64.encodeToString(data, Base64.NO_WRAP)

            apiClient.getTokenService().login("Basic $base64".trim())
                .enqueue(object : Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        Log.i("Fail", "Failure")
                    }

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        val loginResponse = response.body()

                        if (loginResponse != null) {
                            SharedPref.write(SharedPref.USER_TOKEN, loginResponse.jeton)
                            SharedPref.write(SharedPref.USER_ID, loginResponse.idUtil)
//                            Log.i("Jeton", loginResponse.jeton.toString())
//                            Log.i("UtilID", loginResponse.idUtil.toString())

                            val intent = Intent(applicationContext, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                            startActivity(intent)

                        } else {
                                Toast.makeText(applicationContext, loginResponse?.erreur.toString(), Toast.LENGTH_LONG).show()
                                Log.i("Nulled", loginResponse?.erreur.toString())
                        }
                    }
                })
        }
    }

    override fun onBackPressed() {
        if (!SharedPref.read(SharedPref.USER_TOKEN, null).isNullOrEmpty())
            super.onBackPressed()
        else {
            Toast.makeText(this, "Connectez-vous!", Toast.LENGTH_LONG).show()
        }

    }
}