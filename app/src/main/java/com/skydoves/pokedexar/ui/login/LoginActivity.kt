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
import android.widget.TextView
import android.widget.Toast
import com.amn.easysharedpreferences.EasySharedPreference
import com.amn.easysharedpreferences.EasySharedPreferenceConfig
import com.skydoves.pokedexar.R
import com.skydoves.pokedexar.ui.main.MainActivity
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Exception
import java.net.CookieManager
import kotlin.math.sign

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        EasySharedPreferenceConfig.initDefault(EasySharedPreferenceConfig.Builder().inputFileName("easy_preference").inputMode(Context.MODE_PRIVATE).build())

        if(EasySharedPreference.Companion.getString("token", "") != "") {
            intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val retrofit = Retrofit.Builder().baseUrl("http://192.249.18.193:80")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val service = retrofit.create(LoginService::class.java)
        val service2 = retrofit.create(SignupService::class.java)

        findViewById<Button>(R.id.login_button).setOnClickListener {

            val dialog = Dialog(this)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_login)

            val idEditText = dialog.findViewById<EditText>(R.id.idEditText)
            val passwordEditText = dialog.findViewById<EditText>(R.id.passwordEditText)

            val loginErrorMessage = dialog.findViewById<TextView>(R.id.login_error_message)

            dialog.findViewById<Button>(R.id.real_login_button).setOnClickListener{

                val loading = Dialog(this@LoginActivity)
                loading.setContentView(R.layout.loading_login)
                loading.setCancelable(false);
                loading.show()

                service.requestLogin(idEditText.text.toString(), passwordEditText.text.toString()).enqueue(object : Callback<Login> {
                    override fun onFailure(call: Call<Login>, t: Throwable) {
                        loginErrorMessage.text = "서버 연결에 문제가 발생했습니다."
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
                            loginErrorMessage.text = "아이디와 비밀번호가 일치하지 않습니다."
                            loading.dismiss()
                        }

                    }
                })
            }
            dialog.show()
        }

        findViewById<Button>(R.id.signup_button).setOnClickListener {

            val dialog = Dialog(this)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.dialog_signup)

            val idEditText = dialog.findViewById<EditText>(R.id.idEditText)
            val nameEditText = dialog.findViewById<EditText>(R.id.nameEditText)
            val emailEditText = dialog.findViewById<EditText>(R.id.emailEditText)

            // TO DO 이메일
            val passwordEditText = dialog.findViewById<EditText>(R.id.passwordEditText)
            val passwordEditTextR = dialog.findViewById<EditText>(R.id.passwordEditText_repeat)

            val signupErrorMessage = dialog.findViewById<TextView>(R.id.signup_error_message)

            dialog.findViewById<Button>(R.id.real_signup_button).setOnClickListener{

                if(passwordEditText.text.toString() != passwordEditTextR.text.toString()){
                    signupErrorMessage.text = "비밀번호가 일치하지 않습니다."
                } else {

                    val loading = Dialog(this@LoginActivity)
                    loading.setContentView(R.layout.loading_login)
                    loading.setCancelable(false);
                    loading.show()

                    service2.requestSignup(
                        idEditText.text.toString(),
                        nameEditText.text.toString(),
                        emailEditText.text.toString(),
                        passwordEditText.text.toString()
                    ).enqueue(object : Callback<Signup> {
                        override fun onFailure(call: Call<Signup>, t: Throwable) {
                            signupErrorMessage.text = "서버 연결에 문제가 발생했습니다."
                            loading.dismiss()
                        }

                        override fun onResponse(call: Call<Signup>, response: Response<Signup>) {
                            try {
                                //println(response.body().toString())
                                //println(response.body()!!.token)
                                val success = response.body()!!.success
                                val message = response.body()!!.message
                                if (success == 1) {
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "회원가입에 성공하였습니다.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    dialog.dismiss()
                                } else {
                                    signupErrorMessage.text = message
                                }

                                //EasySharedPreference.Companion.putString("token", token)
                                loading.dismiss()
                                //finish()
                            } catch (e: Exception) {
                                signupErrorMessage.text = "서버 연결에 문제가 발생했습니다."
                                loading.dismiss()
                            }

                        }
                    })
                }
            }
            dialog.show()
        }
    }
}