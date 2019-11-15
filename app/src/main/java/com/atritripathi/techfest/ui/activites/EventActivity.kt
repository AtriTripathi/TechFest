package com.atritripathi.techfest.ui.activites

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.atritripathi.techfest.R
import com.atritripathi.techfest.models.Event
import kotlinx.android.synthetic.main.activity_event.*

class EventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        val event = intent.getParcelableExtra<Event>("EVENT_EXTRA")

        event?.apply {
            tv_placeholder_title.text = event.title
            tv_placeholder_body.text = event.body
            tv_placeholder_tp.text = event.techiePoints.toString()
            println("DEBUG: ${event.techiePoints.toString()}")
        }

        button_register_event.setOnClickListener {
            Toast.makeText(this,"You're registered for the event",Toast.LENGTH_SHORT).show()

        }
    }
}
