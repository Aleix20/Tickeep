package edu.upf.tickeep.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import edu.upf.tickeep.R

class login : AppCompatActivity() {
    lateinit var txtEmail: EditText
    lateinit var txtPasword: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loginaccount)
        val buttonSignIn: Button = findViewById(R.id.btn_singinLogin)
        val btnReg: Button = findViewById(R.id.btn_registerFromLogin)
    txtEmail=findViewById(R.id.edit_emailLogin)
        txtPasword = findViewById(R.id.edit_passwordLogin)
        val c = edu.upf.tickeep.utils.Constants()
        btnReg.setOnClickListener {
            val intent = Intent(this, register::class.java)
            startActivity(intent)

        }
        buttonSignIn.setOnClickListener {
            if(!(txtEmail.text.isEmpty() && txtPasword.text.isEmpty())){
                c.firebaseAuth.signInWithEmailAndPassword(txtEmail.text.toString(), txtPasword.text.toString()).addOnCompleteListener {
                    if(it.isSuccessful){



                        val intent = Intent(this, nav_activity::class.java)
                        startActivity(intent)
                        finish()

                    }else{


                        Toast.makeText(
                            applicationContext,
                            resources.getString(R.string.failLogin),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }else{
                Toast.makeText(this, R.string.emptyStringsError, Toast.LENGTH_SHORT).show()
            }

        }
    }
}