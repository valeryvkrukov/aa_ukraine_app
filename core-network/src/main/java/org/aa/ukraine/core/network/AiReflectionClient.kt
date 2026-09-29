package org.aa.ukraine.core.network

import com.google.firebase.Firebase
import com.google.firebase.ai.ai
import com.google.firebase.ai.type.PublicPreviewAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import org.aa.ukraine.core.network.model.NetworkReflection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AiReflectionClient @Inject constructor() {
    private val json = Json { ignoreUnknownKeys = true }

    /**
     * Requests a generative AI response based on Gemini using Firebase AI SDK.
     * Automatically adapts the language of the request and response (uk, en, ru).
     */
    @OptIn(InternalSerializationApi::class, PublicPreviewAPI::class)
    suspend fun fetchDailyReflection(date: String, langCode: String): NetworkReflection = withContext(
        Dispatchers.IO
    ) {
        try {
            val generativeModel = Firebase.ai.templateGenerativeModel()

            val response = generativeModel.generateContent(
                templateId = "daily-reflections-v1",
                inputs = mapOf(
                    "date" to date,
                    "language" to when (langCode.lowercase()) {
                        "uk" -> "Ukrainian"
                        "ru" -> "Russian"
                        else -> "English"
                    },
                    "maxLength" to "250"
                )
            )

            val jsonText = response.text ?: throw IllegalStateException("The AI returned an empty response")

            // Clean markdown code blocks if present
            val cleanedJson = jsonText.trim()
                .removePrefix("```json")
                .removePrefix("```")
                .removeSuffix("```")
                .trim()

            json.decodeFromString<NetworkReflection>(cleanedJson)
        } catch (e: Exception) {
            getFallbackReflection(langCode)
        }
    }

    @OptIn(InternalSerializationApi::class)
    private fun getFallbackReflection(lang: String): NetworkReflection {
        return when (lang) {
            "uk" -> NetworkReflection(
                title = "Здоровий глузд",
                quote = "Ми виявили, що Бог не висуває надто жорстких вимог до тих, хто Його шукає.",
                quoteSource = "Великий Довідник АА, с. 46",
                commentary = "Сьогодні моє завдання — просто залишатися тверезим і довіряти Богу надію цього дня."
            )
            "ru" -> NetworkReflection(
                title = "Здравомыслие",
                quote = "Мы обнаружили, что Бог не предъявляет чересчур жестких требований к тем, кто Его ищет.",
                quoteSource = "Большая Книга АА, с. 46",
                commentary = "Сегодня моя задача — просто оставаться трезвым и доверять Богу этот день."
            )
            else -> NetworkReflection(
                title = "Sanity",
                quote = "We found that God does not make too hard terms with those who seek Him.",
                quoteSource = "Alcoholics Anonymous, p. 46",
                commentary = "Today my only job is to stay clean and sober, trusting the process."
            )
        }
    }
}
