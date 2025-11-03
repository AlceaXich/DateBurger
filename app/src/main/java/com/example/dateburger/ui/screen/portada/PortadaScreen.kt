package com.example.dateburger.ui.screen.portada

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dateburger.R
import kotlin.math.max
import kotlin.math.min


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortadaScreen(viewModel: PortadaViewModel = viewModel()) {
    val event = viewModel.detailEvent.collectAsState().value

    val sheetState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = sheetState,
        sheetPeekHeight = 72.dp,

        sheetDragHandle = { BottomSheetDefaults.DragHandle() }, // Handle visual para indicar que el panel es arrastrable.
        sheetContent = {
            // Hacemos que el contenido pueda ocupar toda la altura al expandirse
            DetailEventContent(
                event = event,
                modifier = Modifier.fillMaxHeight().verticalScroll(rememberScrollState())
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.fillMaxSize().padding(innerPadding)
        ) {
            // --- Efecto Parallax ---
            // 1) Medimos el desplazamiento vertical (offset) del BottomSheet.
            //    Cuando el sheet está colapsado, su offset es mayor (más abajo); al expandirse,
            //    ese offset disminuye (sube).
            // 2) Guardamos el offset inicial (cuando está en su estado de peek/colapsado) para
            //    poder normalizar un progreso entre 0.0 y 1.0.
            // 3) Con ese progreso, aplicamos una traslación negativa en Y a la imagen para
            //    simular el parallax (la imagen se mueve menos que el sheet, creando profundidad).
            var peekOffsetPx by remember { mutableStateOf<Float?>(null) }
            val currentOffsetPx = runCatching { sheetState.bottomSheetState.requireOffset() }.getOrNull()
            if (peekOffsetPx == null && currentOffsetPx != null) {
                peekOffsetPx = currentOffsetPx
            }
            val progress = if (peekOffsetPx != null && currentOffsetPx != null && peekOffsetPx!! > 0f) {
                val raw = (peekOffsetPx!! - currentOffsetPx) / peekOffsetPx!!
                max(0f, min(1f, raw))
            } else 0f

            val parallaxPx = remember(progress) { progress * 120f }

            Image(
                painter = painterResource(id = R.drawable.background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize().graphicsLayer { translationY = -parallaxPx },
                contentScale = ContentScale.Crop
            )
        }
    }

}

@Composable
fun DetailEventContent(event: Event, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth().padding(24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "📅 ${event.date}", fontWeight = FontWeight.Medium, fontSize = 18.sp)
        Text(text = "📍 ${event.place}", fontWeight = FontWeight.Medium, fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = event.description, fontSize = 16.sp)
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { /* Navegar al mapa (en el siguiente paso) */ }) {
            Text("Ver mapa 🗺️")
        }
    }
}