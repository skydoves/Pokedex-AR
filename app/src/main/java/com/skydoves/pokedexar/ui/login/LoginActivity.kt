package com.skydoves.pokedexar.ui.login

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Button
import android.widget.EditText
import com.amn.easysharedpreferences.EasySharedPreference
import com.amn.easysharedpreferences.EasySharedPreferenceConfig
import com.skydoves.pokedexar.R
import com.skydoves.pokedexar.ui.main.MainActivity
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.net.CookieManager

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        EasySharedPreferenceConfig.initDefault(EasySharedPreferenceConfig.Builder().inputFileName("easy_preference").inputMode(Context.MODE_PRIVATE).build())


        //val okHttpClient = OkHttpClient.Builder().cookieJar(JavaNetCookieJar(CookieManager())).build()
        val retrofit = Retrofit.Builder().baseUrl("http://192.249.18.193:80")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(LoginService::class.java)

        //val idEditText = findViewById<EditText>(R.id.id)
        //val pwEditText = findViewById<EditText>(R.id.password)


        findViewById<Button>(R.id.login_button).setOnClickListener {

            val dialog = Dialog(this)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_login)

            val idEditText = dialog.findViewById<EditText>(R.id.idEditText)
            val passwordEditText = dialog.findViewById<EditText>(R.id.passwordEditText)
            dialog.findViewById<Button>(R.id.real_login_button).setOnClickListener{

                val loading = Dialog(this@LoginActivity)
                loading.setContentView(R.layout.loading_login)
                loading.setCancelable(false);
                loading.show()

                service.requestLogin(idEditText.text.toString(), passwordEditText.text.toString()).enqueue(object : Callback<Login> {
                    override fun onFailure(call: Call<Login>, t: Throwable) {
                        loading.dismiss()
                    }

                    override fun onResponse(call: Call<Login>, response: Response<Login>) {
                        try {
                            println(response.body().toString())
                            println(response.body()!!.token)
                            val token = response.body()!!.token
                            dialog.dismiss()

                            EasySharedPreference.Companion.putString("token", token)




                            intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            loading.dismiss()
                            finish()
                        } catch (e: Exception){
                            loading.dismiss()
                            println(e.toString())
                        }

                    }
                })
            }

            dialog.show()
        }
    }
}