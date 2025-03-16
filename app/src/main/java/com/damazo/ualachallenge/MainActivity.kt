package com.damazo.ualachallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.damazo.featurecities.ui.screen.CitiesFilterScreen
import com.damazo.ualachallenge.ui.theme.UalaChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalMaterial3Api
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            UalaChallengeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CitiesFilterScreen(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}