package edu.upf.tickeep.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.facebook.FacebookSdk.getApplicationContext
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.firestore.ktx.toObject
import edu.upf.tickeep.R
import edu.upf.tickeep.model.User
import java.util.*

class profile : Fragment() {

    lateinit var editTxtUser: EditText
    lateinit var editTxtEmail: EditText
    lateinit var editTxtPhone: EditText
    lateinit var editTxtPassword: EditText
    lateinit var txtTitleProfile: TextView
    lateinit var  txtNumberOfTickets: TextView
    private lateinit var  myDialog: Dialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val inflated = inflater.inflate(R.layout.fragment_profile, container, false)
        editTxtUser = inflated.findViewById(R.id.editTxt_user)
        editTxtEmail = inflated.findViewById(R.id.editTxt_Email)
        editTxtPhone = inflated.findViewById(R.id.editTxt_phone)
        editTxtPassword = inflated.findViewById(R.id.editTxt_password)
        txtNumberOfTickets = inflated.findViewById(R.id.txt_numberTickets)
        txtTitleProfile = inflated.findViewById(R.id.txt_userTitle)
        myDialog = Dialog(inflated.context)
        val c = edu.upf.tickeep.utils.Constants()
        c.firebaseFirestore.collection("users").document(c.firebaseAuth.currentUser?.uid.toString()).get().addOnSuccessListener {
                document->
            var user = document.toObject<User>()
            editTxtEmail.text = Editable.Factory.getInstance().newEditable(user?.email.toString())
            txtTitleProfile.text = user?.user.toString()

            editTxtUser.text =Editable.Factory.getInstance().newEditable(user?.user.toString())
            editTxtPhone.text = Editable.Factory.getInstance().newEditable(user?.phone.toString())
        }
        c.firebaseFirestore.collection("users").document(c.firebaseAuth.currentUser?.uid.toString()).
        collection("documents").get().addOnSuccessListener {
            documents ->
            var tickets = 0
            for (document in documents){

                tickets++
            }
            txtNumberOfTickets.text = tickets.toString()

        }

        editTxtUser.inputType = 0
        editTxtEmail.inputType = 0
        editTxtPhone.inputType = 0
        editTxtPassword. inputType = 0


        editTxtUser.setOnClickListener {
            showPopUp_user()
        }
        editTxtPhone.setOnClickListener {
            showPopUp_phone()
        }
        editTxtPassword.setOnClickListener {
            showPopUp_password()
        }
        return inflated
    }
    fun showPopUp_password() {
        val txtClose: TextView
        val editPass: EditText
        val editPass2: EditText
        val oldPassword:EditText
        val btnSave: Button

        myDialog.setContentView(R.layout.popup_pass)
        txtClose = myDialog.findViewById(R.id.txtClose_imgPass)
        btnSave = myDialog.findViewById(R.id.btnSave_popUpPass)
        editPass = myDialog.findViewById(R.id.editText_popUpPass)
        editPass2 = myDialog.findViewById(R.id.editText_popUpPass2)
        oldPassword = myDialog.findViewById(R.id.editText_popUpOldPass)
        btnSave.setOnClickListener {

            val c = edu.upf.tickeep.utils.Constants()
            if(editPass.text.isNotEmpty() && editPass2.text.isNotEmpty() && oldPassword.text.isNotEmpty() && (editPass.text.toString().equals(editPass2.text.toString()))){
                val credential = EmailAuthProvider
                    .getCredential(
                        c.firebaseAuth.currentUser?.email.toString()
                        , oldPassword.text.toString()
                    )

                c.firebaseAuth.currentUser?.reauthenticate(credential)
                    ?.addOnCompleteListener(
                        OnCompleteListener<Void?> { task ->
                            if (task.isSuccessful) {
                                c.firebaseAuth.currentUser!!
                                    .updatePassword(editPass.text.toString())
                                    .addOnCompleteListener(
                                        OnCompleteListener<Void?> { task ->
                                            if (task.isSuccessful) {
                                                myDialog.dismiss()
                                                displayToast(getString(R.string.update_password))
                                            } else {
                                                displayToast("Error auth")
                                            }
                                        })
                            } else {
                                displayToast(getString(R.string.error_updated))
                            }
                        })
            }else{
                displayToast(getString(R.string.emptyStringsError))
            }


        }


        txtClose.setOnClickListener { myDialog.dismiss() }
        myDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()
    }
    fun showPopUp_phone() {
        val txtClose: TextView
        val editPhone: EditText
        val btnSave: Button

        myDialog.setContentView(R.layout.popup_phone)
        txtClose = myDialog.findViewById(R.id.txtClose_imgPhone)
        btnSave = myDialog.findViewById(R.id.btnSave_popUpPhone)
        editPhone = myDialog.findViewById(R.id.editText_popUpPhone)
        btnSave.setOnClickListener {

            val c = edu.upf.tickeep.utils.Constants()
            if(!editPhone.text.isEmpty()){
                c.firebaseFirestore.collection("users").document(c.firebaseAuth.currentUser?.uid.toString())
                    .update("phone",editPhone.text.toString())
                    .addOnCompleteListener(OnCompleteListener<Void?> { task ->
                        if (task.isSuccessful) {
                            editTxtPhone.text = editPhone.text
                            myDialog.dismiss()
                            displayToast(getString(R.string.updated_phone))
                        } else {
                            displayToast(getString(R.string.error_updated))
                        }
                    })
            }else{
                displayToast(getString(R.string.emptyStringsError))
            }


        }


        txtClose.setOnClickListener { myDialog.dismiss() }
        myDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()


    }
    fun showPopUp_user() {
        val txtClose: TextView
        val editUser: EditText
        val btnSave: Button

        myDialog.setContentView(R.layout.popup_user)
        txtClose = myDialog.findViewById(R.id.txtClose_imgUser)
        btnSave = myDialog.findViewById(R.id.btnSave_popUpUser)
        editUser = myDialog.findViewById(R.id.editText_popUpUser)
        btnSave.setOnClickListener {

            val c = edu.upf.tickeep.utils.Constants()
            if(!editUser.text.isEmpty()){
                c.firebaseFirestore.collection("users").document(c.firebaseAuth.currentUser?.uid.toString())
                    .update("user",editUser.text.toString())
                    .addOnCompleteListener(OnCompleteListener<Void?> { task ->
                        if (task.isSuccessful) {
                            editTxtUser.text = editUser.text
                            txtTitleProfile.text = editUser.text.toString()
                            myDialog.dismiss()
                            displayToast(getString(R.string.updated_name))
                        } else {
                            displayToast(getString(R.string.error_updated))
                        }
                    })
            }else{
                displayToast(getString(R.string.emptyStringsError))
            }


        }


        txtClose.setOnClickListener { myDialog.dismiss() }
        myDialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        myDialog.show()


    }
    private fun displayToast(message: String) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show()
    }


}