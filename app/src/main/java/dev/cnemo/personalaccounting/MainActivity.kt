package dev.cnemo.personalaccounting

import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.FirebaseApp
import dev.cnemo.personalaccounting.databinding.ActivityMainBinding
import dev.cnemo.personalaccounting.models.User
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        user = buildUser()

        navView.setupWithNavController(navController)
    }

    fun getUser() = user

    private fun buildUser(): User{
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        var username = sharedPreferences.getString("username", null)
        var uuid = sharedPreferences.getString("uuid", null)
        if (username.isNullOrEmpty()) {
            usernameSetup(sharedPreferences){
                username = it
                Toast.makeText(this, "Usuario correctamente seteado a '$it'", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this, "Sesion iniciada como '$username'", Toast.LENGTH_LONG).show()
        }
        if (uuid.isNullOrEmpty()) {
            uuidSetup(sharedPreferences){
                uuid = it
            }
        }
        assert(username.isNullOrEmpty().not())
        assert(uuid.isNullOrEmpty().not())
        return User(uuid!!, username!!)
    }

    private fun usernameSetup(sharedPreferences: SharedPreferences, callback: (String) -> Unit){
        if (sharedPreferences.getString("username", null) == null ){ // Handle invalid username
            val input: EditText = EditText(this).apply {
                inputType = InputType.TYPE_CLASS_TEXT
            }
            val dialogBuilder = AlertDialog.Builder(this).apply {
                setTitle("Nombre de usuario")
                setView(input)
                setPositiveButton("Ok", null)
            }
            val dialog = dialogBuilder.create()
            dialog.setOnShowListener {
                val mPositiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                mPositiveButton.setOnClickListener {
                    if (input.text.isNullOrBlank()){
                        input.error = "Es obligatorio ingresar un usuario valido"
                    } else {
                        val username = input.text.toString()

                        sharedPreferences.edit().putString("username", username).apply()
                        callback(username)
                        dialog.dismiss()
                    }
                }
            }
            dialog.setCanceledOnTouchOutside(false)
            dialog.show()
        }
    }

    private fun uuidSetup(sharedPreferences: SharedPreferences, callback: (String) -> Unit){
        if (sharedPreferences.getString("uuid", null) == null) { // Handle invalid uuid
            val uuid = UUID.randomUUID().toString()
            sharedPreferences.edit().putString("uuid", uuid).apply()
            callback(uuid)
        }
    }
}