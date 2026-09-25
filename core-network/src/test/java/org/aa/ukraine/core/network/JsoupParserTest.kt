package org.aa.ukraine.core.network

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class JsoupParserTest {
    private val parser = JsoupParser()

    @Test
    fun test_fetchMainPage_shouldContainElementorClasses() {
        val url = "https://aa.org.ua"
        val html = parser.fetchRawHtml(url)

        println("=== HOMEPAGE TEST ===")
        assertFalse(html.startsWith("ERROR"))
        assertTrue(html.contains("elementor", ignoreCase = true))
        println("Success! Length of downloaded HTML: ${html.length} characters.")
    }

    @Test
    fun test_fetchGroupsPage_shouldContainRegionsAndGroups() {
        val url = "https://aa.org.ua/groups/"
        val html = parser.fetchRawHtml(url)

        println("=== ТЕСТ СТРАНИЦЫ ГРУПП ===")
        assertFalse(html.startsWith("ERROR"))

        // Checking whether Jsoup detects the structure of regions (h3) and groups (h4)
        assertTrue(html.contains("<h3", ignoreCase = true))
        assertTrue(html.contains("<h4", ignoreCase = true))

        println("Success! Number of h4 tags on the page: ${html.split("<h4").size - 1}")
    }
}
