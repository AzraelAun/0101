package com.example.menu


import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.menu.databinding.FragmentGarbageCollectionBinding
import android.app.DatePickerDialog
import com.example.menu.GlobalVariable.userID
import kotlinx.android.synthetic.main.fragment_garbage_collection.*
import java.util.*

class GarbageCollectionService : Fragment() {

    lateinit var handler: AccDatabase
    lateinit var name:String

    val c: Calendar = Calendar.getInstance();
    val mYear = c.get(Calendar.YEAR);
    val mMonth = c.get(Calendar.MONTH);
    val mDay = c.get(Calendar.DAY_OF_MONTH);

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentGarbageCollectionBinding>(inflater,
            R.layout.fragment_garbage_collection,container,false)

        handler = AccDatabase(binding.root.context)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var data = handler.retrieveData(userID)
        for(i in 0..(data.size-1)){
            name = data.get(i).username
            textAddress2.append(data.get(i).address)
        }


        textDate.setOnClickListener() {

            val datePickerDialog = DatePickerDialog(
                context!!,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    textDate.setText("Service Date : " + dayOfMonth.toString() + " / " + (monthOfYear + 1) + " / " + year)
                }, mYear, mMonth, mDay
            )
            val currentTimestamp = System.currentTimeMillis()
            var NEXTDAY:Long = 1 * 24 * 60 * 60 * 1000
            datePickerDialog.datePicker.minDate = currentTimestamp + NEXTDAY
            var DURATION:Long = 7 * 24 * 60 * 60 * 1000
            datePickerDialog.datePicker.maxDate = currentTimestamp + DURATION
            datePickerDialog.show();
        }

        btnRequest.setOnClickListener {

            if (textDate.text.toString() == "Click to Select Service Date")
            {
                val builder = AlertDialog.Builder(context!!)

                with(builder) {
                    setTitle("Warning!")
                    setMessage("Please select a service date!")
                    setPositiveButton("OK", null)
                }
                val alertDialog = builder.create()
                alertDialog.show()
            }
            else
            {
                val recipient = "heeci-wm17@student.tarc.edu.my"
                val subject = "Garbage Collection Service Request"
                val message = textDate.text.toString() + "\n\n" + "User Name : " + name + "\n\n" + "Address : " + textAddress2.text.toString()

                sendEmail(recipient, subject, message)
            }

        }
    }

    private fun sendEmail(recipient: String, subject: String, message: String) {

        val mIntent = Intent(Intent.ACTION_SEND)

        mIntent.data = Uri.parse("mailto:")
        mIntent.type = "text/plain"

        mIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(recipient))

        mIntent.putExtra(Intent.EXTRA_SUBJECT, subject)

        mIntent.putExtra(Intent.EXTRA_TEXT, message)


        try {
            startActivity(Intent.createChooser(mIntent, "Choose Email Client..."))
        }
        catch (e: Exception){
            Toast.makeText(activity, e.message, Toast.LENGTH_LONG).show()
        }

    }
}