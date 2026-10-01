package org.aa.ukraine.core.network

import android.util.Log
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GoogleGenerativeAIException
import com.google.ai.client.generativeai.type.ServerException
import com.google.ai.client.generativeai.type.generationConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.aa.ukraine.core.network.model.NetworkReflection
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.milliseconds

@Singleton
class AiReflectionClient @Inject constructor() {
    private val json = Json { ignoreUnknownKeys = true }

    /**
     * Requests a generative AI response based on Gemini using Google Generative AI SDK.
     * Automatically adapts the language of the request and response (uk, en, ru).
     */
    suspend fun fetchDailyReflection(date: String, langCode: String): NetworkReflection = withContext(
        Dispatchers.IO
    ) {
        try {
            val langPrompt = when {
                langCode.lowercase().startsWith("uk") -> "Ukrainian language (use the official Ukrainian translation of the 'Daily Reflections' book)"
                langCode.lowercase().startsWith("ru") -> "Russian language (use the official Russian translation of the 'Daily Reflections' book)"
                else -> "English language (original 'Daily Reflections' text)"
            }

            val config = generationConfig {
                responseMimeType = "application/json"
            }

            val generativeModel = GenerativeModel(
                modelName = "gemini-3.6-flash",
                apiKey = BuildConfig.GEMINI_API_KEY,
                generationConfig = config
            )

            val prompt = """
                You are an Alcoholics Anonymous (AA) Daily Reflections assistant.
                Generate the daily reflection for date: "$date" in language: "$langPrompt".

                Output ONLY a JSON object matching this schema:
                {
                  "title": "Topic Title in specified language",
                  "quote": "Quote from AA literature for this date in specified language",
                  "quoteSource": "Source of quote in specified language (e.g. Alcoholics Anonymous / Big Book)",
                  "commentary": "String (The exact, word-for-word body text of the reflection written by the AA member for this calendar day. Do not modify the text)"
                }
            """.trimIndent()

            val response = retryWithBackoff(retries = 3) {
                generativeModel.generateContent(prompt)
            }

            val jsonText = response.text ?: throw IllegalStateException("The AI returned an empty response")

            // Clean Markdown code blocks if present
            val cleanedJson = jsonText.trim()
                .removePrefix("```json")
                .removePrefix("```")
                .removeSuffix("```")
                .trim()

            json.decodeFromString<NetworkReflection>(cleanedJson)
        } catch (e: Exception) {
            Log.w("fetchDailyReflection", e.localizedMessage ?: "Unknown error")
            getFallbackReflection(langCode)
        }
    }

    private suspend fun <T> retryWithBackoff(
        retries: Int = 3,
        initialDelayMillis: Long = 1000,
        maxDelayMillis: Long = 8000,
        factor: Double = 2.0,
        block: suspend () -> T
    ): T {
        var currentDelay = initialDelayMillis
        repeat(retries - 1) { attempt ->
            try {
                return block()
            } catch (e: ServerException) {
                Log.w("AiReflectionClient", "Server error on attempt ${attempt + 1}: ${e.localizedMessage}. Retrying in ${currentDelay}ms...")
                delay(currentDelay.milliseconds)
                currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelayMillis)
            } catch (e: GoogleGenerativeAIException) {
                Log.w("AiReflectionClient", "Generative AI error on attempt ${attempt + 1}: ${e.localizedMessage}. Retrying in ${currentDelay}ms...")
                delay(currentDelay.milliseconds)
                currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelayMillis)
            } catch (e: IOException) {
                Log.w("AiReflectionClient", "Network error on attempt ${attempt + 1}: ${e.localizedMessage}. Retrying in ${currentDelay}ms...")
                delay(currentDelay.milliseconds)
                currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelayMillis)
            } catch (e: Exception) {
                if (e.localizedMessage?.contains("high demand", ignoreCase = true) == true ||
                    e.localizedMessage?.contains("unavailable", ignoreCase = true) == true ||
                    e.localizedMessage?.contains("rate limit", ignoreCase = true) == true) {
                    Log.w("AiReflectionClient", "Transient error on attempt ${attempt + 1}: ${e.localizedMessage}. Retrying in ${currentDelay}ms...")
                    delay(currentDelay.milliseconds)
                    currentDelay = (currentDelay * factor).toLong().coerceAtMost(maxDelayMillis)
                } else {
                    throw e
                }
            }
        }
        return block()
    }

    private fun getFallbackReflection(lang: String): NetworkReflection {
        return when {
            lang.lowercase().startsWith("uk") -> NetworkReflection(
                title = "Здоровий глузд",
                quote = "Ми виявили, що Бог не висуває надто жорстких вимог до тих, хто Його шукає.",
                quoteSource = "Великий Довідник АА, с. 46",
                commentary = "Сьогодні моє завдання — просто залишатися тверезим і довіряти Богу надію цього дня."
            )
            lang.lowercase().startsWith("ru") -> NetworkReflection(
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
