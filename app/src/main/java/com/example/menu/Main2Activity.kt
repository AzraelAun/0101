package com.example.menu

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.fragment_register.*
import java.util.*


class Main2Activity : AppCompatActivity() {

    lateinit var handler: AccDatabase

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadLocale()
        @Suppress("UNUSED_VARIABLE")
      //  val binding = DataBindingUtil.setContentView<ActivityMain2Binding>(this, R.layout.activity_main2)
        handler = AccDatabase(this)

        setContentView(R.layout.fragment_login)

        imageView5.setOnClickListener{
            changeLang()
        }

        txtSignup.setOnClickListener{
            setContentView(R.layout.fragment_register)
            sign_button.setOnClickListener {
                if(username.text.toString().isNotEmpty()){
                    if (handler.verifyUsername(username.text.toString())) {
                        if (pass.text.toString().isNotEmpty()) {
                            if (pass.text.toString() == password.text.toString()) {
                                if (email.text.toString().isNotEmpty()) {
                                    handler.insertUserData(username.text.toString(), pass.text.toString(), email.text.toString(), address.text.toString())
                                    Toast.makeText(this, getString(R.string.Register_Successful), Toast.LENGTH_SHORT).show()
                                    setContentView(R.layout.fragment_login)
                                    loginToMenu()
                                } else {
                                    Toast.makeText(this, getString(R.string.Email_cannot_empty), Toast.LENGTH_SHORT).show()
                                }
                            } else
                                Toast.makeText(this, getString(R.string.Incorrect_CPassword), Toast.LENGTH_SHORT).show()
                        } else
                            Toast.makeText(this, getString(R.string.Passwordempty), Toast.LENGTH_SHORT).show()
                    } else
                        Toast.makeText(this, getString(R.string.Username_Used), Toast.LENGTH_SHORT).show()
                } else
                    Toast.makeText(this, getString(R.string.Usernameempty), Toast.LENGTH_SHORT).show()
            }
            textView5.setOnClickListener{
                setContentView(R.layout.fragment_login)
                loginToMenu()
            }
        }

        loginToMenu()
    }


    private fun changeLang(){
        val listItems = arrayOf("华语","Malay","English")

        val mBuilder = AlertDialog.Builder(this@Main2Activity)
        mBuilder.setTitle("Change Language")
        mBuilder.setSingleChoiceItems(listItems,-1)

        {dialog, i ->
            if(i == 0){
                setLocate("zh")
                Toast.makeText(this, "更换语言- 华语", Toast.LENGTH_SHORT).show()
                recreate()
            }
            else if(i == 1){
                setLocate("ms")
                Toast.makeText(this, "Tukar Bahasa - Melay", Toast.LENGTH_SHORT).show()
                recreate()
            }
            else if(i == 2){
                setLocate("en")
                Toast.makeText(this, "Change Language - English", Toast.LENGTH_SHORT).show()
                recreate()
            }
            dialog.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()
    }
    private fun setLocate(Lang : String){
        val locale = Locale(Lang)

        Locale.setDefault(locale)

        val config = Configuration()

        config.locale = locale
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()
    }

    private fun loadLocale(){
        val prefs = getSharedPreferences("Settings", Context.MODE_PRIVATE)
        val language = prefs.getString("My_Lang","")
        setLocate(language.toString())

    }

    private fun loginToMenu(){
        loginbtn.setOnClickListener {
            if (login_username.text.toString().isNotEmpty() && login_pass.text.toString().isNotEmpty()) {
                if (handler.userLogin(login_username.text.toString(), login_pass.text.toString())) {
                    Toast.makeText(this, getString(R.string.Login_Successful), Toast.LENGTH_SHORT).show()
                    GlobalVariable.userID = login_username.text.toString()
                    GlobalVariable.LoginPassword = login_pass.text.toString()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, getString(R.string.UPassw_Incorrect), Toast.LENGTH_SHORT).show()
                }
            }else
                Toast.makeText(this, getString(R.string.UPassw_empty), Toast.LENGTH_SHORT).show()
        }
    }


}
