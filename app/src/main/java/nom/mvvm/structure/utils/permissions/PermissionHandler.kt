package nom.mvvm.structure.utils.permissions

interface PermissionHandler {
    fun onGranted()
    fun onDenied()
}