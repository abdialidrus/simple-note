package id.co.abdialidrus.simplenote.presentation.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import id.co.abdialidrus.simplenote.R
import id.co.abdialidrus.simplenote.business.domain.util.*

private val TAG: String = "AppDebug"

fun processQueue(
    context: Context?,
    queue: Queue<StateMessage>,
    stateMessageCallback: StateMessageCallback
) {
    context?.let { ctx ->
        if(!queue.isEmpty()){
            queue.peek()?.let { stateMessage ->
                ctx.onResponseReceived(
                    response = stateMessage.response,
                    stateMessageCallback = stateMessageCallback
                )
            }
        }
    }
}

private fun Context.onResponseReceived(
    response: Response,
    stateMessageCallback: StateMessageCallback
) {
    when(response.uiComponentType){

//        is UIComponentType.AreYouSureDialog -> {
//
//            response.message?.let {
//                areYouSureDialog(
//                    message = it,
//                    callback = response.uiComponentType.callback,
//                    stateMessageCallback = stateMessageCallback
//                )
//            }
//        }

        is UIComponentType.Toast -> {
            response.message?.let {
                displayToast(
                    message = it,
                    stateMessageCallback = stateMessageCallback
                )
            }
        }

        is UIComponentType.Dialog -> {
            displayDialog(
                response = response,
                stateMessageCallback = stateMessageCallback
            )
        }

        is UIComponentType.None -> {
            // This would be a good place to send to your Error Reporting
            // software of choice (ex: Firebase crash reporting)
            Log.i(TAG, "onResponseReceived: ${response.message}")
            stateMessageCallback.removeMessageFromStack()
        }
    }
}

fun Context.displayToast(
    message:String,
    stateMessageCallback: StateMessageCallback
){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    stateMessageCallback.removeMessageFromStack()
}

private fun Context.displayDialog(
    response: Response,
    stateMessageCallback: StateMessageCallback
){
    response.message?.let { message ->

        when (response.messageType) {

            is MessageType.Error -> {
                displayErrorDialog(
                    message = message,
                    stateMessageCallback = stateMessageCallback
                )
            }

            is MessageType.Success -> {
                displaySuccessDialog(
                    message = message,
                    stateMessageCallback = stateMessageCallback
                )
            }

            is MessageType.Info -> {
                displayInfoDialog(
                    message = message,
                    stateMessageCallback = stateMessageCallback
                )
            }

            else -> {
                // do nothing
                stateMessageCallback.removeMessageFromStack()
                null
            }
        }
    }?: stateMessageCallback.removeMessageFromStack()
}

private fun Context.displaySuccessDialog(
    message: String?,
    stateMessageCallback: StateMessageCallback
) {
    MaterialAlertDialogBuilder(this)
        .setTitle(resources.getString(R.string.text_success))
        .setMessage(message)
        .setPositiveButton(resources.getString(R.string.text_ok)) { dialog, which ->
            // Respond to positive button press
            stateMessageCallback.removeMessageFromStack()
        }
        .setCancelable(false)
        .show()
}

private fun Context.displayErrorDialog(
    message: String?,
    stateMessageCallback: StateMessageCallback
) {
    MaterialAlertDialogBuilder(this)
        .setTitle(resources.getString(R.string.text_error))
        .setMessage(message)
        .setPositiveButton(resources.getString(R.string.text_ok)) { dialog, which ->
            // Respond to positive button press
            stateMessageCallback.removeMessageFromStack()
        }
        .setCancelable(false)
        .show()
}

private fun Context.displayInfoDialog(
    message: String?,
    stateMessageCallback: StateMessageCallback
) {
    MaterialAlertDialogBuilder(this)
        .setTitle(resources.getString(R.string.text_info))
        .setMessage(message)
        .setPositiveButton(resources.getString(R.string.text_ok)) { dialog, which ->
            // Respond to positive button press
            stateMessageCallback.removeMessageFromStack()
        }
        .setCancelable(false)
        .show()
}
