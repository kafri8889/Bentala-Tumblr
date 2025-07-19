package com.anafthdev.bentalatumblr.ui.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.LinkInteractionListener
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.anafthdev.bentalatumblr.R
import com.anafthdev.bentalatumblr.foundation.theme.BentalaTumblrTheme

@Composable
@Preview(showSystemUi = true)
private fun LoginScreenPreview() {
    BentalaTumblrTheme {
        LoginScreenContent(
            state = LoginState(),
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    modifier: Modifier = Modifier,
    onLoggedIn: () -> Unit = {}
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val event by viewModel.uiEvent.collectAsStateWithLifecycle(null)

    LaunchedEffect(event) {
        when (event) {
            is LoginEvent.LoggedIn -> onLoggedIn()
        }
    }

    LoginScreenContent(
        state = state,
        onEmailChange = viewModel::setEmail,
        onPasswordChange = viewModel::setPassword,
        onRememberMeChange = viewModel::setIsRememberMe,
        onPasswordVisibilityChange = viewModel::setIsPasswordVisible,
        onLogin = viewModel::login,
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .imeNestedScroll()
    )

}

@Composable
private fun LoginScreenContent(
    state: LoginState,
    modifier: Modifier = Modifier,
    onEmailChange: (String) -> Unit = {},
    onPasswordChange: (String) -> Unit = {},
    onRememberMeChange: (Boolean) -> Unit = {},
    onPasswordVisibilityChange: (Boolean) -> Unit = {},
    onLoginWithGoogle: () -> Unit = {},
    onLogin: () -> Unit = {}
) {

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        contentPadding = PaddingValues(16.dp),
        modifier = modifier
    ) {
        item {
            Column {
                Spacer(Modifier.height(64.dp))

                Text(
                    text = stringResource(R.string.login_login),
                    style = MaterialTheme.typography.headlineSmall
                )

                Text(
                    text = stringResource(R.string.login_welcome),
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(Modifier.height(64.dp))
            }
        }

        item {
            OutlinedTextField(
                value = state.email,
                onValueChange = onEmailChange,
                shape = RoundedCornerShape(8.dp),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_mail),
                        contentDescription = null
                    )
                },
                label = {
                    Text(stringResource(R.string.login_email))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding()
            )
        }

        item {
            OutlinedTextField(
                value = state.password,
                onValueChange = onPasswordChange,
                shape = RoundedCornerShape(8.dp),
                visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                leadingIcon = {
                    Icon(
                        painter = painterResource(R.drawable.ic_lock),
                        contentDescription = null
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            onPasswordVisibilityChange(!state.isPasswordVisible)
                        }
                    ) {
                        Icon(
                            imageVector = if (state.isPasswordVisible) Icons.Rounded.Visibility else Icons.Rounded.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                label = {
                    Text(stringResource(R.string.login_password))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding()
            )
        }

        item {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Checkbox(
                    checked = state.isRememberMe,
                    onCheckedChange = onRememberMeChange,
                )

                Text(
                    text = stringResource(R.string.login_remember_me),
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier
                        .weight(1f)
                )

                Text(
                    text = buildAnnotatedString {
                        withLink(LinkAnnotation.Clickable(
                            tag = "forgot_password",
                            styles = TextLinkStyles(
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.colorScheme.primary
                                ).toSpanStyle()
                            ),
                            linkInteractionListener = object : LinkInteractionListener {
                                override fun onClick(link: LinkAnnotation) {

                                }
                            }
                        )) {
                            append(stringResource(R.string.login_forgot_password))
                        }
                    }
                )
            }
        }

        item {
            Button(
                onClick = onLogin,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ButtonDefaults.MinHeight.times(1.2f))
            ) {
                Text(stringResource(R.string.login_login))
            }
        }

        item {
            Text(
                text = buildAnnotatedString {
                    append(" - ")
                    append(stringResource(R.string.login_login_with))
                    append(" - ")
                },
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        item {
            OutlinedButton(
                onClick = onLoginWithGoogle,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ButtonDefaults.MinHeight.times(1.2f))
            ) {
                Image(
                    painter = painterResource(R.drawable.google_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(20.dp)
                        .align(Alignment.CenterVertically)
                )

                Spacer(Modifier.width(8.dp))

                Text(stringResource(R.string.google))
            }
        }

        item {
            Spacer(Modifier.height(8.dp))

            Text(
                text = buildAnnotatedString {
                    append(stringResource(R.string.login_dont_have_account))

                    append(" ")

                    withLink(LinkAnnotation.Clickable(
                        tag = "sign_up",
                        styles = TextLinkStyles(
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.primary
                            ).toSpanStyle()
                        ),
                        linkInteractionListener = object : LinkInteractionListener {
                            override fun onClick(link: LinkAnnotation) {

                            }
                        }
                    )) {
                        append(stringResource(R.string.login_signup))
                    }
                },
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }

}
