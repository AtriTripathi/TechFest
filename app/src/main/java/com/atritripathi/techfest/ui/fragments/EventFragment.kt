package com.atritripathi.techfest.ui.fragments


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.atritripathi.techfest.R
import com.atritripathi.techfest.models.Event
import com.atritripathi.techfest.ui.activites.EventActivity
import com.atritripathi.techfest.utils.TopSpacingItemDecoration
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_event.*

/**
 * A simple [Fragment] subclass.
 */
class EventFragment : Fragment(), EventsAdapter.Interaction {
    override fun onItemSelected(position: Int, item: Event) {
        // Open the card view and show details
        val intent = Intent(activity, EventActivity::class.java)
        intent.putExtra("EVENT_EXTRA", item)
        startActivity(intent)
    }

    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null


    // vars
    lateinit var recyclerAdapter: EventsAdapter
    lateinit var eventsList: MutableList<Event>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("events")
        eventsList = mutableListOf()

        initRecyclerView()

    }

    private fun initRecyclerView() {
        recyclerAdapter = EventsAdapter()
        recyclerview.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacingDecorator = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecorator)
            recyclerAdapter =
                EventsAdapter(this@EventFragment)
            adapter = recyclerAdapter
        }
    }

    override fun onResume() {
        super.onResume()

        FirebaseDatabase.getInstance()
            .getReference("events")
            .addListenerForSingleValueEvent(object : ValueEventListener {
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
    }


    private fun getFromDatabase() {
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

    }

    /**
     * Can be used to create and add Users own events
     */
    private fun addToDatabase() {

        val eventId = mDatabaseReference!!.push().key

        val event = Event(eventId, "someTitle", "10AM", "Bla Bla Bla", "50")
        mDatabaseReference!!.child(eventId!!).setValue(event).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(activity, "New Event added", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

