package com.matrix.test.presenting_cards.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.matrix.test.presenting_cards.R
import com.matrix.test.presenting_cards.member.User
import com.bumptech.glide.Glide

class UsersAdapter(var listUsers:List<User>, val mContext:Context) :RecyclerView.Adapter<UsersAdapter.UserViewHolder>(),FillData<User>{

    override fun onCreateViewHolder(p0: ViewGroup?, p1: Int): UserViewHolder {
        val v=LayoutInflater.from(p0?.context).inflate(R.layout.item_user,p0,false)
        return UserViewHolder(v)
    }

    override fun set(list: List<User>) {
        this.listUsers=list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return listUsers.size
    }

    override fun onBindViewHolder(v: UserViewHolder?, p1: Int) {
        val itemUser=listUsers.get(p1)

        v?.txtFullName?.text=itemUser.full_name
//        get descriptions
        if(!itemUser.service.isEmpty()){
            v?.linearLayoutDescription?.visibility = View.VISIBLE
            var description = "";
            for(service in itemUser.service){
                description += service.description+"\n";
            }
            v?.txtDescription?.text=description
        }else{
            v?.linearLayoutDescription?.visibility = View.GONE
        }


        if(itemUser.img!=null){
            v?.linearLayout?.visibility=View.VISIBLE
            Glide.with(mContext).load(itemUser.getImageLink()).into(v?.imgUser)
        }else{
            v?.linearLayout?.visibility=View.GONE
        }
    }

    class UserViewHolder(v:View):RecyclerView.ViewHolder(v){
        val txtFullName:TextView
        val txtDescription:TextView
        val imgUser:ImageView
        val linearLayout:LinearLayout
        val linearLayoutDescription: LinearLayout

        init {
            txtFullName=v.findViewById(R.id.txtFullName)
            txtDescription=v.findViewById(R.id.txtDescription)
            imgUser=v.findViewById(R.id.imgUser)
            linearLayout=v.findViewById(R.id.linearLayoutImage)
            linearLayoutDescription = v.findViewById(R.id.linearLayoutDescrption)
        }
    }
}