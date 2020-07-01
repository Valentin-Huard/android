package tp.lpdip.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class TP3 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tp3)
        var btnConnexion = findViewById(R.id.tp3_btnConnexion) as Button
        var editTextMail = findViewById(R.id.tp3_mail) as EditText
        var editTextPwd = findViewById(R.id.tp3_pwd) as EditText
        btnConnexion.setOnClickListener {
            checkConnexion(editTextMail.text.toString(), editTextPwd.text.toString())
        }
    }
    fun checkConnexion(mail:String, password:String){
        val textViewReponse = findViewById(R.id.tp3_reponse) as TextView
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
        textViewReponse.text = response
        val monIntentRetour : Intent =  Intent(this,TP3_activiteRetour::class.java)
        if (statut) {
            startActivity(monIntentRetour)
        }
    }
}