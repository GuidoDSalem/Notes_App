package com.example.notesapp.presentation.notes.composables

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedContentScope.SlideDirection.Companion.End
import androidx.compose.animation.AnimatedContentScope.SlideDirection.Companion.Start
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.InspectableModifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notesapp.domain.models.Note
import com.example.notesapp.domain.models.NoteColor

@Composable
fun EditableNote(
        modifier: Modifier,

        title: String,
        onTitleValueChange: (String)->Unit,
        titleHint: String = " ...Title ",
        content: String,
        onContentValueChange: (String) -> Unit,
        contentHint: String = "...Note",
        color: NoteColor = NoteColor.BlueColor,
        onColorClick: (NoteColor)-> Unit
) {
    
    var showColor by  remember{
        mutableStateOf<Boolean>(true)
    }

    var title_ by remember {
        mutableStateOf(title)
    }

    var content_ by remember{
        mutableStateOf(content)
    }


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
            modifier = modifier
                .padding(horizontal = 5.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(color.color),
            contentAlignment = Alignment.CenterEnd
    ){
        Column(
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.End
        ) {
            Spacer(modifier = Modifier.height(10.dp))

                BasicTextField(
                    value = title,
                    modifier = Modifier.padding(horizontal = 5.dp),
                    onValueChange = {
                        onTitleValueChange(it)
                        title_ = it
                    },
                    textStyle = MaterialTheme.typography.h3.copy(
                            textAlign = TextAlign.End
                    ),
                    decorationBox = { innerTextField ->
                        if (title.isEmpty()){
                            Text(text = titleHint,
                                 style = MaterialTheme.typography.h3.copy(
                                    textAlign = TextAlign.End
                            ),
                             fontWeight = FontWeight.Light, color = Color.Gray)
                        }
                        else{
                            innerTextField()


                        }
                    }
                )

            Spacer(modifier = Modifier.height(10.dp))

            BasicTextField(
                    value = content,
                    modifier = Modifier.padding(horizontal = 5.dp),
                    onValueChange = { newContent ->
                        onContentValueChange(newContent)
                        content_ = newContent },
                    textStyle = MaterialTheme.typography.h5.copy(
                            textAlign = TextAlign.End
                    ),
                    decorationBox = { innerTextField ->
                        if (content.isEmpty()){
                            Text(text = contentHint,
                                 style = MaterialTheme.typography.h5.copy(
                                         textAlign = TextAlign.End
                                 ),
                                 fontWeight = FontWeight.Light, color = Color.Gray)
                        }
                        else{
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Spacer(modifier = Modifier.width(40.dp))
                                innerTextField()

                            }
                        }
                    }
            )

            Spacer(modifier = Modifier.height(25.dp))

            AnimatedVisibility(visible = showColor) {
                LazyRow(modifier = Modifier.fillMaxWidth()){
                    items(colorList){ color: NoteColor ->

                        Spacer(modifier = Modifier.width(10.dp))

                        ColorItem(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clickable {
                                        onColorClick(color)
                                    },
                                noteColor = color,
                                width = 5f,


                        )
                        Spacer(modifier = Modifier.width(5.dp))

                    }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
        }

        IconButton(
                onClick = {showColor = !showColor},
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
                .padding(vertical = 10.dp)
,
            title ="",
            onTitleValueChange ={ },
            content ="This is Good " +
                    "Contentaaaaaaaaaaaaaaaaaaaaaaaaaaaaaasasasdadaddadadadadadadsadsadadadaa",
            onContentValueChange = {}

    ) {

    }

}
