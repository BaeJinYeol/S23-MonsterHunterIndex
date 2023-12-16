package kr.ac.kumoh.ce.s20190558.monsterhunterindex

import retrofit2.http.GET

interface MonsterApi {
    @GET("monster")
    suspend fun getMonsters(): List<Monster>
}