package com.abduselam.sms_mockapp

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.type.DateTime
import java.time.Instant
import java.time.Instant.*
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.HashMap


class MainActivity : AppCompatActivity() {

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sendButton = findViewById<Button>(R.id.send_button)
        val messageTextView = findViewById<TextView>(R.id.message_text_view)
        val thanksTextView = findViewById<TextView>(R.id.thanks_text_view)
        sendButton.setOnClickListener(View.OnClickListener {
            val messageEditText = findViewById<EditText>(R.id.message_edit_text)
            val message = messageEditText.text
            //Toast.makeText(this, message, Toast.LENGTH_LONG).show()
            messageTextView.text = message
            messageTextView.visibility = View.VISIBLE
            messageEditText.setText("")
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val view = this.currentFocus
            imm.hideSoftInputFromWindow(view!!.windowToken, 0)
            val db = FirebaseFirestore.getInstance()
            val transaction = HashMap<String, Any>()
            transaction["amount"] = message.toString().toInt()
            transaction["entity"] = "0925610101"
            transaction["enitiy_name"] = "0925610101"
            transaction["payment_system"] = 3
            transaction["timestamp"] = java.util.Calendar.getInstance()
            transaction["type"] = 1
            db.collection("transactions")
                    .add(transaction)
                    .addOnSuccessListener {
                        db.collection("balance")
                                .get()
                                .addOnSuccessListener {
                                    val amountData = it.documents[0].data
                                    val amountValue = amountData?.get("amount")
                                    val newAmountValue = amountValue.toString().toInt() + message.toString().toInt()

                                    db.collection("balance")
                                            .document("amount")
                                            .update("amount", newAmountValue)
                                            .addOnSuccessListener {
                                                thanksTextView.text =
                                                        "Thank You!!\nYou have\nsuccessfully\ndonated $message Birr\n to Feed All."
                                                thanksTextView.visibility = View.VISIBLE
                                            }
                                }
                    }
                    .addOnFailureListener {
                        thanksTextView.text = "TransactionFailed!\nPlease Try again Later!"
                        thanksTextView.visibility = View.VISIBLE
                    }


        })


    }
}