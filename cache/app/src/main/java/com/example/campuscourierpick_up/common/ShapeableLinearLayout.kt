package com.example.campuscourierpick_up.common

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.StateListDrawable
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.Dimension
import androidx.core.content.ContextCompat
import com.example.campuscourierpick_up.R
import com.example.campuscourierpick_up.common.Corner.Companion.BOTTOM_LEFT
import com.example.campuscourierpick_up.common.Corner.Companion.BOTTOM_RIGHT
import com.example.campuscourierpick_up.common.Corner.Companion.TOP_LEFT
import com.example.campuscourierpick_up.common.Corner.Companion.TOP_RIGHT

/**
 * 支持shape的LinearLayout
 */
class ShapeableLinearLayout : LinearLayout {

    private var strokeWidth: Int = -1
    private var dashWidth: Float = 0f
    private var dashGap: Float = 0f
    private var normalStrokeColor: ColorStateList?

    private var checkedStrokeColor: ColorStateList?
    private var disableStrokeColor: ColorStateList?


    private var normalSolidColor: ColorStateList?
    private var checkedSolidColor: ColorStateList?
    private var disableSolidColor: ColorStateList?


    private var normalGradientColors: MutableList<Int>
    private var checkedGradientColors: MutableList<Int>
    private var disableGradientColors: MutableList<Int>

    private var shapeModel: Int = GradientDrawable.RECTANGLE
    private lateinit var gradientDrawable: GradientDrawable
    private lateinit var stateListDrawable: StateListDrawable

    private var checkedDrawable: GradientDrawable? = null
    private var disableDrawable: GradientDrawable? = null

    @GradientOrientation
    private var gradientOrientation: Int = GradientOrientation.LEFT_RIGHT
    private var gradientType: Int = GradientDrawable.LINEAR_GRADIENT
    private val cornerRadii =
        floatArrayOf(
            DEFAULT_RADIUS,
            DEFAULT_RADIUS,
            DEFAULT_RADIUS,
            DEFAULT_RADIUS
        )

    companion object {
        const val DEFAULT_RADIUS = 0f
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attributeSet: AttributeSet?) : this(context, attributeSet, 0)
    constructor(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) : super(
        context, attributeSet, defStyleAttr
    ) {
        val a = context.obtainStyledAttributes(attributeSet, R.styleable.ShapeableLinearLayout)
        var cornerSize =
            a.getDimensionPixelSize(R.styleable.ShapeableLinearLayout_cornerRadius, -1).toFloat()
        cornerRadii[TOP_LEFT] =
            a.getDimensionPixelSize(R.styleable.ShapeableLinearLayout_corner_radius_top_left, -1)
                .toFloat()
        cornerRadii[TOP_RIGHT] =
            a.getDimensionPixelSize(R.styleable.ShapeableLinearLayout_corner_radius_top_right, -1)
                .toFloat()
        cornerRadii[BOTTOM_RIGHT] = a.getDimensionPixelSize(
            R.styleable.ShapeableLinearLayout_corner_radius_bottom_right,
            -1
        ).toFloat()
        cornerRadii[BOTTOM_LEFT] =
            a.getDimensionPixelSize(R.styleable.ShapeableLinearLayout_corner_radius_bottom_left, -1)
                .toFloat()
        strokeWidth = a.getDimensionPixelSize(R.styleable.ShapeableLinearLayout_strokeWidth, -1)
        dashWidth = a.getFloat(R.styleable.ShapeableLinearLayout_dashWidth, 0f)
        dashGap = a.getFloat(R.styleable.ShapeableLinearLayout_dashGap, 0f)

        normalSolidColor =
            ShapeUtils.getColorStateList(context, a, R.styleable.ShapeableLinearLayout_solidColor)

        checkedSolidColor = ShapeUtils.getColorStateList(
            context,
            a,
            R.styleable.ShapeableLinearLayout_checkedSolidColor
        )
        disableSolidColor = ShapeUtils.getColorStateList(
            context,
            a,
            R.styleable.ShapeableLinearLayout_disableSolidColor
        )

        normalStrokeColor =
            ShapeUtils.getColorStateList(context, a, R.styleable.ShapeableLinearLayout_strokeColor)

        checkedStrokeColor = ShapeUtils.getColorStateList(
            context,
            a,
            R.styleable.ShapeableLinearLayout_checkedStrokeColor
        )
        disableStrokeColor = ShapeUtils.getColorStateList(
            context,
            a,
            R.styleable.ShapeableLinearLayout_disableStrokeColor
        )

        shapeModel =
            a.getInt(R.styleable.ShapeableLinearLayout_shapeModel, GradientDrawable.RECTANGLE)

        val normalGradientStartColor =
            a.getColor(R.styleable.ShapeableLinearLayout_gradientStartColor, 0)
        val normalGradientMiddleColor =
            a.getColor(R.styleable.ShapeableLinearLayout_gradientMiddleColor, 0)
        val normalGradientEndColor =
            a.getColor(R.styleable.ShapeableLinearLayout_gradientEndColor, 0)

        val checkedGradientStartColor =
            a.getColor(R.styleable.ShapeableLinearLayout_checkedGradientStartColor, 0)
        val checkedGradientMiddleColor =
            a.getColor(R.styleable.ShapeableLinearLayout_checkedGradientMiddleColor, 0)
        val checkedGradientEndColor =
            a.getColor(R.styleable.ShapeableLinearLayout_checkedGradientEndColor, 0)

        val disableGradientStartColor =
            a.getColor(R.styleable.ShapeableLinearLayout_disableGradientStartColor, 0)
        val disableGradientMiddleColor =
            a.getColor(R.styleable.ShapeableLinearLayout_disableGradientMiddleColor, 0)
        val disableGradientEndColor =
            a.getColor(R.styleable.ShapeableLinearLayout_disableGradientEndColor, 0)

        gradientOrientation = a.getInt(
            R.styleable.ShapeableLinearLayout_gradientOrientation,
            GradientOrientation.LEFT_RIGHT
        )
        gradientType = a.getInt(
            R.styleable.ShapeableLinearLayout_gradientType,
            GradientDrawable.LINEAR_GRADIENT
        )

        a.recycle()

        if (shapeModel == GradientDrawable.RECTANGLE) {
            var any = false
            for ((i, cornerRadius) in cornerRadii.withIndex()) {
                if (cornerRadius < 0) {
                    cornerRadii[i] = 0f
                } else {
                    any = true
                }
            }
            if (!any) {
                if (cornerSize < 0) {
                    cornerSize = DEFAULT_RADIUS
                }
                for ((i, _) in cornerRadii.withIndex()) {
                    cornerRadii[i] = cornerSize
                }
            }
        }

        checkedGradientColors = mutableListOf()
        normalGradientColors = mutableListOf()
        disableGradientColors = mutableListOf()

        if (normalGradientStartColor != 0) {
            normalGradientColors.add(normalGradientStartColor)
        }
        if (normalGradientMiddleColor != 0) {
            normalGradientColors.add(normalGradientMiddleColor)
        }
        if (normalGradientEndColor != 0) {
            normalGradientColors.add(normalGradientEndColor)
        }

        if (checkedGradientStartColor != 0) {
            checkedGradientColors.add(checkedGradientStartColor)
        }
        if (checkedGradientMiddleColor != 0) {
            checkedGradientColors.add(checkedGradientMiddleColor)
        }
        if (checkedGradientEndColor != 0) {
            checkedGradientColors.add(checkedGradientEndColor)
        }

        if (disableGradientStartColor != 0) {
            disableGradientColors.add(disableGradientStartColor)
        }
        if (disableGradientMiddleColor != 0) {
            disableGradientColors.add(disableGradientMiddleColor)
        }
        if (disableGradientEndColor != 0) {
            disableGradientColors.add(disableGradientEndColor)
        }
        initDrawable()
    }

    private fun initDrawable() {
        stateListDrawable = StateListDrawable()
        gradientDrawable =
            generateDrawable(normalSolidColor, normalStrokeColor, normalGradientColors)

        checkedDrawable =
            generateDrawable(checkedSolidColor, checkedSolidColor, checkedGradientColors)
        disableDrawable =
            generateDrawable(disableSolidColor, disableStrokeColor, disableGradientColors)

        stateListDrawable.addState(intArrayOf(android.R.attr.state_checked), checkedDrawable)
        stateListDrawable.addState(intArrayOf(android.R.attr.state_enabled), gradientDrawable)
        stateListDrawable.addState(intArrayOf(-android.R.attr.state_enabled), disableDrawable)
        stateListDrawable.addState(intArrayOf(), gradientDrawable)
        background = stateListDrawable
    }

    private fun generateDrawable(
        solidColor: ColorStateList?,
        strokeColor: ColorStateList?,
        gradientColors: MutableList<Int>? = null
    ): GradientDrawable {
        val drawable = GradientDrawable()

        drawable.shape = shapeModel
        setCornerSize(drawable)

        if (strokeColor != null) {
            drawable.setStroke(strokeWidth, strokeColor, dashWidth, dashGap)
        }

        drawable.gradientType = gradientType
        if (!gradientColors.isNullOrEmpty()) {
            if (gradientColors.size < 2) {
                throw IllegalArgumentException("gradient color数量必须大于2")
            }
            drawable.colors = gradientColors.toIntArray()
        } else {
            drawable.color = solidColor
        }
        val orientation: GradientDrawable.Orientation =
            ShapeUtils.getGradientOrientation(gradientOrientation)
        drawable.orientation = orientation
        return drawable
    }

    private fun setCornerSize(gradientDrawable: GradientDrawable) {
        val cornerRadii =
            floatArrayOf(
                this.cornerRadii[TOP_LEFT],
                this.cornerRadii[TOP_LEFT],
                this.cornerRadii[TOP_RIGHT],
                this.cornerRadii[TOP_RIGHT],
                this.cornerRadii[BOTTOM_RIGHT],
                this.cornerRadii[BOTTOM_RIGHT],
                this.cornerRadii[BOTTOM_LEFT],
                this.cornerRadii[BOTTOM_LEFT]
            )
        gradientDrawable.cornerRadii = cornerRadii
    }

    fun setSolidColorRes(@ColorRes solidColor: Int) {
        setSolidColor(ColorStateList.valueOf(ContextCompat.getColor(context, solidColor)))
    }

    fun setSolidColor(@ColorInt solidColor: Int) {
        setSolidColor(ColorStateList.valueOf(solidColor))
    }

    fun setSolidColor(solidColor: ColorStateList?) {
        this.normalSolidColor = solidColor
        gradientDrawable.color = solidColor
    }

    fun setGradientColor(orientation: GradientDrawable.Orientation, @ColorInt colors: IntArray) {
        gradientDrawable.orientation = orientation
        gradientDrawable.colors = colors
    }

    fun setCheckedGradientColor(
        orientation: GradientDrawable.Orientation,
        @ColorInt colors: IntArray
    ) {
        checkedDrawable?.orientation = orientation
        checkedDrawable?.colors = colors
    }

    fun setDisableGradientColor(
        orientation: GradientDrawable.Orientation,
        @ColorInt colors: IntArray
    ) {
        disableDrawable?.orientation = orientation
        disableDrawable?.colors = colors
    }

    fun setStrokeColor(@ColorInt color: Int) {
        if (strokeWidth > 0) {
            normalStrokeColor = ColorStateList.valueOf(color)
            gradientDrawable.setStroke(strokeWidth, normalStrokeColor, dashWidth, dashGap)
        }
    }

    fun setStrokeColorRes(@ColorRes color: Int) {
        if (strokeWidth > 0) {
            normalStrokeColor = ColorStateList.valueOf(ContextCompat.getColor(context, color))
            gradientDrawable.setStroke(strokeWidth, normalStrokeColor, dashWidth, dashGap)
        }
    }

    fun setCheckedStrokeColor(@ColorInt color: Int) {
        checkedStrokeColor = ColorStateList.valueOf(color)
        checkedDrawable?.setStroke(strokeWidth, normalStrokeColor, dashWidth, dashGap)
    }


    fun setDisableStrokeColor(@ColorInt color: Int) {
        disableStrokeColor = ColorStateList.valueOf(color)
        disableDrawable?.setStroke(strokeWidth, normalStrokeColor, dashWidth, dashGap)
    }

    fun setCornerSize(@Dimension cornerSize: Float) {
        for (i in cornerRadii.indices) {
            cornerRadii[i] = cornerSize
        }
        setCornerSize(gradientDrawable)

        checkedDrawable?.let {
            setCornerSize(it)
        }
        disableDrawable?.let {
            setCornerSize(it)
        }
    }

    fun setCornerRadius(
        @Dimension topLeft: Float,
        @Dimension topRight: Float,
        @Dimension bottomLeft: Float,
        @Dimension bottomRight: Float
    ) {
        if (cornerRadii[TOP_LEFT] == topLeft && cornerRadii[TOP_RIGHT] == topRight && cornerRadii[BOTTOM_RIGHT] == bottomRight && cornerRadii[BOTTOM_LEFT] == bottomLeft) {
            return
        }
        cornerRadii[TOP_LEFT] = topLeft
        cornerRadii[TOP_RIGHT] = topRight
        cornerRadii[BOTTOM_LEFT] = bottomLeft
        cornerRadii[BOTTOM_RIGHT] = bottomRight
        setCornerSize(gradientDrawable)

        checkedDrawable?.let {
            setCornerSize(it)
        }
        disableDrawable?.let {
            setCornerSize(it)
        }
    }

    fun setTopLeftCorner(@Dimension cornerSize: Float) {
        if (cornerRadii[TOP_LEFT] == cornerSize) {
            return
        }
        cornerRadii[TOP_LEFT] = cornerSize
        setCornerSize(gradientDrawable)

        checkedDrawable?.let {
            setCornerSize(it)
        }
        disableDrawable?.let {
            setCornerSize(it)
        }
    }

    fun setTopRightCorner(@Dimension cornerSize: Float) {
        if (cornerRadii[TOP_RIGHT] == cornerSize) {
            return
        }
        cornerRadii[TOP_RIGHT] = cornerSize
        setCornerSize(gradientDrawable)

        checkedDrawable?.let {
            setCornerSize(it)
        }
        disableDrawable?.let {
            setCornerSize(it)
        }
    }

    fun setBottomLeftCorner(@Dimension cornerSize: Float) {
        if (cornerRadii[BOTTOM_LEFT] == cornerSize) {
            return
        }
        cornerRadii[BOTTOM_LEFT] = cornerSize
        setCornerSize(gradientDrawable)
        checkedDrawable?.let {
            setCornerSize(it)
        }
        disableDrawable?.let {
            setCornerSize(it)
        }
    }

    fun setBottomRightCorner(@Dimension cornerSize: Float) {
        if (cornerRadii[TOP_LEFT] == cornerSize) {
            return
        }
        cornerRadii[BOTTOM_RIGHT] = cornerSize
        setCornerSize(gradientDrawable)
    }
}