package ca.qc.cgodin.projetfinalandroid.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import ca.qc.cgodin.projetfinalandroid.ApiClient
import ca.qc.cgodin.projetfinalandroid.LoginResponse
import ca.qc.cgodin.projetfinalandroid.SessionManager
import ca.qc.cgodin.projetfinalandroid.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var sessionManager: SessionManager
    private lateinit var apiClient: ApiClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        apiClient = ApiClient()
        sessionManager = SessionManager(this)

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
                            loginResponse.jeton?.let { it1 -> sessionManager.saveAuthToken(it1) }
                            loginResponse.idUtil?.let { it1 -> sessionManager.saveUserId(it1) }
                            Log.i("Jeton", loginResponse.jeton.toString())
                            Log.i("UtilID", loginResponse.idUtil.toString())

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
}