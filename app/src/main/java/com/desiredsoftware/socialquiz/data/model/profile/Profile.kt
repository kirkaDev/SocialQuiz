package com.desiredsoftware.socialquiz.data.model.profile

class Profile (
    val id: String,
    var avatarURI: String = "",
    var nickName: String = "Anonymous",
    var score: Long = 0,
    var role: ROLES = ROLES.NEW,
    var about: String = "",
    var instagram: String = "",
    var tiktok: String = "",
    var accountType : ACCOUNT_TYPES = ACCOUNT_TYPES.FREE,
    )
{

    companion object{
        const val FIELD_ID = "UID"
        const val FIELD_AVATAR_URI = "AVATAR_URI"
        const val FIELD_NICK_NAME = "NICK_NAME"
        const val FIELD_SCORE = "SCORE"
        const val FIELD_ROLE = "ROLE"
        const val FIELD_ABOUT = "ABOUT"
        const val FIELD_INSTAGRAM = "INSTAGRAM"
        const val FIELD_TIK_TOK = "TIK_TOK"
        const val FIELD_ACCOUNT_TYPE = "ACCOUNT_TYPE"

        enum class ROLES{
            NEW, AUTHOR, MODERATOR, ADMIN
        }

        enum class ACCOUNT_TYPES{
            FREE, IS_PAID, IS_REACHED_PREMIUM
        }
    }
}