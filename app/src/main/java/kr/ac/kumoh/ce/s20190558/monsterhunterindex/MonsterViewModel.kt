package kr.ac.kumoh.ce.s20190558.monsterhunterindex

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MonsterViewModel() : ViewModel() {
    private val SERVER_URL = "https://port-0-s23-monsterhunterbe-5yc2g32mlomgpvzu.sel5.cloudtype.app/"
    private val monsterApi: MonsterApi
    private val _monsterList = MutableLiveData<List<Monster>>()
    val monsterList: LiveData<List<Monster>>
        get() = _monsterList

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        monsterApi = retrofit.create(MonsterApi::class.java)
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            try {
                val response = monsterApi.getMonsters()
                _monsterList.value = response
            } catch (e: Exception) {
                Log.e("fetchData()", e.toString())
            }
        }
    }
}