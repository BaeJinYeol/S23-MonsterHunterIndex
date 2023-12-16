package kr.ac.kumoh.ce.s20190558.monsterhunterindex

import com.google.gson.annotations.SerializedName

data class Monster(
    val id: Int,
    val name: String,
    val nickname: String,
    val weakness: String,
    val species: String,
    val maps: String,
    @SerializedName("icon_address") val iconAddress: String,
    @SerializedName("image_address") val imageAddress: String
)