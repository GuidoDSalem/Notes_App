package com.example.notesapp.presentation.composables

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notesapp.domain.models.NoteColor
import io.ktor.server.http.*
import java.time.LocalDate
import java.time.ZonedDateTime


val color_ = 0xFFFF91F4
val color = Color(color = color_)
val colorR_ : Float = color.red
val colorG_ : Float = color.green
val colorB_ : Float = color.blue
val colorA_ : Float = color.alpha
val colorHex_: String = color.toString()
val colorL_ : Int = color.toArgb()
val colorNEW = Color(colorL_)
//val c = when (NoteColor.BlueColor){
//    is NoteColor.BlueColor-> 0xff0000ff
//    else -> 0L
//}



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SimpleNote(
        modifier: Modifier,
        title: String,
        content: String,
        noteColor: NoteColor,
        time: ZonedDateTime,
        onClick: () -> Unit
) {


    Box(
            modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(color = noteColor.color).clickable {
                                                           onClick()
            },
            contentAlignment = Alignment.CenterEnd,

            ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                        end = 16.dp,
                        start = 10.dp
                )

            ,
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h2,
                color = MaterialTheme.colors.onSurface,
                fontWeight = FontWeight(5),
                fontSize = 36.sp,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = content,
                style = MaterialTheme.typography.body1,

            )
            Spacer(modifier = Modifier.height(4.dp))
            Row() {
                Text(text = "${time.dayOfMonth}/${time.month}")
                Spacer(Modifier.weight(0.90f))

            }
            Spacer(modifier = Modifier.height(5.dp))
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview()
@Composable
fun PreviewSimpleNote() {
    Column() {

        SimpleNote(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                        vertical = 16.dp,
                        horizontal = 8.dp
                ) ,
            title = "Title",
            content = "This is the ",
            noteColor = NoteColor.BlueColor,
            time = ZonedDateTime.now()

        ){}
    }

}

