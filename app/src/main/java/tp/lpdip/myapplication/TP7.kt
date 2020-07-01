package tp.lpdip.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.BroadcastReceiver
import android.content.Context
import android.widget.TextView
import android.content.IntentFilter
import android.os.BatteryManager


class TP7 : AppCompatActivity() {

    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)
        setContentView(R.layout.activity_tp7)
        contentTxt = this.findViewById(R.id.text) as TextView
        this.registerReceiver(this.mBatInfoReceiver,
                IntentFilter(Intent.ACTION_BATTERY_CHANGED))
    }

    private var contentTxt: TextView? = null
    private val mBatInfoReceiver = object : BroadcastReceiver() {
        override fun onReceive(arg0: Context, intent: Intent) {
            lateinit var retour:String
            if(BatteryManager.BATTERY_STATUS_CHARGING === 1){
                retour = "En charge"
            }else{
                retour = "Sur batterie"
            }
            contentTxt!!.text = retour
        }
    }
}
