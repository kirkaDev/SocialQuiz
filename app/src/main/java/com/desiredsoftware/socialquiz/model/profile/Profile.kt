package com.desiredsoftware.socialquiz.model.profile

class Profile (
    val id: String,
    val avatarURI: String = "https://img.icons8.com/color/48/000000/anonymous-mask.png",
    val nickName: String = "Anonymous",
    val score: Long = 0,
    val role: String = "Player",
    val about: String = "",
    val instagram: String = "",
    val tiktok: String = "",
    val accountType : String = "",
    )
{
    companion object{
        val FIELD_ID = "UID"
        val FIELD_AVATAR_URI = "AVATAR_URI"
        val FIELD_NICK_NAME = "NICK_NAME"
        val FIELD_SCORE = "SCORE"
        val FIELD_ROLE = "ROLE"
        val FIELD_ABOUT = "ABOUT"
        val FIELD_INSTAGRAM = "INSTAGRAM"
        val FIELD_TIK_TOK = "TIK_TOK"
        val FIELD_ACCOUNT_TYPE = "ACCOUNT_TYPE"
    }
}