package com.atritripathi.techfest.ui.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.atritripathi.techfest.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.zxing.BarcodeFormat
import kotlinx.android.synthetic.main.fragment_profile.*
import me.dm7.barcodescanner.zxing.ZXingScannerView

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : Fragment() {

    private lateinit var database: DatabaseReference
    private var mTechiePoints: String = ""
    private var mFirstname: String = ""
    private var mLastname: String = ""
    private var mUserId: String = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
                    mFirstname = (dataSnapshot.value as Long).toString()
                    mLastname = (dataSnapshot.value as Long).toString()

                    println("DEBUG $mTechiePoints")
                }
            }
        })


        val qrCodeScanner = view.findViewById<ZXingScannerView>(R.id.qrCodeScanner)
        setScannerProperties()
    }

    /**
     * Tried to implement QR Scanner
     */
    private fun setScannerProperties() {
        qrCodeScanner.setFormats(listOf(BarcodeFormat.QR_CODE))
        qrCodeScanner.setAutoFocus(true)
        qrCodeScanner.setLaserColor(R.color.colorAccent)
        qrCodeScanner.setMaskColor(R.color.colorAccent)
        qrCodeScanner.setAspectTolerance(0.5f)
    }

    private fun startScan() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            if (checkSelfPermission(Manifest.permission.CAMERA,) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.CAMERA),
//                    MY_CAMERA_REQUEST_CODE)
//                return
//            }
//        }
        qrCodeScanner.startCamera()
//        qrCodeScanner.setResultHandler(this)
    }


}
