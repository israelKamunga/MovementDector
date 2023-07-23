package com.isy_code.notionsadvanced

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.isy_code.notionsadvanced.Data.Retrofit.CordonneeService
import com.isy_code.notionsadvanced.Entities.Coordinate
import com.isy_code.notionsadvanced.Repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.URL

class VM : ViewModel() {

    private var mutablelivedata = MutableLiveData<List<String>>()
    val livedata:LiveData<List<String>> = mutablelivedata
    var service : CordonneeService? = null
    var connexionObserver = MutableLiveData("loading")
    var retrofit : Retrofit? = null
    var gson: Gson? = null

    init {
        gson = GsonBuilder().setLenient().create()
    }

    fun initialiserClient(url:String){
        try {
            retrofit = Retrofit.Builder()
                .baseUrl("http://"+url+":3000")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
            service = retrofit?.create(CordonneeService::class.java)
        }catch (e:Exception){
            connexionObserver.value = "failed"
        }

    }
    fun putData(coordinate:Coordinate){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                service?.updateAngles(coordinate)
                connexionObserver.postValue("success")
            }catch (e:Exception){
                e.printStackTrace()
                connexionObserver.postValue("failed")
            }
        }
    }
}