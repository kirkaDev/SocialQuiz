package com.desiredsoftware.socialquiz.model.profile

data class Profile constructor (val id: String, val avatarURI: String, val isPremium : Boolean,
                                val propertiesArray : ArrayList<ProfileProperty>)


{

    class ProfileProperty (val propertyName: String, val propertyValue : String)
    {

    }
}