package org.aa.ukraine.core.network

import org.aa.ukraine.core.network.model.NetworkMeetingType
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
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

        println("=== GROUPS PAGE TEST ===")
        assertFalse(html.startsWith("ERROR"))

        // Checking whether Jsoup detects the structure of regions (h3) and groups (h4)
        assertTrue(html.contains("<h3", ignoreCase = true))
        assertTrue(html.contains("<h4", ignoreCase = true))

        println("Success! Number of h4 tags on the page: ${html.split("<h4").size - 1}")
    }

    @Test
    fun test_fetchMeetings_shouldReturnValidParsedObjects() {
        val meetings = parser.fetchMeetings()

        println("=== DEEP MODEL PARSE TEST ===")
        // 1. Check that the list of groups is not empty at all
        assertTrue("The list of parsed groups is empty!", meetings.isNotEmpty())
        println("Total successfully parsed: ${meetings.size} groups.")

        // 2. Detailed log for the first group
        val firstGroup = meetings.first()
        println("\n--- The first group on the list ---")
        println("Title: ${firstGroup.title}")
        println("City/Region: ${firstGroup.city}")
        println("Time: ${firstGroup.time}")
        println("Type: ${firstGroup.type}")
        println("Address: ${firstGroup.address}")

        // Basic assertions for the first group
        assertFalse("Group name is empty", firstGroup.title.isBlank())
        assertNotNull("City/Region not specified", firstGroup.city)
        assertTrue("The days of the week array is empty", firstGroup.daysOfWeek.isNotEmpty())

        // 3. Scan the ENTIRE list for type validity and the presence of ONLINE groups
        var onlineCount = 0
        var offlineCount = 0

        for (meeting in meetings) {
            // Verify that the name and time for each group have a reasonable length
            assertTrue("Empty group name found", meeting.title.isNotBlank())
            assertTrue("An open meeting slot was found for the group ${meeting.title}", meeting.time.isNotBlank())

            // Counting types for statistics
            when (meeting.type) {
                NetworkMeetingType.ONLINE -> {
                    onlineCount++
                    // If the meeting is online, it must include a Zoom or Telegram link
                    assertNotNull("For the online group '${meeting.title}' missing link!", meeting.link)
                    assertTrue("The online group link is incorrect", meeting.link!!.contains("zoom") || meeting.link.contains("t.me"))
                }
                NetworkMeetingType.OFFLINE -> {
                    offlineCount++
                }
            }
        }

        println("\n--- Field validation statistics ---")
        println("Active (offline) groups found: $offlineCount")
        println("Remote (online) groups found: $onlineCount")

        // Check that the parser was able to find
        // at least one online group (they definitely exist on the site)
        assertTrue("The parser failed to find a single ONLINE group with a link!", onlineCount > 0)
        println("All data contracts have been successfully verified!")
    }
}
