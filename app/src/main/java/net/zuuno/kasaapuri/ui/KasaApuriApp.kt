package net.zuuno.kasaapuri.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import net.zuuno.kasaapuri.navigation.AppNavHost
import net.zuuno.kasaapuri.ui.theme.KasaApuriTheme

@Composable
fun KasaApuriApp() {
    val navController = rememberNavController()

    KasaApuriTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            AppNavHost(navController)
        }
    }
}