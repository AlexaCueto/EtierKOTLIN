package com.example.etierkotlin

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment


class LoginDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialog_login, null)

        val usernameField = view.findViewById<EditText>(R.id.editUsername)
        val passwordField = view.findViewById<EditText>(R.id.editPassword)

        val builder = AlertDialog.Builder(requireContext())
        builder.setView(view)
        val dialog = builder.create()

        val loginButton = view.findViewById<Button>(R.id.btnLogin)
        loginButton.setOnClickListener {
            val username = usernameField.text.toString()
            val password = passwordField.text.toString()

            if (username == "admin" && password == "etier123") {
                Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
                // You can navigate to MainActivity here if needed
            } else {
                Toast.makeText(requireContext(), "Invalid credentials", Toast.LENGTH_SHORT).show()
            }
        }

        return dialog
    }
}
