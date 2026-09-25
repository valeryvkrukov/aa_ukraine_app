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

    @Test
    fun test_fetchMeetings_shouldReturnValidParsedObjects() {
        val meetings = parser.fetchMeetings()

        println("=== ГЛУБОКИЙ ТЕСТ ПАРСИНГА МОДЕЛЕЙ ===")
        assertTrue(meetings.isNotEmpty())

        // Let's take the first group we come across for a detailed log
        val sample = meetings.first()
        println("The first group was successfully parsed:")
        println("Title: ${sample.title}")
        println("City/Region: ${sample.city}")
        println("Time: ${sample.time}")
        println("Days of the week (dates): ${sample.daysOfWeek}")
        println("Meeting type: ${sample.type}")
        println("Address: ${sample.address}")
        println("Link: ${sample.link}")

        // Basic data contract checks
        assertFalse(sample.title.isEmpty())
        assertTrue(sample.daysOfWeek.isNotEmpty())
    }
}
