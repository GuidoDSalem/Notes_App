package com.example.notesapp.domain.models

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb


sealed class NoteColor(val hex: Long,val color:Color) {
    object BlueColor : NoteColor(0xFF7FD7FF,color = Color(0xFF7FD7FF))
    object GreenColor : NoteColor(0xFFA5D6A7,color = Color(0xFFA5D6A7))
    object PurpleColor : NoteColor(0xFFFF91F4,color = Color(0xFFFF91F4))
    object GrayColor : NoteColor(0xFFEEEEEE,color = Color(0xFFEEEEEE))
    object YellowColor : NoteColor(0xFFFFF59D,color = Color(0xFFFFF59D))
    object CyanColor : NoteColor(0xFF80DEEA,color = Color(0xFF80DEEA))
    object RedColor : NoteColor(0xFF000070,color = Color(0xFFFA8282))
    object NullColor: NoteColor(0,Color(0x00000000))

    companion object {
        fun fromLong(hex: Long): NoteColor {
            return when (hex) {
                0xFF7FD7FF -> NoteColor.BlueColor
                0xFFA5D6A7 -> NoteColor.GreenColor
                0xFFFF91F4 -> NoteColor.PurpleColor
                0xFFEEEEEE -> NoteColor.GrayColor
                0xFFFFF59D -> NoteColor.YellowColor
                0xFF80DEEA -> NoteColor.CyanColor
                0xFFFA8282 -> NoteColor.RedColor
                else -> NoteColor.BlueColor
            }
        }

        fun getLong(color: NoteColor): Long{
            return when(color){
                is BlueColor -> 0xFF7FD7FF
                is GreenColor -> 0xFFA5D6A7
                is PurpleColor -> 0xFFFF91F4
                is GrayColor -> 0xFFEEEEEE
                is YellowColor -> 0xFFFFF59D
                is CyanColor -> 0xFF80DEEA
                is RedColor -> 0xFFFA8282
                is NullColor -> 0x00000000
                else -> 0xFF7FD7FF
            }
        }
    }
}



