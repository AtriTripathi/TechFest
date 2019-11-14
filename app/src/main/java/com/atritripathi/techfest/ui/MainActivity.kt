package com.atritripathi.techfest.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.atritripathi.techfest.R
import com.atritripathi.techfest.api.ApiInterface
import com.atritripathi.techfest.models.Event
import com.atritripathi.techfest.utils.TopSpacingItemDecoration
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), EventsAdapter.Interaction {

    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null

    // vars
    lateinit var recyclerAdapter: EventsAdapter
    lateinit var eventsList: MutableList<Event>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("events")
        eventsList = mutableListOf()

        mDatabaseReference!!.addValueEventListener(object: ValueEventListener{
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (data in dataSnapshot.children) {
                        val event = data.getValue(Event::class.java)
                        eventsList.add(event!!)
                        recyclerAdapter.submitList(eventsList)
                    }
                }
            }
        })

        addToDatabase()

        initRecyclerView()

//        handleNetworkApi()

        bottom_navigation.setOnNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.action_recents -> {
                    Toast.makeText(this, "Recents", Toast.LENGTH_SHORT).show()

                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_favorites -> {

                    return@setOnNavigationItemSelectedListener true
                }
                R.id.action_nearby -> {

                    return@setOnNavigationItemSelectedListener true
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
    }

    private fun addToDatabase() {

        val eventId = mDatabaseReference!!.push().key

        val event = Event(eventId,"someTitle","10AM","Bla Bla Bla",50)
        mDatabaseReference!!.child(eventId!!).setValue(event).addOnCompleteListener {task ->
            if (task.isSuccessful) {
                Toast.makeText(this,"New Event added",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initRecyclerView() {
        recyclerAdapter = EventsAdapter()
        recyclerview.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val topSpacingDecorator = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecorator)
            recyclerAdapter = EventsAdapter(this@MainActivity)
            adapter = recyclerAdapter
        }
    }

    private fun handleNetworkApi() {
        val apiInterface = ApiInterface.create().getEvents()

        apiInterface.enqueue(object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>?, response: Response<List<Event>>?) {
                println("DEBUG ${response?.body()}")
                if (response?.body() != null)
                    recyclerAdapter.submitList(response.body()!!)
            }

            override fun onFailure(call: Call<List<Event>>?, t: Throwable?) {

            }
        })
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
            R.id.action_exit -> {
                Toast.makeText(applicationContext, "click on exit", Toast.LENGTH_LONG).show()
                finishAffinity()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    // Implement this if you get time.
    override fun onItemSelected(position: Int, item: Event) {
        // Open the card view and show details
        Toast.makeText(this, "Item selected", Toast.LENGTH_SHORT).show()
    }
}
