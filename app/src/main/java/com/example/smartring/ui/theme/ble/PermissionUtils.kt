package com.example.smartring.ble

import android.app.Activity
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

object PermissionUtils {
    private val requiredPermissions = mutableListOf(
        android.Manifest.permission.BLUETOOTH,
        android.Manifest.permission.BLUETOOTH_ADMIN,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    ).apply {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            add(android.Manifest.permission.BLUETOOTH_CONNECT)
            add(android.Manifest.permission.BLUETOOTH_SCAN)
        }
    }.toTypedArray()

    // 권한 확인
    fun checkPermissions(activity: Activity): Boolean {
        return requiredPermissions.all {
            ContextCompat.checkSelfPermission(activity, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    // 런처를 사용한 권한 요청
    fun requestPermissionsWithLauncher(
        activity: Activity,
        launcher: ActivityResultLauncher<Array<String>>
    ) {
        launcher.launch(requiredPermissions)
    }
}
