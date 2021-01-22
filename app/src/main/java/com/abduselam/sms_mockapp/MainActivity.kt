package com.abduselam.sms_mockapp

import android.app.Activity
import android.content.Context
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*


class MainActivity : AppCompatActivity() {
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
            //TODO DATABASE
            val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            val view = this.currentFocus
            imm.hideSoftInputFromWindow(view!!.windowToken, 0)
            thanksTextView.text =
                "Thanks You!!\nYou have successfully donated " + message + " Birr to Feed All"
            thanksTextView.visibility = View.VISIBLE

        })

    }

}