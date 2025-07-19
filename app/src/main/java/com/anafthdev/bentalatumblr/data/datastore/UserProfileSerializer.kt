package com.anafthdev.bentalatumblr.data.datastore

import androidx.datastore.core.Serializer
import com.anafthdev.bentalatumblr.ProtoUserProfile
import java.io.InputStream
import java.io.OutputStream

object UserProfileSerializer : Serializer<ProtoUserProfile> {

    override val defaultValue: ProtoUserProfile
        get() = ProtoUserProfile()

    override suspend fun readFrom(input: InputStream): ProtoUserProfile {
        return ProtoUserProfile.ADAPTER.decode(input)
    }

    override suspend fun writeTo(t: ProtoUserProfile, output: OutputStream) {
        ProtoUserProfile.ADAPTER.encode(output, t)
    }

}
