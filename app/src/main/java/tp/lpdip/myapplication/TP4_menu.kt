package tp.lpdip.myapplication

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_tp4_menu.*
import android.content.SharedPreferences



class TP4_menu : AppCompatActivity() {

    private var PRIVATE_MODE = 0
    private val PREF_TP1 = "preference"
    private var context: Context = this
    private lateinit var preferences: SharedPreferences
    private lateinit var myStringData: String


    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                loadFragment(HomeFragement())
                val preferences = context.getSharedPreferences(PREF_TP1, PRIVATE_MODE).edit()
                        .putString("Fragement", "home")
                        .apply()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorite -> {
                loadFragment(FavoriteFragment())
                val preferences = context.getSharedPreferences(PREF_TP1, PRIVATE_MODE).edit()
                        .putString("Fragement", "favorite")
                        .apply()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_settings -> {
                loadFragment(SettingsFragment())
                val preferences = context.getSharedPreferences(PREF_TP1, PRIVATE_MODE).edit()
                        .putString("Fragement", "settings")
                        .apply()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tp4_menu)
        if (this::preferences.isInitialized){
            myStringData = preferences.toString()
        }else{
            myStringData = "home"
            navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        }


        when (myStringData) {
            "home" -> {
                loadFragment(HomeFragement())
                val preferences = context.getSharedPreferences(PREF_TP1, PRIVATE_MODE).edit()
                        .putString("Fragement", "home")
                        .apply()
            }
            "favorite" -> {
                loadFragment(FavoriteFragment())
                val preferences = context.getSharedPreferences(PREF_TP1, PRIVATE_MODE).edit()
                        .putString("Fragement", "favorite")
                        .apply()
            }
            "settings" -> {
                loadFragment(SettingsFragment())
                val preferences = context.getSharedPreferences(PREF_TP1, PRIVATE_MODE).edit()
                        .putString("Fragement", "settings")
                        .apply()
            }
        }
    }
}
