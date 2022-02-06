package com.desiredsoftware.socialquiz.data

import com.desiredsoftware.socialquiz.data.model.profile.Profile

class CommonDataSets {
    companion object{
        val newUserProfileDataSet = hashMapOf(
            Profile.FIELD_AVATAR_URI to "https://cdn.icon-icons.com/icons2/2643/PNG/512/male_boy_person_people_avatar_icon_159358.png",
            Profile.FIELD_NICK_NAME to "New user",
            Profile.FIELD_SCORE to 0L,
            Profile.FIELD_ROLE to Profile.Companion.ROLES.NEW.toString(),
            Profile.FIELD_ABOUT to String(),
            Profile.FIELD_INSTAGRAM to String(),
            Profile.FIELD_TIK_TOK to String(),
            Profile.FIELD_ACCOUNT_TYPE to Profile.Companion.ACCOUNT_TYPES.FREE.toString(),
        )
    }
}