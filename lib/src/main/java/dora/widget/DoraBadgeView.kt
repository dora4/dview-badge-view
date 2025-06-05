package dora.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.ViewGroup
import dora.widget.badgeview.R

class DoraBadgeView @JvmOverloads constructor(context: Context, attrs: AttributeSet?,
                                              defStyleAttr: Int = 0) :
    ViewGroup(context, attrs, defStyleAttr) {

    var badgeColor: Int = Color.RED
    var badgeRadius: Int = 10
    var badgeGravity: Int = 0
    var badgeVerticesOffset: Int = 0
    var showBadge: Boolean = false
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            child.layout(l, t, r, b)
        }
    }

    override fun dispatchDraw(canvas: Canvas) {
        super.dispatchDraw(canvas)
        var cx = 0f
        var cy = 0f
        when (badgeGravity) {
            0 -> {
                cx = badgeRadius.toFloat() + badgeVerticesOffset
                cy = badgeRadius.toFloat() + badgeVerticesOffset
            }
            1 -> {
                cx = measuredWidth - badgeRadius.toFloat() - badgeVerticesOffset
                cy = badgeRadius.toFloat() + badgeVerticesOffset
            }
            2 -> {
                cx = measuredWidth - badgeRadius.toFloat() - badgeVerticesOffset
                cy = measuredHeight - badgeRadius.toFloat() - badgeVerticesOffset
            }
            3 -> {
                cx = badgeRadius.toFloat() + badgeVerticesOffset
                cy = measuredHeight - badgeRadius.toFloat() - badgeVerticesOffset
            }
        }
        if (showBadge) {
            canvas.drawCircle(cx, cy, badgeRadius.toFloat(), paint)
        }
    }

    init {
        setWillNotDraw(false)
        val a = context.obtainStyledAttributes(attrs, R.styleable.DoraBadgeView)
        badgeColor = a.getColor(R.styleable.DoraBadgeView_dview_bv_badgeColor, badgeColor)
        badgeRadius = a.getDimensionPixelSize(R.styleable.DoraBadgeView_dview_bv_badgeRadius, badgeRadius)
        badgeGravity = a.getInt(R.styleable.DoraBadgeView_dview_bv_badgeGravity, 0)
        badgeVerticesOffset = a.getDimensionPixelSize(R.styleable.DoraBadgeView_dview_bv_badgeVerticesOffset, badgeVerticesOffset)
        showBadge = a.getBoolean(R.styleable.DoraBadgeView_dview_bv_showBadge, showBadge)
        a.recycle()
        paint.color = badgeColor
    }
}