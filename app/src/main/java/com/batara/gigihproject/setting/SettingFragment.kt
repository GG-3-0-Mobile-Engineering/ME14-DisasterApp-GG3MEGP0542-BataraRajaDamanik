package com.batara.gigihproject.setting

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import com.batara.gigihproject.R
import com.batara.gigihproject.SettingPreferences
import com.batara.gigihproject.databinding.FragmentSettingBinding
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SettingFragment : Fragment() {

    private var _binding : FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private val settingViewModel: SettingViewModel by viewModel { parametersOf(get<SettingPreferences>()) }

    private val notificationId = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        settingViewModel.getThemeSettings().observe(viewLifecycleOwner) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                binding.switchTheme.isChecked = true
            } else {
                binding.switchTheme.isChecked = false
            }
        }

        with(binding){
            switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
                settingViewModel.saveThemeSetting(isChecked)
            }

            btnNotification.setOnClickListener{
                showNotification()
            }
        }
    }

    private fun showNotification() {
        val channelId = "channelId"

        val notificationBuilder = NotificationCompat.Builder(requireContext(), channelId)
            .setSmallIcon(R.drawable.ic_launcher_background) // Replace with your notification icon
            .setContentTitle("Waspada Ketinggian Air")
            .setContentText("Status : SIAGA 4")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true) // Removes the notification when tapped


        val permission = Manifest.permission.POST_NOTIFICATIONS
        val notificationManager = NotificationManagerCompat.from(requireContext())
        if (activity?.applicationContext?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    permission
                )
            } != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission not granted, request it
            requestNotificationPermission()
        } else {
            notificationManager.notify(notificationId, notificationBuilder.build())
        }
    }

    private fun requestNotificationPermission() {
        val permission = Manifest.permission.POST_NOTIFICATIONS

        activity?.let {
            ActivityCompat.requestPermissions(
                it,
                arrayOf(permission),
                notificationId
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            notificationId -> {
                // Check if the permission is granted
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, proceed with showing the notification
                    showNotification()
                } else {
                    // Permission denied, handle the case where the user denies the permission
                    // You can show a message to the user or disable functionality that requires this permission.
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}