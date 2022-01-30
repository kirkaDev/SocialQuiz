package com.desiredsoftware.socialquiz.model.profile

class Profile (
    val id: String,
    val nickName: String = "Anonymous",
    val role: String = "Player",
    val isPremium : Boolean = false,
    val score: Long = 0,
    val avatarURI: String = "https://img.icons8.com/color/48/000000/anonymous-mask.png",
    val about: String = "",
    val instagram: String = "",
    )
{
    companion object{
        val FIELD_ID = "UID"
        val FIELD_NICK_NAME = "NICK_NAME"
        val FIELD_ROLE = "ROLE"
        val FIELD_IS_PREMIUM = "IS_PREMIUM"
        val FIELD_SCORE = "SCORE"
        val FIELD_AVATAR_URI = "AVATAR_URI"
        val FIELD_ABOUT = "ABOUT"
        val FIELD_INSTAGRAM = "INSTAGRAM"
        val FIELD_EMAIL = "EMAIL"
    }
}