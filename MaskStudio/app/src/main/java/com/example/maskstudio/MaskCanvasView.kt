package com.example.maskstudio

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Region
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

data class ColoredPath(val path: Path, val color: Int)

class MaskCanvasView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var maskType: String = "Oni Mask"
    private var currentColor: Int = Color.RED

    private val maskOutlinePaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 6f
        isAntiAlias = true
    }

    private val eyeMouthPaint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    private val eyeMouthStrokePaint = Paint().apply {
        color = Color.WHITE
        style = Paint.Style.STROKE
        strokeWidth = 4f
        isAntiAlias = true
    }

    private val pathList = mutableListOf<ColoredPath>()
    private var currentPath: Path? = null

    private val maskPath = Path()
    private val holesPath = Path()

    fun setMaskType(type: String) {
        maskType = type
        invalidate()
    }

    fun setColor(color: Int) {
        currentColor = color
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val newPath = Path()
                newPath.moveTo(x, y)
                currentPath = newPath
                pathList.add(ColoredPath(newPath, currentColor))
            }
            MotionEvent.ACTION_MOVE -> {
                currentPath?.lineTo(x, y)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                currentPath = null
            }
            else -> return false
        }
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val cx = width / 2f
        val cy = height / 2.3f

        buildPaths(cx, cy)

        canvas.drawPath(maskPath, maskOutlinePaint)

        canvas.save()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            canvas.clipPath(maskPath)
            canvas.clipOutPath(holesPath)
        } else {
            @Suppress("DEPRECATION")
            canvas.clipPath(maskPath)
            @Suppress("DEPRECATION")
            canvas.clipPath(holesPath, Region.Op.DIFFERENCE)
        }

        for (coloredPath in pathList) {
            val strokePaint = Paint().apply {
                color = coloredPath.color
                style = Paint.Style.STROKE
                strokeWidth = 14f
                isAntiAlias = true
                strokeCap = Paint.Cap.ROUND
                strokeJoin = Paint.Join.ROUND
            }
            canvas.drawPath(coloredPath.path, strokePaint)
        }
        canvas.restore()

        canvas.drawPath(holesPath, eyeMouthPaint)
        canvas.drawPath(holesPath, eyeMouthStrokePaint)
    }

    private fun buildPaths(cx: Float, cy: Float) {
        maskPath.reset()
        holesPath.reset()

        when {
            maskType.contains("Kitsune", ignoreCase = true) -> {
                maskPath.moveTo(cx, cy + 300f)
                maskPath.lineTo(cx - 240f, cy + 20f)
                maskPath.lineTo(cx - 300f, cy - 320f)
                maskPath.lineTo(cx - 120f, cy - 160f)
                maskPath.lineTo(cx + 120f, cy - 160f)
                maskPath.lineTo(cx + 300f, cy - 320f)
                maskPath.lineTo(cx + 240f, cy + 20f)
                maskPath.close()

                maskPath.moveTo(cx - 270f, cy - 280f); maskPath.lineTo(cx - 150f, cy - 170f)
                maskPath.moveTo(cx + 270f, cy - 280f); maskPath.lineTo(cx + 150f, cy - 170f)

                maskPath.moveTo(cx - 160f, cy + 80f); maskPath.lineTo(cx - 290f, cy + 60f)
                maskPath.moveTo(cx - 150f, cy + 120f); maskPath.lineTo(cx - 280f, cy + 130f)
                maskPath.moveTo(cx + 160f, cy + 80f); maskPath.lineTo(cx + 290f, cy + 60f)
                maskPath.moveTo(cx + 150f, cy + 120f); maskPath.lineTo(cx + 280f, cy + 130f)

                holesPath.addCircle(cx - 90f, cy - 20f, 28f, Path.Direction.CW)
                holesPath.addCircle(cx + 90f, cy - 20f, 28f, Path.Direction.CW)
                holesPath.addCircle(cx, cy + 200f, 18f, Path.Direction.CW)
            }
            maskType.contains("Cyberpunk", ignoreCase = true) -> {
                maskPath.moveTo(cx - 240f, cy - 100f)
                maskPath.lineTo(cx + 240f, cy - 100f)
                maskPath.lineTo(cx + 180f, cy + 80f)
                maskPath.lineTo(cx + 100f, cy + 150f)
                maskPath.lineTo(cx - 100f, cy + 150f)
                maskPath.lineTo(cx - 180f, cy + 80f)
                maskPath.close()

                maskPath.moveTo(cx - 240f, cy - 100f); maskPath.lineTo(cx - 270f, cy); maskPath.lineTo(cx - 180f, cy + 80f)
                maskPath.moveTo(cx + 240f, cy - 100f); maskPath.lineTo(cx + 270f, cy); maskPath.lineTo(cx + 180f, cy + 80f)

                holesPath.addRect(cx - 180f, cy - 60f, cx + 180f, cy + 20f, Path.Direction.CW)
                holesPath.addRect(cx - 60f, cy + 80f, cx + 60f, cy + 110f, Path.Direction.CW)
            }
            maskType.contains("Gala", ignoreCase = true) -> {
                maskPath.moveTo(cx - 320f, cy - 100f)
                maskPath.cubicTo(cx - 160f, cy - 220f, cx + 160f, cy - 220f, cx + 320f, cy - 100f)
                maskPath.cubicTo(cx + 350f, cy + 80f, cx + 180f, cy + 180f, cx, cy + 60f)
                maskPath.cubicTo(cx - 180f, cy + 180f, cx - 350f, cy + 80f, cx - 320f, cy - 100f)
                maskPath.close()

                holesPath.addOval(cx - 190f, cy - 80f, cx - 50f, cy, Path.Direction.CW)
                holesPath.addOval(cx + 50f, cy - 80f, cx + 190f, cy, Path.Direction.CW)
            }
            maskType.contains("Comedy", ignoreCase = true) -> {
                maskPath.addOval(cx - 240f, cy - 300f, cx + 240f, cy + 300f, Path.Direction.CW)

                holesPath.addCircle(cx - 100f, cy - 90f, 38f, Path.Direction.CW)
                holesPath.addCircle(cx + 100f, cy - 90f, 38f, Path.Direction.CW)
                holesPath.addOval(cx - 130f, cy + 70f, cx + 130f, cy + 190f, Path.Direction.CW)
            }
            else -> {
                maskPath.addCircle(cx, cy, 220f, Path.Direction.CW)

                maskPath.moveTo(cx - 160f, cy - 140f); maskPath.lineTo(cx - 260f, cy - 340f); maskPath.lineTo(cx - 70f, cy - 200f)
                maskPath.moveTo(cx + 160f, cy - 140f); maskPath.lineTo(cx + 260f, cy - 340f); maskPath.lineTo(cx + 70f, cy - 200f)

                holesPath.addCircle(cx - 90f, cy - 40f, 35f, Path.Direction.CW)
                holesPath.addCircle(cx + 90f, cy - 40f, 35f, Path.Direction.CW)

                holesPath.addRect(cx - 110f, cy + 70f, cx + 110f, cy + 130f, Path.Direction.CW)

                holesPath.moveTo(cx - 70f, cy + 70f)
                holesPath.lineTo(cx - 50f, cy + 110f)
                holesPath.lineTo(cx - 30f, cy + 70f)

                holesPath.moveTo(cx + 30f, cy + 70f)
                holesPath.lineTo(cx + 50f, cy + 110f)
                holesPath.lineTo(cx + 70f, cy + 70f)
            }
        }
    }
}