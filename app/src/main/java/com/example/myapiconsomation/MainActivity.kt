package com.example.myapiconsomation


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    lateinit var userRV: RecyclerView
    lateinit var loadingPB: ProgressBar
    lateinit var usersAd: UsersAdapter
    lateinit var userList: ArrayList<UsersRvModel>
    lateinit var saveBtn: FloatingActionButton
    lateinit var mCheckBox: CheckBox
    private lateinit var settings: FloatingActionButton
    private lateinit var next: FloatingActionButton
    private var isAllFabsVisible: Boolean? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // on below line we are initializing
        // our variable with their ids.
        userRV = findViewById(R.id.Rvusers)
        loadingPB = findViewById(R.id.idPBLoading)

        saveBtn = findViewById(R.id.save)
        //mCheckBox = findViewById(R.id.checkBox)
        next = findViewById(R.id.next)
        settings = findViewById(R.id.settings)

        saveBtn.visibility = View.GONE
        next.visibility = View.GONE

        isAllFabsVisible = false

        settings.setOnClickListener(View.OnClickListener {
            (if (!isAllFabsVisible!!) {
                saveBtn.show()
                next.show()
                true
            } else {
                next.hide()
                saveBtn.hide()
                false
            }).also { isAllFabsVisible = it }
        })

        next.setOnClickListener {
            val intent = Intent(applicationContext, savedusers::class.java)

            //intent.putExtra("message_key", str)

            startActivity(intent)
            Toast.makeText(this, "saved users ", Toast.LENGTH_SHORT).show()
        }

        saveBtn.setOnClickListener {
            // if (mCheckBox.isChecked){

            val sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            // creating a new variable for gson.
            val gson = Gson()

            // getting data from gson and storing it in a string.
            val json: String = gson.toJson(userList)

            // below line is to save data in shared
            // prefs in the form of string.
            editor.putString("users", json)

            // below line is to apply changes
            // and save data in shared prefs.
            editor.apply()

            // after saving data we are displaying a toast message.
            Toast.makeText(this, "Saved Array List to Shared preferences. ", Toast.LENGTH_SHORT)
                .show()

            //}
        }

        // on below line we are initializing our list
        userList = ArrayList()

        // on below line we are initializing our adapter.
        usersAd = UsersAdapter(userList)

        // on below line we are setting
        // adapter to recycler view.
        userRV.adapter = usersAd

        // on below line we are calling
        // get data method to get data.
        getData()



    }



    private fun getData() {
        // on below line we are creating a variable for url
        var url = "https://jsonplaceholder.typicode.com/users/"

        // on below line we are creating a
        // variable for our request queue
        val queue = Volley.newRequestQueue(this@MainActivity)

        // on below line we are creating a request
        // variable for making our json object request.
        val request =
            // as we are getting json object response and we are making a get request.
            JsonArrayRequest(Request.Method.GET, url, null, { response ->
                // this method is called when we get successful response from API.
                loadingPB.visibility = View.GONE
                try {
                    for (i in 0 until response.length()) {
                        // on below line we are extracting
                        // data from each json object
                        val respObj = response.getJSONObject(i)
                        val id = respObj.getString("id")
                        val name = respObj.getString("name")
                        val usernamme =respObj.getString("username")
                        val email=respObj.getString("email")

                        // on below line we are adding data to our list
                        userList.add(UsersRvModel(id, name, email))


                        // on below line we are notifying
                        // our adapter that data has updated.
                        usersAd.notifyDataSetChanged()
                    }

                    // on below line we
                    // are handling exception
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }, { error ->
                // in this case we are simply displaying a toast message.
                Toast.makeText(this@MainActivity, "Fail to get response", Toast.LENGTH_SHORT)
                    .show()
            })
        // at last we are adding our
        // request to our queue.
        queue.add(request)
    }

}
