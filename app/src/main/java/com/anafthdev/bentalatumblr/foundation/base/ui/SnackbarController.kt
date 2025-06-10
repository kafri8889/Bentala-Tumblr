package com.anafthdev.bentalatumblr.foundation.base.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

/**
 * Install the controller, provide the [LocalSnackbarControllerState] to composition local
 * and wrap [content] to scaffold
 */
@Composable
fun SnackbarController(
	modifier: Modifier = Modifier,
	state: SnackbarControllerState = rememberSnackbarControllerState(),
	content: @Composable () -> Unit
) {
	CompositionLocalProvider(
		LocalSnackbarControllerState provides state
	) {
		Scaffold(
			modifier = modifier,
			contentWindowInsets = WindowInsets(0),
			snackbarHost = {
				SnackbarHost(state.snackbarHostState)
			}
		) { scaffoldPadding ->
			Box(modifier = Modifier.padding(scaffoldPadding)) {
				content()
			}
		}
	}
}

@Composable
fun rememberSnackbarControllerState(
	snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
): SnackbarControllerState {
	return remember {
		SnackbarControllerState(snackbarHostState)
	}
}

@Stable
class SnackbarControllerState(
	val snackbarHostState: SnackbarHostState
) {

	private val currentSnackbarData: SnackbarData?
		get() = snackbarHostState.currentSnackbarData

	val visuals: SnackbarVisuals?
		get() = currentSnackbarData?.visuals

	/**
	 * Shows or queues to be shown a [Snackbar] at the bottom of the [Scaffold] to which this state
	 * is attached and suspends until the snackbar has disappeared.
	 *
	 * [SnackbarHostState] guarantees to show at most one snackbar at a time. If this function is
	 * called while another snackbar is already visible, it will be suspended until this snackbar is
	 * shown and subsequently addressed. If the caller is cancelled, the snackbar will be removed
	 * from display and/or the queue to be displayed.
	 *
	 * All of this allows for granular control over the snackbar queue from within:
	 *
	 * @sample androidx.compose.material3.samples.ScaffoldWithCoroutinesSnackbar
	 *
	 * To change the Snackbar appearance, change it in 'snackbarHost' on the [Scaffold].
	 *
	 * @param message text to be shown in the Snackbar
	 * @param actionLabel optional action label to show as button in the Snackbar
	 * @param withDismissAction a boolean to show a dismiss action in the Snackbar. This is
	 * recommended to be set to true for better accessibility when a Snackbar is set with a
	 * [SnackbarDuration.Indefinite]
	 * @param duration duration to control how long snackbar will be shown in [SnackbarHost], either
	 * [SnackbarDuration.Short], [SnackbarDuration.Long] or [SnackbarDuration.Indefinite].
	 *
	 * @return [SnackbarResult.ActionPerformed] if option action has been clicked or
	 * [SnackbarResult.Dismissed] if snackbar has been dismissed via timeout or by the user
	 */
	suspend fun showSnackbar(
		message: String,
		actionLabel: String? = null,
		withDismissAction: Boolean = false,
		duration: SnackbarDuration =
			if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Indefinite
	): SnackbarResult = snackbarHostState.showSnackbar(message, actionLabel, withDismissAction, duration)

	/**
	 * Shows or queues to be shown a [Snackbar] at the bottom of the [Scaffold] to which this state
	 * is attached and suspends until the snackbar has disappeared.
	 *
	 * [SnackbarHostState] guarantees to show at most one snackbar at a time. If this function is
	 * called while another snackbar is already visible, it will be suspended until this snackbar is
	 * shown and subsequently addressed. If the caller is cancelled, the snackbar will be removed
	 * from display and/or the queue to be displayed.
	 *
	 * All of this allows for granular control over the snackbar queue from within:
	 *
	 * @sample androidx.compose.material3.samples.ScaffoldWithCustomSnackbar
	 *
	 * @param visuals [SnackbarVisuals] that are used to create a Snackbar
	 *
	 * @return [SnackbarResult.ActionPerformed] if option action has been clicked or
	 * [SnackbarResult.Dismissed] if snackbar has been dismissed via timeout or by the user
	 */
	suspend fun showSnackbar(visuals: SnackbarVisuals): SnackbarResult = snackbarHostState.showSnackbar(visuals)

	fun performAction() = currentSnackbarData?.performAction()

	fun dismiss() = currentSnackbarData?.dismiss()

}

val LocalSnackbarControllerState: ProvidableCompositionLocal<SnackbarControllerState> = compositionLocalOf {
	SnackbarControllerState(SnackbarHostState())
}
