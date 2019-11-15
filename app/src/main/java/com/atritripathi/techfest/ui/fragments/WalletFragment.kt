package com.atritripathi.techfest.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.atritripathi.techfest.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

/**
 * A simple [Fragment] subclass.
 */
class WalletFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private var mTechiePoints: String = ""
    private var mUserId: String = ""

    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wallet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

        val user = FirebaseAuth.getInstance().currentUser
        user.let {
            mUserId = user!!.uid
        }

        database = FirebaseDatabase.getInstance().reference

        val ref = database.child("Users").child(mUserId).child("techiePoints")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    mTechiePoints = (dataSnapshot.value as Long).toString()
                }
            }

        })
    }
}
