package com.example.tic_tac_toe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                TicTacToeGame()
        }
    }
}

@Composable
fun TicTacToeGame() {
    var board by remember { mutableStateOf(List(3) { List(3) { "" } }) }
    var currentPlayer by remember { mutableStateOf("X") }
    var gameState by remember { mutableStateOf("Playing") }

    fun checkWin(board: List<List<String>>): String {

        for (i in 0..2) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != "") return board[i][0]
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != "") return board[0][i]
        }

        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != "") return board[0][0]
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != "") return board[0][2]

       
        if (board.all { row -> row.all { cell -> cell != "" } }) return "Draw"

        return "Playing"
    }

    fun resetGame() {
        board = List(3) { List(3) { "" } }
        currentPlayer = "X"
        gameState = "Playing"
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF8E1))
    ) {
        Text(
            text = "Tic Tac Toe",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Serif,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
            color = Color(0xFFFFA000)
        )

        Text(
            text = when (gameState) {
                "X" -> "Player X wins!"
                "O" -> "Player O wins!"
                "Draw" -> "It's a draw!"
                else -> "Player $currentPlayer's turn"
            },
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(16.dp),
            textAlign = TextAlign.Center,
            color = Color(0xFFFF6F00)
        )

        Spacer(modifier = Modifier.height(16.dp))

        for (i in 0..2) {
            Row {
                for (j in 0..2) {
                    Box(
                        modifier = Modifier
                            .size(100.dp)
                            .background(Color(0xFFFFE082))
                            .clip(RoundedCornerShape(50.dp))
                            .clickable(enabled = board[i][j] == "" && gameState == "Playing") {
                                if (board[i][j] == "") {
                                    board = board
                                        .toMutableList()
                                        .apply {
                                            this[i] = this[i]
                                                .toMutableList()
                                                .apply {
                                                    this[j] = currentPlayer
                                                }
                                        }
                                    gameState = checkWin(board)
                                    if (gameState == "Playing") {
                                        currentPlayer = if (currentPlayer == "X") "O" else "X"
                                    }
                                }
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(text = board[i][j], fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFF6F00))
                    }
                    if (j < 2) {  // Add vertical grid line if not the last column
                        Spacer(modifier = Modifier
                            .width(4.dp)
                            .background(Color.Black))
                    }
                }
            }
            if (i < 2) {  // Add horizontal grid line if not the last row
                Spacer(modifier = Modifier
                    .height(4.dp)
                    .background(Color.Black))
            }
                }
        if (gameState != "Playing") {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                colors = ButtonDefaults.buttonColors(colorResource(id = R.color.myColor) ),
                onClick = { resetGame() }
            )
            {
                Text("Play Again", color = Color.White)
            }
        }


    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
        TicTacToeGame()
}
