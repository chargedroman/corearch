package com.r.immoscoutpuller.repository.impl

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Bitmap
import android.os.Handler
import android.os.Looper
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.lifecycle.MutableLiveData
import com.r.immoscoutpuller.R
import com.roman.basearch.utility.LocalRepository
import com.roman.basearch.utility.TextLocalization
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withLock
import org.apache.commons.text.StringEscapeUtils
import org.koin.core.KoinComponent
import org.koin.core.inject

/**
 *
 * Author: romanvysotsky
 * Created: 09.03.23
 */

class FakeBrowser: KoinComponent {


    private val mainThread = Handler(Looper.getMainLooper())
    private val textLocalization: TextLocalization by inject()
    private val localRepository: LocalRepository by inject()
    private val application: Application by inject()
    private val mutex = Mutex()

    private var webView: WebView? = null

    val captchaRequired: MutableLiveData<WebView?> = MutableLiveData(null)


    var pageNotFullyLoaded: (rawHtml: String) -> Boolean = { rawHtml ->
        rawHtml.contains("https://www.static-immobilienscout24.de/fro/imperva/0.0.1/robot-logo.svg")
    }

    var isCaptcha: (rawHtml: String) -> Boolean = { rawHtml ->
        rawHtml.contains("id=\"captcha-box\"")
    }


    /**
     * loads the page behind [url]
     * @return raw html of the page
     */
    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    suspend fun loadPage(url: String): String {

        val result = mutex.withLock {
            val callback = createCallback()

            mainThread.post {
                val webView: WebView = getOrCreateWebView()
                webView.setCustomUserAgentIfSpecified()
                webView.setDataCallback(callback)
                webView.loadUrl(url)
            }

            callback.getData()
        }

        return result
    }


    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface")
    private fun WebView.setDataCallback(callback: DataCallback) {
        val webView = this
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object: WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                webView.evaluateJavascript("(function(){return unescape(window.document.body.outerHTML)})();") { data ->
                    val rawHtml = StringEscapeUtils.unescapeJava(data)
                    if(pageNotFullyLoaded(rawHtml).not()) {
                        captchaRequired.postValue(null)
                        callback.putData(rawHtml)
                    }
                    if(isCaptcha(rawHtml)) {
                        captchaRequired.postValue(webView)
                    }
                }
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }
        }
    }


    private suspend fun createCallback(): DataCallback {
        val semaphore = Semaphore(1)
        semaphore.acquire()
        return DataCallback(semaphore)
    }

    private class DataCallback(private val semaphore: Semaphore) {
        private var data: String? = null

        fun putData(data: String) {
            this.data = data
            semaphore.release()
        }

        suspend fun getData(): String {
            semaphore.acquire()
            return data!!
        }
    }


    private fun getOrCreateWebView(): WebView {
        val currentWebView = this.webView
        if(currentWebView != null) {
            return currentWebView
        }

        val webView = WebView(application)
        webView.id = View.generateViewId()
        webView.onResume()
        this.webView = webView
        return webView
    }

    private fun WebView.setCustomUserAgentIfSpecified() {
        val key = textLocalization.getString(R.string.custom_user_agent)
        val userAgent = localRepository.retrieve(key)

        if(userAgent.isNullOrBlank().not() && userAgent != key) {
            this.settings.userAgentString = userAgent
        }
    }


}
