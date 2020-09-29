package com.prit.mvvmkotlintest.view

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.prit.mvvmkotlintest.Adapter.Loginadapter
import com.prit.mvvmkotlintest.Adapter.User_Info_Adapter
import com.prit.mvvmkotlintest.R
import com.prit.mvvmkotlintest.databinding.MainActivityBinding
import com.prit.mvvmkotlintest.model.LoginuserModel
import com.prit.mvvmkotlintest.model.User_info_Data
import com.prit.mvvmkotlintest.viewmodel.MainActivityViewModel
import kotlinx.android.synthetic.main.main_activity.*


class MainActivity : AppCompatActivity() {
    var mDeveloper_CustomAdapter: Loginadapter? = null
    var userinfo_adapter: User_Info_Adapter? = null
    private  var myViewModel: MainActivityViewModel?=null
    private  var viewDataBinding: MainActivityBinding?=null
    private lateinit var recycler_view: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         viewDataBinding =   DataBindingUtil.setContentView(this, R.layout.main_activity)
        myViewModel=ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
        viewDataBinding?.mainActivityViewModel=myViewModel
        viewDataBinding?.setLifecycleOwner { lifecycle }

        // bind RecyclerView
        val recyclerView1 = viewDataBinding?.recyclerView
        recyclerView1!!.setLayoutManager(LinearLayoutManager(this))
        recyclerView1!!.setHasFixedSize(true)
        //init the Custom adataper
        mDeveloper_CustomAdapter = Loginadapter()
        //set the CustomAdapter
        recyclerView1.setAdapter(mDeveloper_CustomAdapter)



        ///load user data in recyclerview
        val recyclerView = viewDataBinding?.recyclerView1
        recyclerView!!.setLayoutManager(LinearLayoutManager(this))
        recyclerView!!.setHasFixedSize(true)
        //init the Custom adataper
        userinfo_adapter = User_Info_Adapter(ArrayList<User_info_Data>())
        //set the CustomAdapter
        recyclerView.setAdapter(userinfo_adapter)

        myViewModel?.getuser_info_list(this)
        myViewModel!!.loginresponse.observe(this, Observer {

          //  Toast.makeText(this,it, Toast.LENGTH_LONG).show()
        })

        myViewModel!!.namesList.observe(this, Observer {
            val mylist = ArrayList<String>()
            for (item in it){
                mylist.add(item.username)
            }
            val spinner :Spinner = findViewById(R.id.spinner_name)
            spinner.adapter=ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,mylist)
            mDeveloper_CustomAdapter?.setDeveloperList(it as ArrayList<LoginuserModel>)
        })

        myViewModel?.user_info_list?.observe(this, Observer {

            userinfo_adapter?.updatelList(it,this)
        } )
    }



}