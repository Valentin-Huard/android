package tp.lpdip.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class TP1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tp1)
        var btnConnexion = findViewById(R.id.tp1_btnConnexion) as Button
        var editTextMail = findViewById(R.id.tp1_mail) as EditText
        var editTextPwd = findViewById(R.id.tp1_pwd) as EditText
        btnConnexion.setOnClickListener {
            checkConnexion(editTextMail.text.toString(), editTextPwd.text.toString())
        }
    }
    fun checkConnexion(mail:String, password:String){
        var statut:Boolean = false
        lateinit var response:String

        Toast.makeText(this, mail, Toast.LENGTH_SHORT).show()
        if (mail == "admin") {
            if (password == "password") {
                statut = true
            }
        }

        if (statut) {
            response = "Connection réussi"
        }else{

            response = "Connection échouée, mail ou mots de passe incorrect"
        }
        Toast.makeText(this, response, Toast.LENGTH_SHORT).show()
    }
}

