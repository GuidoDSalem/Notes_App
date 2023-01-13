package com.example.notesapp.presentation.notes.composables

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedContentScope.SlideDirection.Companion.Start
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notesapp.domain.models.NoteColor

@SuppressLint("UnrememberedMutableState")
@Composable
fun EditableNote(
        modifier: Modifier,

        title: String,
        onTitleValueChange: (String)->Unit,
        titleHint: String = " ...Title ",
        showTitleHint: Boolean = false,

        content: String,
        onContentValueChange: (String) -> Unit,
        contentHint: String = "Note...",
        showContentHint: Boolean = false,

        onColorClick: (NoteColor)-> Unit
) {
    
    var showColor: Boolean by mutableStateOf<Boolean>(true)
    val colorList: List<NoteColor> = listOf(
            NoteColor.BlueColor,
            NoteColor.GreenColor,
            NoteColor.PurpleColor,
            NoteColor.GrayColor,
            NoteColor.YellowColor,
            NoteColor.CyanColor,
            NoteColor.RedColor,
            )
    Box(
            modifier = modifier,
            contentAlignment = Alignment.CenterEnd
    ){
        Column(
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.End
        ) {
            Text(
                    text = if(showTitleHint) titleHint else title,
                    style =  MaterialTheme.typography.h2,
                    color = if(showTitleHint) Color.Gray else Color.Black,
                    fontWeight = if(showTitleHint) FontWeight(1) else FontWeight(3)
            )

            Text(text = if(showContentHint) contentHint else content,
                 style =  MaterialTheme.typography.h4,
                 color = if(showContentHint) Color.Gray else Color.Black,
                 fontWeight = if(showContentHint) FontWeight(1) else FontWeight(3))

            AnimatedVisibility(visible = showColor) {
                LazyRow(modifier = Modifier.fillMaxWidth()){
                    items(colorList){ item ->

                        Spacer(modifier = Modifier.width(10.dp))

                        ColorItem(
                                noteColor = item,
                                modifier = Modifier.size(40.dp),
                                width = 5f
                        )
                        Spacer(modifier = Modifier.width(5.dp))

                    }
                }
            }
        }
        IconButton(
                onClick = {showColor = showColor.not()},
                modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Choose Color"
            )

        }

    }

}

@Preview
@Composable
fun PrevEditableNote() {

    EditableNote(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(NoteColor.BlueColor.color)
                .padding(vertical = 10.dp)
,
            title ="My Title",
            showTitleHint = false,
            onTitleValueChange ={},
            content ="My Content",
            showContentHint = true,
            onContentValueChange = {}
    
    ) {

    }

}
