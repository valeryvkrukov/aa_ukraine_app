package org.aa.ukraine.core.network

import org.jsoup.Jsoup

class JsoupParser {
    /**
     * Downloads the raw HTML code of the page from the provided URL.
     * Returns a string containing code for a basic connectivity check.
     */
    fun fetchRawHtml(url: String): String {
        return try {
            val document = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .timeout(6000)
                .get()

            document.html()
        } catch (e: Exception) {
            "ERROR: ${e.localizedMessage}"
        }
    }
}
