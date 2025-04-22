import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

// ==========================
// Ktor Client конфігурація
// ==========================
val client = HttpClient {
    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        })
    }
}

// ==========================
// Модель для JSONPlaceholder
// ==========================
@Serializable
data class LogPost(
    val userId: Int,
    val id: Int = 0,
    val title: String,
    val body: String
)

// ==========================
// Функція для відправки логу в API
// ==========================
suspend fun sendLog(title: String, body: String) {
    val log = LogPost(userId = 1, title = title, body = body)
    client.post("https://jsonplaceholder.typicode.com/posts") {
        contentType(ContentType.Application.Json)
        setBody(log)
    }
}