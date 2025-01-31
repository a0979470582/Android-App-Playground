package com.example.androidplayground.ui.brush

import android.os.Build
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidplayground.R

private enum class BrushType {
    Horizontal,
    Vertical,
    Linear,
    Sweep,
    Radial,
    ColorStops,
    TileModeOnRepeated,
    TileModeOnMirror,
    TileModeOnClamp,
    TileModeOnDecal,
    ShaderBrushSmallRadial,
    ShaderBrushLargeRadial,
    ShaderBrushImage,
}

private data class BrushData(val brushType: BrushType, val title: String, val description: String)

private val brushes = listOf(
    BrushData(BrushType.Horizontal, "Brush.horizontalGradient(colorList)", "漸層效果從左至右。"),
    BrushData(BrushType.Vertical, "Brush.verticalGradient(colorList)", "漸層效果從上至下。"),
    BrushData(BrushType.Linear, "Brush.linearGradient(colorList)", "漸層效果從左上至右下。"),
    BrushData(
        BrushType.Sweep,
        "Brush.sweepGradient(colorList)",
        "漸層效果是圓餅圖，起始點是 x 軸正向，方向順時針。建議頭尾顏色相同會產生比較平滑的效果。"
    ),
    BrushData(
        BrushType.Radial,
        "Brush.radialGradient(colorList)",
        "漸層效果是同心圓，起始點是最內層。"
    ),
    BrushData(
        BrushType.ColorStops,
        "Brush.horizontalGradient(colorStops)",
        "之前的用法是傳入 colorList，但可以傳入 colorStops 來設定顏色分佈。在這個範例中，設定了 0f to Red 和 0.2f to Blue。"
    ),
    BrushData(
        BrushType.TileModeOnRepeated,
        "Brush.horizontalGradient(tileMode = TileMode.Repeated)",
        "tile 是磁磚、鋪磚的意思，當區域大小超過 brush 大小，例如在這個範例中設置了 endX=100.dp，才會看得出 TileMode 的效果。預設值是 TileMode.Clamp。"
    ),
    BrushData(
        BrushType.TileModeOnMirror,
        "Brush.horizontalGradient(tileMode = TileMode.Mirror)",
        "每一塊磚的顏色會是前一塊磚的反轉(鏡像)。此範例亦設置 endX=100.dp。"
    ),
    BrushData(
        BrushType.TileModeOnClamp,
        "Brush.horizontalGradient(tileMode = TileMode.Clamp)",
        "clamp 是鉗子，固定的意思，在程式設計的含義是限制數值的範圍。邊緣會被固定為最終顏色，其餘區域會採用此顏色。此範例亦設置 endX=100.dp。"
    ),
    BrushData(
        BrushType.TileModeOnDecal,
        "Brush.horizontalGradient(tileMode = TileMode.Decal)",
        "只繪製在指定範圍內，其餘區域顯示透明黑色，注意此屬性只在 API Level 31 以上支援，否則會回退至 TileMode.Clamp，您目前的 API Level: ${Build.VERSION.SDK_INT}。此範例亦設置 endX=100.dp。"
    ),
    BrushData(
        BrushType.ShaderBrushSmallRadial,
        "單純使用 Brush.radialGradient(...) 的效果",
        "預設情況下 Brush.radialGradient 的半徑會是寬高的最小值，這使得圓形看起來較小。如何調整請參考下個範例。"
    ),
    BrushData(
        BrushType.ShaderBrushLargeRadial,
        "自行實作 ShaderBrush 抽象類",
        "自行實作 ShaderBrush 抽象類的好處在於可以取得 brush 所應用的元件的 size，在這個範例中我們設定 radius 為寬高的最大值，這使得圓形看起來較大。"
    ),
    BrushData(
        BrushType.ShaderBrushImage,
        "將圖片變成 Brush",
        "首先將 R.drawable 轉換為 Bitmap 後傳入 ImageShader，該物件就像一般的 brush 可應用於 Box, Text, Canvas 元件。"
    ),
)


@Composable
fun BrushScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7)) // 柔和背景色
            .padding(16.dp)
    ) {
        items(brushes) { brushData ->
            GradientItem(brushData)
        }
    }
}

@Composable
private fun getBrushInstance(brushType: BrushType): Brush {
    return when (brushType) {
        BrushType.Horizontal -> Brush.horizontalGradient(listOf(Color.Red, Color.Blue))
        BrushType.Vertical -> Brush.verticalGradient(listOf(Color.Red, Color.Blue))
        BrushType.Linear -> Brush.linearGradient(listOf(Color.Red, Color.Blue))
        BrushType.Sweep -> Brush.sweepGradient(listOf(Color.Red, Color.Blue))
        BrushType.Radial -> Brush.radialGradient(listOf(Color.Red, Color.Blue))
        BrushType.ColorStops -> Brush.horizontalGradient(
            colorStops = arrayOf(
                0f to Color.Red,
                0.2f to Color.Blue
            )
        )

        BrushType.TileModeOnRepeated -> Brush.horizontalGradient(
            listOf(Color.Red, Color.Blue),
            endX = with(LocalDensity.current) { 100.dp.toPx() },
            tileMode = TileMode.Repeated
        )

        BrushType.TileModeOnMirror -> Brush.horizontalGradient(
            listOf(Color.Red, Color.Blue, Color.Yellow),
            endX = with(LocalDensity.current) { 100.dp.toPx() },
            tileMode = TileMode.Mirror
        )

        BrushType.TileModeOnClamp -> Brush.horizontalGradient(
            listOf(Color.Red, Color.Blue, Color.Yellow),
            endX = with(LocalDensity.current) { 100.dp.toPx() },
            tileMode = TileMode.Clamp
        )

        BrushType.TileModeOnDecal -> Brush.horizontalGradient(
            listOf(Color.Red, Color.Blue, Color.Yellow),
            startX = with(LocalDensity.current) { 0.dp.toPx() },
            endX = with(LocalDensity.current) { 100.dp.toPx() },
            tileMode = TileMode.Decal
        )

        BrushType.ShaderBrushSmallRadial -> Brush.radialGradient(
            listOf(
                Color(0xFF2be4dc),
                Color(0xFF243484)
            )
        )

        BrushType.ShaderBrushLargeRadial -> remember {
            object : ShaderBrush() {
                override fun createShader(size: Size): Shader {
                    return RadialGradientShader(
                        colors = listOf(Color(0xFF2be4dc), Color(0xFF243484)),
                        center = size.center,
                        radius = maxOf(size.width, size.height) / 2f,
                        colorStops = listOf(0f, 0.95f)
                    )
                }
            }
        }

        BrushType.ShaderBrushImage -> ShaderBrush(ImageShader(ImageBitmap.imageResource(id = R.drawable.dog)))
    }
}

@Composable
private fun ImageBrushDemo(brush: Brush) {
    Box(
        modifier = Modifier
            .size(200.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(brush = brush)
    )
    Text(
        text = "Hello Android!",
        style = TextStyle(
            brush = brush,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 36.sp
        )
    )
    Canvas(onDraw = {
        drawCircle(brush)
    }, modifier = Modifier.size(200.dp))
}

@Composable
private fun GradientItem(brushData: BrushData) {
    val brush = getBrushInstance(brushType = brushData.brushType)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(12.dp)) // 柔和圓角
            .background(Color.White) // 背景白色
            .padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp) // 元素間距
    ) {
        Text(
            text = brushData.title,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = Color(0xFF333333)
        )
        Text(
            text = brushData.description,
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF666666)
        )

        if (brushData.brushType == BrushType.ShaderBrushImage) {
            ImageBrushDemo(brush = brush)
        } else {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(brush = brush)
            )
        }
    }
}
