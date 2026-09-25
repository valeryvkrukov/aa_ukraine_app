package org.aa.ukraine.core.network

import org.aa.ukraine.core.network.model.NetworkMeeting
import org.aa.ukraine.core.network.model.NetworkMeetingType
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

class JsoupParser {
    /**
     * Downloads the schedule from the website
     * and fully structures it into a list of objects
     */
    fun fetchMeetings(url: String = "https://aa.org.ua/groups/"): List<NetworkMeeting> {
        val meetings = mutableListOf<NetworkMeeting>()
        try {
            val document = Jsoup.connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36")
                .timeout(10000)
                .get()

            // ЖЕЛЕЗНЫЙ ВЫБОР: Находим абсолютно все h3 и h4 на странице в правильном порядке
            val headings = document.select("h3, h4")
            var currentCity = "Не визначено"

            for (heading in headings) {
                // 1. Если нашли h3 — переключаем текущую область/город
                if (heading.tagName() == "h3") {
                    val cityText = heading.text().trim()
                    if (cityText.isNotEmpty() && !cityText.contains("Оберiть", ignoreCase = true)) {
                        currentCity = cityText.replace(" область", "")
                    }
                    continue
                }

                // 2. Если нашли h4 — это начало карточки группы АА
                if (heading.tagName() == "h4") {
                    val title = heading.text().trim()
                    if (title.isEmpty() || title.contains("телефон", ignoreCase = true)) continue

                    var address: String? = null
                    var additionalInfo = ""
                    val daysOfWeek = mutableListOf<Int>()
                    var meetingTime = ""
                    var link: String? = null

                    // Ищем текстовые данные, идущие СЛЕДОМ за заголовком h4 на том же уровне DOM дерева
                    var nextSibling: Element? = heading.nextElementSibling()

                    // Если Elementor обернул h4 в свой div, выходим на уровень контейнера виджета
                    if (nextSibling == null && heading.parent()?.tagName() == "div") {
                        nextSibling = heading.parent()?.nextElementSibling()
                    }

                    // Сканируем элементы ниже, пока не наткнемся на следующий h4 или h3
                    while (nextSibling != null) {
                        if (nextSibling.tagName() == "h4" || nextSibling.tagName() == "h3" || nextSibling.select("h4, h3").isNotEmpty()) {
                            break // Началась следующая группа
                        }

                        val text = nextSibling.text().trim()
                        val dayMatch = parseDayOfWeek(text)

                        if (dayMatch != null) {
                            daysOfWeek.add(dayMatch)
                            if (meetingTime.isEmpty()) {
                                meetingTime = text.substringAfter("–").substringAfter("-").trim()
                            }
                        } else if (text.contains("вул.", ignoreCase = true) || text.contains("м.", ignoreCase = true) || text.contains("просп.", ignoreCase = true)) {
                            address = text.substringBefore("МАПА").trim()
                        } else if (text.isNotEmpty() && !text.contains("Створена", ignoreCase = true)) {
                            additionalInfo += (if (additionalInfo.isNotEmpty()) "\n" else "") + text
                        }

                        // Извлекаем Zoom / Telegram ссылки
                        val anchor = nextSibling.select("a").first()
                        if (anchor != null) {
                            val href = anchor.absUrl("href")
                            if (href.contains("zoom", ignoreCase = true) || href.contains("t.me", ignoreCase = true)) {
                                link = href
                            }
                        }

                        nextSibling = nextSibling.nextElementSibling()
                    }

                    if (daysOfWeek.isEmpty()) daysOfWeek.add(1)
                    if (meetingTime.isEmpty()) meetingTime = "18:00"

                    meetings.add(
                        NetworkMeeting(
                            title = title.replace("“", "").replace("”", ""),
                            time = meetingTime,
                            daysOfWeek = daysOfWeek,
                            type = if (link != null && (link.contains("zoom") || link.contains("t.me"))) NetworkMeetingType.ONLINE else NetworkMeetingType.OFFLINE,
                            city = currentCity,
                            address = address,
                            link = link,
                            additionalInfo = additionalInfo.ifEmpty { null }
                        )
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return meetings
    }

    private fun parseDayOfWeek(text: String): Int? {
        val cleanText = text.lowercase()
        return when {
            cleanText.startsWith("пн") -> 1
            cleanText.startsWith("вт") -> 2
            cleanText.startsWith("ср") -> 3
            cleanText.startsWith("чт") -> 4
            cleanText.startsWith("пт") -> 5
            cleanText.startsWith("сб") -> 6
            cleanText.startsWith("нд") -> 7
            cleanText.startsWith("щоденно") -> 1
            else -> null
        }
    }

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
