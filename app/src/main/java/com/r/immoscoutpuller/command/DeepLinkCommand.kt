package com.r.immoscoutpuller.command

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import com.roman.basearch.navigation.Command
import com.roman.basearch.view.BaseActivity

/**
 *
 * Author: romanvysotsky
 * Created: 14.07.20
 */

class DeepLinkCommand(
    private val link: String,
    private val error: (e: ActivityNotFoundException) -> Unit
): Command {

    override fun execute(context: BaseActivity<*, *>) {

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))

        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            error(e)
        }
    }

}
