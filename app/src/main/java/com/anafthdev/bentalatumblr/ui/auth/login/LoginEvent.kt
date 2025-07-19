package com.anafthdev.bentalatumblr.ui.auth.login

import com.anafthdev.bentalatumblr.foundation.base.ui.UiEvent

sealed class LoginEvent: UiEvent() {
    data object LoggedIn: LoginEvent()
}
