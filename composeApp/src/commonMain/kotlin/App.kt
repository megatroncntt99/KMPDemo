import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import model.BirdImage
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

const val  URL_API ="https://sebastianaigner.github.io/demo-image-api/"
@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
        var showContent by remember { mutableStateOf(false) }
        val greeting = remember { Greeting().greet() }
        var image by remember { mutableStateOf<String?>(null) }
        LaunchedEffect(Unit){
            val images = getBridImages()
            if (images.isNotEmpty()){
                image = URL_API + images[0].path
            }
        }
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me12345!")
            }
            AnimatedVisibility(showContent) {
                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(painterResource("compose-multiplatform.xml"), null)
                    Text("Compose: $greeting")
                    image?.let {
                        KamelImage(asyncPainterResource(it),null,
                                   modifier = Modifier.size(200.dp))
                    }
                       }
            }
        }
    }
}
val httpClient = HttpClient {
    install(ContentNegotiation){
        json()
    }
}
suspend fun getBridImages():List<BirdImage> {
    val  images = httpClient.get(URL_API+ "pictures.json").body<List<BirdImage>>()
    return images
}