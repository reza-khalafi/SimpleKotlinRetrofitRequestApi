package com.example.simplekotlinretrofitrequestapi

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainActivity : AppCompatActivity() {

    lateinit var getIdEditText: EditText
    lateinit var sendButton: Button
    lateinit var resultTextView: TextView

    val services by lazy {
        Services.create()
    }
    var disposable: Disposable? = null
    var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        getIdEditText = findViewById(R.id.main_get_id_edit_text)
        sendButton = findViewById(R.id.main_send_button)
        resultTextView = findViewById(R.id.main_result_text_view)


        sendButton.setOnClickListener {
            userId = getIdEditText.text.toString().toInt()
            Log.e("userId",userId.toString())
            resultTextView.text = ""
            hideKeyboardwithoutPopulate(this)
            sendRequestToGetUserInfo()
        }


    }



    fun sendRequestToGetUserInfo() {
        this.disposable =
            services.getUser(userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    Log.d("Result", "There are ${result.data.toString()} user")
                    resultTextView.text = getFormattedUserInfo(result.data)
                },{ error ->
                    Log.e("Result", "There are  error: ${error.localizedMessage} ")
                })
    }


    fun getFormattedUserInfo(userModel: UserModel) : String {
        var resultString:String = ""
        resultString += userModel.id
        resultString += "\n"
        resultString += userModel.first_name
        resultString += "\n"
        resultString += userModel.last_name
        resultString += "\n"
        resultString += userModel.email
        resultString += "\n"
        resultString += userModel.avatar
        return resultString
    }


    fun hideKeyboardwithoutPopulate(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(activity.currentFocus!!.windowToken, 0)
    }


}
