package com.matrix.test.presenting_cards.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Toast
import com.matrix.test.presenting_cards.R
import com.matrix.test.presenting_cards.adapter.UsersAdapter
import com.matrix.test.presenting_cards.member.APIs
import com.matrix.test.presenting_cards.member.FinalResponse
import com.matrix.test.presenting_cards.member.User
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONObject

class MainActivity : AppCompatActivity(), FetchData {

    val TAG = MainActivity::class.java.simpleName

    private lateinit var shimmerFrameLayout: ShimmerFrameLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: UsersAdapter
    lateinit var listUsers:ArrayList<User>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpRecyclerView()
        shimmerFrameLayout = findViewById(R.id.shimmerFrameLayout)
    }

    override fun fetch() {
        val request= JsonObjectRequest(APIs.RESPONSE,null,object: Response.Listener<JSONObject>{
            override fun onResponse(response: JSONObject?) {
                if(response==null){
                    Toast.makeText(this@MainActivity,"couldn't get response", Toast.LENGTH_SHORT).show()
                    return
                }

                val response= Gson().fromJson<FinalResponse>(response.toString(),object: TypeToken<FinalResponse>(){}.type)

                listUsers.clear()
                listUsers.addAll(response.data.data)
                mAdapter.notifyDataSetChanged()

                shimmerFrameLayout.stopShimmer()
                shimmerFrameLayout.visibility= View.GONE
            }
        }, object : Response.ErrorListener{
            override fun onErrorResponse(error: VolleyError?) {
                Log.e(TAG,"Error: "+error?.message)
                Toast.makeText(this@MainActivity,"Error: "+error?.message, Toast.LENGTH_SHORT).show()
            }
        })

        MyApplication.getInstance().addToRequestQueue(request)
    }

    override fun onStart() {
        super.onStart()
        fetch();
    }

    override fun onResume() {
        super.onResume()
        shimmerFrameLayout.startShimmer()
    }

    override fun onPause() {
        shimmerFrameLayout.stopShimmer()
        super.onPause()
    }

    override fun setUpRecyclerView() {
        listUsers=ArrayList()
        mAdapter= UsersAdapter(listUsers,this)

        val layoutManager= LinearLayoutManager(this)
        recyclerView=findViewById(R.id.recyclerViewUsers)
        recyclerView.layoutManager=layoutManager
        recyclerView.itemAnimator= DefaultItemAnimator()
        recyclerView.adapter=mAdapter
    }
}