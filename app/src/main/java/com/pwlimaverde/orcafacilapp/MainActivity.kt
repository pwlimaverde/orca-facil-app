package com.pwlimaverde.orcafacilapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pwlimaverde.orcafacilapp.ui.navigation.OrcaNavGraph
import com.pwlimaverde.orcafacilapp.ui.theme.OrcaFacilAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OrcaFacilAppTheme {
                OrcaNavGraph()
            }
        }
    }
}
