package com.example.assigment3

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlin.math.floor

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun draughtsView(game_state: MutableList<MutableList<Int>>,orientation_vector: FloatArray,onAttemptMove:(x:Int,y:Int) ->Unit){
    var width by remember { mutableStateOf(0.0f) }
    var height by remember { mutableStateOf(0.0f) }
    var cell_width by remember { mutableStateOf(0.0f) }
    var cell_height by remember { mutableStateOf(0.0f) }




    Canvas(modifier = Modifier
        .padding(5.dp)
        .aspectRatio((1.0f))
        .background(color = Color.DarkGray)
        .pointerInput(key1 = Unit) {
            detectTapGestures(
                onTap = {
                    var cell_x = floor(it.x / cell_width).toInt()
                    var cell_y = floor(it.y / cell_height).toInt()
                    onAttemptMove(cell_x, cell_y)
                }
            )
        }){
        width = size.width
        height = size.height

        cell_width = width / 11.0f
        cell_height =width/11.0f


//        for (i in 0 until 11) {
//            for (j in 0 until 11) {
//                var color= Color.Black
//                if (array[i][j]==0){
//                     color= Color.DarkGray}
//                drawRect(
//                    color = color,
//                    topLeft = Offset(i * cell_width, j * cell_height),
//                    size = androidx.compose.ui.geometry.Size(cell_width, cell_height)
//                )
//
//
//
//            }
//
//        }
        // Convert to radians
        val rotationXRad = Math.toRadians(orientation_vector[0].toDouble())
        val rotationYRad = Math.toRadians(orientation_vector[1].toDouble())
        val rotationZRad = Math.toRadians(orientation_vector[2].toDouble())

        rotate(degrees = orientation_vector[0]) {
            rotate(degrees = orientation_vector[1]) {
                rotate(degrees = orientation_vector[2]) {
                    // Draw your content here, considering it will be rotated
                    //drawCircle(color = Color.Red, center = Offset(size.width / 2, size.height / 2), radius = 50f)
                    // Add more drawing operations as needed


        val center = Offset(width / 2, height / 2)
        val endPoint = Offset(center.x, 0f)
        drawLine(
            color = Color.Red, // Change color if needed
            start = center,
            end = endPoint,
            strokeWidth = 4f, // Change stroke width if needed
            cap = StrokeCap.Round // Change stroke cap if needed
        )
        drawLine(
            color = Color.Green, // Change color if needed
            start = center,
            end = Offset(center.x, height),
            strokeWidth = 4f, // Change stroke width if needed
            cap = StrokeCap.Round // Change stroke cap if needed
        )
        drawLine(
            color = Color.Green, // Change color if needed
            start = center,
            end = Offset(width, center.y),
            strokeWidth = 4f, // Change stroke width if needed
            cap = StrokeCap.Round // Change stroke cap if needed
        )
        drawLine(
            color = Color.Green, // Change color if needed
            start = center,
            end = Offset(0f, center.y),
            strokeWidth = 4f, // Change stroke width if needed
            cap = StrokeCap.Round // Change stroke cap if needed
        )



        // Drawing letters at different positions
        val textSize = 48f // Change text size if needed

        drawIntoCanvas { canvas ->
            val paint = Paint().asFrameworkPaint()
            paint.textSize = textSize
            paint.textAlign = android.graphics.Paint.Align.CENTER
            paint.color= android.graphics.Color.BLUE

            val textBounds = android.graphics.Rect()

            // Draw N upside down
            val textN = "N"
            paint.getTextBounds(textN, 0, textN.length, textBounds)
            val xN = width/2 + textBounds.exactCenterY()
            val yN = 50f //center.y - textBounds.exactCenterY()
            canvas.nativeCanvas.drawText(textN, xN, yN, paint)

            // Draw S
            val textS = "S"
            paint.getTextBounds(textS, 0, textS.length, textBounds)
            val xS = center.x + textBounds.exactCenterY()
            val yS = height - 50f//center.y + textBounds.exactCenterY() * 3 // Increase/decrease the multiplier to adjust spacing
            canvas.nativeCanvas.drawText(textS, xS, yS, paint)

            // Draw E
            val textE = "W"
            paint.getTextBounds(textE, 0, textE.length, textBounds)
            val xE = 50f
            val yE = center.y + textBounds.exactCenterY() // Adjust the y position as needed
            canvas.nativeCanvas.drawText(textE, xE, yE, paint)

            // Draw W
            val textW = "E"
            paint.getTextBounds(textW, 0, textW.length, textBounds)
            val xW = width-50f
            val yW = center.y + textBounds.exactCenterY() // Adjust the y position as needed
            canvas.nativeCanvas.drawText(textW, xW, yW, paint)
            println(width)
        }
        // Draw a circle that fits within the canvas
        val radius = if (width < height) width / 2 else height / 2
        drawCircle(
            color = Color.Yellow,
            center = center,
            radius = radius - 20f, // Adjust the padding as needed
            style = Stroke(30f)
        )

    }
            }
        }
    }

}


