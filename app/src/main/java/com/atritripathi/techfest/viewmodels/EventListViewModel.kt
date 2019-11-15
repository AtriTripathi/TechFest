package com.atritripathi.techfest.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.atritripathi.techfest.models.Event
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * I had plans to take this approach, but... :(
 */
class EventListViewModel: ViewModel() {
    var events: MutableLiveData<List<Event>> = MutableLiveData()

    fun getEvents() {
        if (events.value == null) {
            FirebaseDatabase.getInstance()
                .getReference("events")
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                       if (dataSnapshot.exists()) {
                           var eventsList: MutableList<Event> = mutableListOf()
                           for (data in dataSnapshot.children) {
                               val event = data.getValue(Event::class.java)
                               eventsList.add(event!!)
                           }
                           events.postValue(eventsList)
//                           recyclerAdapter.submitList(events)
                       }
                    }

                })
        }
    }
}