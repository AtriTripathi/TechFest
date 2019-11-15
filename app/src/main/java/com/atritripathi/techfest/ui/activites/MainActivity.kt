package com.atritripathi.techfest.ui.activites

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.atritripathi.techfest.R
import com.atritripathi.techfest.models.Event
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mAuth: FirebaseAuth? = null

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        navController = Navigation.findNavController(this, R.id.fragment)
        bottomNav.setupWithNavController(navController)

        NavigationUI.setupActionBarWithNavController(this,navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }


//    private fun getFromDatabase() {
//
//        mDatabaseReference!!.addValueEventListener(object: ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    for (data in dataSnapshot.children) {
//                        val event = data.getValue(Event::class.java)
//                        eventsList.add(event!!)
//                        recyclerAdapter.submitList(eventsList)
//                    }
//                }
//            }
//        })
//    }

    /**
     * I would've used this to add a new event to the list, online
     */
    private fun addToDatabase() {

        val eventId = mDatabaseReference!!.push().key

        val event = Event(eventId,"someTitle","10AM","Bla Bla Bla","50")
        mDatabaseReference!!.child(eventId!!).setValue(event).addOnCompleteListener {task ->
            if (task.isSuccessful) {
                Toast.makeText(this,"New Event added",Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_share -> {
                Toast.makeText(applicationContext, "click on share", Toast.LENGTH_LONG).show()
                return true
            }
            R.id.action_signout -> {
                initiateSignout()
                return true
            }
            R.id.action_exit -> {
                Toast.makeText(applicationContext, "click on exit", Toast.LENGTH_LONG).show()
                finishAffinity()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initiateSignout() {
        FirebaseAuth.getInstance().signOut().run {
            startActivity(Intent(this@MainActivity, RegisterActivity::class.java))
            finish()
        }
    }
}
