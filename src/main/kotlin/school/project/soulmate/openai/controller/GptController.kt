package school.project.soulmate.openai.controller

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.io.IOException

@RestController
class GptController(
    @Value("\${openai.api.key}") private val apiKey: String,
) {
    private val client = OkHttpClient()

    @PostMapping("/text-completion")
    fun completeText(
        @RequestBody userInput: UserInput,
    ): ResponseEntity<String> {
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val body =
            """
            {
                "model": "gpt-3.5-turbo-instruct",
                "prompt": "${userInput.prompt}",
                "max_tokens": 100
            }
            """.trimIndent().toRequestBody(mediaType)

        val request =
            Request.Builder()
                .url("https://api.openai.com/v1/completions")
                .addHeader("Authorization", "Bearer $apiKey")
                .post(body)
                .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            return ResponseEntity(response.body?.string(), HttpStatus.OK)
        }
    }
}

data class UserInput(val prompt: String)
