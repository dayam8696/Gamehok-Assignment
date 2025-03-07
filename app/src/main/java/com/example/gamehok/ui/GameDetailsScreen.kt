import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gamehok.R


@Composable
fun GameDetailsScreen(navController: NavController) {
    val game = mapOf(
        "name" to "Valorant",
        "genre" to "Shooter",
        "description" to "A tactical shooter game by Riot Games."
    )

    val tournamentList = listOf(
        mapOf("title" to "Valorant Championship", "date" to "March 15, 2025"),
        mapOf("title" to "Pro League", "date" to "April 20, 2025"),
        mapOf("title" to "Community Cup", "date" to "May 10, 2025")
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Set background to black
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = R.drawable.tournament_icon), // Use drawable image
            contentDescription = "Game Banner",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)

        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = game["name"] ?: "", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)

        Text(text = game["genre"] ?: "", fontSize = 16.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = game["description"] ?: "", fontSize = 14.sp, color = Color.White)

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Handle Play/Join */ },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(Color.Green)
        ) {
            Text(text = "PLAY NOW", color = Color.Black, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Upcoming Tournaments", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)

        LazyColumn {
            items(tournamentList) { tournament ->
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    Text(text = tournament["title"] ?: "", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
                    Text(text = tournament["date"] ?: "", fontSize = 14.sp, color = Color.Gray)
                }
            }
        }
    }
}
