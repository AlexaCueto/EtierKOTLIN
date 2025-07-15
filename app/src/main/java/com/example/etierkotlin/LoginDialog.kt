package com.example.etierkotlin

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.fragment.app.DialogFragment

class LoginDialog(private val onLoginSuccess: () -> Unit) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = LayoutInflater.from(requireContext())
        val view = inflater.inflate(R.layout.dialog_login, null)

        // UI elements with IDs
        val editUsername = view.findViewById<EditText>(R.id.editUsername)
        val editPassword = view.findViewById<EditText>(R.id.editPassword)

        builder.setView(view)
            .setTitle("DialogLogin")
            .setPositiveButton("Login") { _, _->
                if (authenticate(
                        editUsername.text.toString(),
                        editPassword.text.toString()
                    )
                ) {
                    onLoginSuccess.invoke()
                    dismiss()
                }
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }

        return builder.create()
    }

    private fun authenticate(username: String, password: String): Boolean {
        return username == "admin" && password == "password123"
    }
}