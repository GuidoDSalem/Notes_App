package com.example.notesapp.presentation.notes.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notesapp.domain.models.NoteColor


@Composable
fun ColorItem(
        modifier: Modifier,
        noteColor: NoteColor,
        width: Float = 35.dp.value

) {
    Box(modifier = modifier, contentAlignment = Alignment.Center){
        Canvas(
                modifier = Modifier.fillMaxSize()

        ){



            drawCircle(
                    color = Color.Black,
                    radius = center.x * 0.95f,
                    style = Stroke(width)
            )

            drawCircle(
                    color = noteColor.color,
                    radius = center.x * 0.85f,
            )



        }
    }


}


@Preview
@Composable
fun PreviewColorItem() {
    ColorItem(
            noteColor = NoteColor.CyanColor,
            modifier = Modifier.size(400.dp).background(Color(0x22FF55FF)),

    )

}


