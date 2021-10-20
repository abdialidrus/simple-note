package id.co.abdialidrus.simplenote.presentation

interface UICommunicationListener {

    fun displayProgressBar(isLoading: Boolean)

    fun expandAppBar()

    fun hideSoftKeyboard()

    //fun isStoragePermissionGranted(): Boolean
}