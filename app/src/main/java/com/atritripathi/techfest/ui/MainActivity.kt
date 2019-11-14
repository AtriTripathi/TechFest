package com.atritripathi.techfest.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.atritripathi.techfest.R
import com.atritripathi.techfest.api.ApiInterface
import com.atritripathi.techfest.models.Event
import com.atritripathi.techfest.utils.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), EventsAdapter.Interaction
{

    // vars
    lateinit var recyclerAdapter: EventsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()

        handleNetworkApi()
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

        //apiInterface.enqueue( Callback<List<Movie>>())
        apiInterface.enqueue( object : Callback<List<Event>> {
            override fun onResponse(call: Call<List<Event>>?, response: Response<List<Event>>?) {
                println("DEBUG ${response?.body()}")
                if(response?.body() != null)
                    recyclerAdapter.submitList(response.body()!!)
            }

            override fun onFailure(call: Call<List<Event>>?, t: Throwable?) {

            }
        })
    }


    // Implement this if you get time.
    override fun onItemSelected(position: Int, item: Event) {
        // Open the card view and show details
        Toast.makeText(this,"Item selected",Toast.LENGTH_SHORT).show()
    }
}
