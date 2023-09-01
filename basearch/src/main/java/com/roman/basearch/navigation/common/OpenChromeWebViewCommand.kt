package com.roman.basearch.navigation.common

import android.content.ActivityNotFoundException
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import com.roman.basearch.navigation.Command
import com.roman.basearch.view.BaseActivity


class OpenChromeWebViewCommand(
    val url: String,
    val error: (e: ActivityNotFoundException) -> Unit
) : Command {


    override fun execute(context: BaseActivity<*, *>) {

        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()

        try {
            val url = Uri.parse(url)
            customTabsIntent.launchUrl(context, url)
        } catch (e: ActivityNotFoundException) {
            error(e)
        }

    }

}
