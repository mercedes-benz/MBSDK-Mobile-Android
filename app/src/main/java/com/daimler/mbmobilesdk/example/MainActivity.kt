package com.daimler.mbmobilesdk.example

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.daimler.mbmobilesdk.app.MBMobileSDK
import com.daimler.mbmobilesdk.featuretoggling.OnFeatureChangedListener
import com.daimler.mbmobilesdk.jumio.identitycheckhint.IdentityCheckHintActivity.Companion.getIdentityCheckIntent
import com.daimler.mbmobilesdk.logic.UserTask
import com.daimler.mbmobilesdk.notificationcenter.presentation.InboxActivity
import com.daimler.mbmobilesdk.tou.AgreementsActivity
import com.daimler.mbmobilesdk.utils.extensions.isLoggedIn
import com.daimler.mbmobilesdk.vehiclestage.VehicleStageActivity
import com.daimler.mbloggerkit.MBLoggerKit
import com.daimler.mbloggerkit.export.shareAsFile
import com.daimler.mbloggerkit.export.shareAsText
import com.daimler.mbingresskit.MBIngressKit
import com.daimler.mbingresskit.common.UserCredentials
import com.daimler.mbingresskit.login.LoginFailure
import com.daimler.mbcarkit.MBCarKit
import com.daimler.mbcarkit.business.model.command.CommandRequest
import com.daimler.mbnetworkkit.socket.ConnectionState
import com.daimler.mbnetworkkit.socket.SocketConnectionListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), OnFeatureChangedListener, SocketConnectionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_jumio.setOnClickListener {
            startActivity(getIdentityCheckIntent())
        }

        btn_show_login_ui.setOnClickListener {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }
        btn_login.setOnClickListener {
            Toast.makeText(this, "Not supported yet", Toast.LENGTH_SHORT).show()
        }
        btn_login_native.setOnClickListener {
            showPendingLogin()
            // For testing, simply replace usercredentials here
            MBIngressKit.loginWithCredentials(UserCredentials("alanturing", "Test1234$"))
                .onComplete {
                    showLoggedIn()
                }.onFailure {
                    showLoggedOut()
                    val msg = if (it?.requestError is LoginFailure) {
                        "FAILED: ${(it.requestError as? LoginFailure)?.name}"
                    } else {
                        "FAILED: ${it?.requestError}"
                    }
                    Toast.makeText(this@MainActivity, msg, Toast.LENGTH_LONG).show()
                    MBLoggerKit.e(msg)
                }
        }
        btn_logout.setOnClickListener {
            showPendingLogout()
            MBIngressKit.logout()
                .onAlways { _, _, _ ->
                    MBCarKit.disconnectFromWebSocket()
                    MBCarKit.clearLocalCache()
                    MBIngressKit.clearLocalCache()

                    showLoggedOut()
                }
        }
        btn_get_user.setOnClickListener {
            val token = MBIngressKit.authenticationService().getToken().jwtToken
            MBIngressKit.userService().loadUser(token.plainToken)
                .onComplete {
                    Toast.makeText(this@MainActivity, "$it", Toast.LENGTH_LONG).show()
                }.onFailure {
                    Toast.makeText(this@MainActivity, "UNKNOWN", Toast.LENGTH_LONG).show()
                }
        }
        btn_vehicle_assignment.setOnClickListener {
            UserTask().fetchUser().onComplete {
                startActivityForResult(VehicleStageActivity.getStartIntent(this, null),
                    REQ_CODE_ASSIGNMENT)
            }
        }

        UserTask().fetchUser()
        MBIngressKit.refreshTokenIfRequired()
            .onComplete {
                MBCarKit.connectToWebSocket(it.jwtToken.plainToken, this)
            }.onFailure {
                MBLoggerKit.e("Could not refresh token.", throwable = it)
            }

        btn_fetch_soe.setOnClickListener {
            startActivity(AgreementsActivity.getStartIntent(this, AgreementsActivity.Type.SOE))
        }

        btn_clear_caches.setOnClickListener {
            MBCarKit.disconnectFromWebSocket()
            MBCarKit.clearLocalCache()
            MBIngressKit.clearLocalCache()
        }

        btn_inbox.setOnClickListener {
            InboxActivity.start(this)
        }

        MBMobileSDK.featureToggleService().registerFeatureListener("ris-sdk-soe-terms-of-use", this)
    }

    override fun connectionStateChanged(connectionState: ConnectionState) {
        MBLoggerKit.d("connectionState = $connectionState")
    }

    override fun onFeatureChanged(key: String) {
        MBLoggerKit.d("CHANGED $key to ${MBMobileSDK.featureToggleService().isToggleEnabled("ris-sdk-soe-terms-of-use")}")
    }

    override fun onResume() {
        super.onResume()
        if (MBIngressKit.authenticationService().isLoggedIn()) {
            showLoggedIn()
        } else {
            showLoggedOut()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        MBMobileSDK.featureToggleService().unregisterFeatureListener("ris-sdk-soe-terms-of-use", this)
        MBCarKit.unregisterFromSocket(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.refresh_token -> refreshToken()
            R.id.force_token_refresh -> MBIngressKit.authenticationService().forceTokenRefresh()
            R.id.share_log_text -> MBLoggerKit.loadCurrentLog().shareAsText(this)
            R.id.share_log_file -> MBLoggerKit.loadCurrentLog().shareAsFile(this, getString(R.string.log_export_filename))
            R.id.pin -> sendPinCommand()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            REQ_CODE_ASSIGNMENT ->
                MBLoggerKit.d("resultCode = $resultCode, flags = ${data?.getIntExtra(VehicleStageActivity.ARG_STAGE_RESULT, 0)}")
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun refreshToken() {
        MBIngressKit.refreshTokenIfRequired().onComplete {
            Toast.makeText(this, "Refresh Success: ${it.accessToken}", Toast.LENGTH_LONG).show()
        }.onFailure {
            Toast.makeText(this, "Refresh Failed: ${it?.message
                ?: "Unknown"}", Toast.LENGTH_LONG).show()
        }
    }

    private fun sendPinCommand() {
        MBCarKit.sendCarCommand(CommandRequest.WindowsOpen("")) {
            MBLoggerKit.d("$it")
        }
    }

    private fun showPendingLogin() {
        btn_login.visibility = View.GONE
        btn_login_native.visibility = View.GONE
        tv_login_started.visibility = View.VISIBLE
        tv_logout_started.visibility = View.GONE
        btn_logout.visibility = View.GONE
    }

    private fun showLoggedIn() {
        btn_login.visibility = View.GONE
        btn_login_native.visibility = View.GONE
        tv_login_started.visibility = View.GONE
        btn_logout.visibility = View.VISIBLE
        tv_logout_started.visibility = View.GONE
        btn_get_user.visibility = View.VISIBLE
    }

    private fun showLoggedOut() {
        btn_login_native.visibility = View.VISIBLE
        btn_login.visibility = View.VISIBLE
        tv_login_started.visibility = View.GONE
        btn_logout.visibility = View.GONE
        tv_logout_started.visibility = View.GONE
        btn_get_user.visibility = View.GONE
    }

    private fun showPendingLogout() {
        btn_login_native.visibility = View.GONE
        btn_login.visibility = View.GONE
        tv_login_started.visibility = View.GONE
        btn_logout.visibility = View.GONE
        tv_logout_started.visibility = View.VISIBLE
    }

    companion object {
        private const val REQ_CODE_ASSIGNMENT = 11
        private const val REQ_CODE_ONBOARDING = 12
    }
}
