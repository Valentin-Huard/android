package tp.lpdip.myapplication

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lateinit var btn1:Button
        btn1 = findViewById(R.id.button_tp1)
        btn1.setOnClickListener{val intent = Intent(this, TP1::class.java)
            startActivity(intent)}

        lateinit var btn2:Button
        btn2 = findViewById(R.id.button_tp2)
        btn2.setOnClickListener{val intent = Intent(this, TP2::class.java)
            startActivity(intent)}

        lateinit var btn3:Button
        btn3 = findViewById(R.id.button_tp3)
        btn3.setOnClickListener{val intent = Intent(this, TP3::class.java)
            startActivity(intent)}

        lateinit var btn4:Button
        btn4 = findViewById(R.id.button_tp4)
        btn4.setOnClickListener{val intent = Intent(this, TP4_menu::class.java)
            startActivity(intent)}

        lateinit var btn5:Button
        btn5 = findViewById(R.id.button_tp6)
        btn5.setOnClickListener{val intent = Intent(this, TP6::class.java)
            startActivity(intent)}

        lateinit var bt6:Button
        bt6 = findViewById(R.id.button_tp7)
        bt6.setOnClickListener{val intent = Intent(this, TP7::class.java)
            startActivity(intent)}

        lateinit var bt7:Button
        bt7 = findViewById(R.id.button_tp8)
        bt7.setOnClickListener{val intent = Intent(this, TP8::class.java)
            startActivity(intent)}
    }
}
