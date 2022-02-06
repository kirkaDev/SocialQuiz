package com.desiredsoftware.socialquiz.utils

import com.desiredsoftware.socialquiz.data.model.profile.Profile

class ProfileUtils {

    companion object{
        fun getDataSetForProfile(profile: Profile): MutableMap<String, *> {
            return mutableMapOf<String, Any>(
                Profile.FIELD_ID to profile.id,
                Profile.FIELD_AVATAR_URI to profile.avatarURI,
                Profile.FIELD_NICK_NAME to profile.nickName,
                Profile.FIELD_SCORE to profile.score,
                Profile.FIELD_ROLE to profile.role.toString(),
                Profile.FIELD_ABOUT to profile.about,
                Profile.FIELD_INSTAGRAM to profile.instagram,
                Profile.FIELD_TIK_TOK to profile.tiktok,
                Profile.FIELD_ACCOUNT_TYPE to profile.accountType.toString(),
            )
        }
    }
}