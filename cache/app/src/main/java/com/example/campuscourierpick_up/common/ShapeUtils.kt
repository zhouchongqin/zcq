package com.example.campuscourierpick_up.common

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.GradientDrawable
import androidx.annotation.StyleableRes
import androidx.appcompat.content.res.AppCompatResources

/**
 * 工具类
 */
object ShapeUtils {
    fun getColorStateList(
        context: Context, attributes: TypedArray, @StyleableRes index: Int
    ): ColorStateList? {
        if (attributes.hasValue(index)) {
            val resourceId = attributes.getResourceId(index, 0)
            if (resourceId != 0) {
                val value = AppCompatResources.getColorStateList(context, resourceId)
                if (value != null) {
                    return value
                }
            }
        }
        return attributes.getColorStateList(index)
    }

    fun getGradientOrientation(orientation: Int): GradientDrawable.Orientation {
        return when (orientation) {
            GradientOrientation.TOP_BOTTOM -> {
                GradientDrawable.Orientation.TOP_BOTTOM
            }
            GradientOrientation.TR_BL -> {
                GradientDrawable.Orientation.TR_BL
            }
            GradientOrientation.RIGHT_LEFT -> {
                GradientDrawable.Orientation.RIGHT_LEFT
            }
            GradientOrientation.BR_TL -> {
                GradientDrawable.Orientation.BR_TL
            }
            GradientOrientation.BOTTOM_TOP -> {
                GradientDrawable.Orientation.BOTTOM_TOP
            }
            GradientOrientation.BL_TR -> {
                GradientDrawable.Orientation.BL_TR
            }
            GradientOrientation.LEFT_RIGHT -> {
                GradientDrawable.Orientation.LEFT_RIGHT
            }
            GradientOrientation.TL_BR -> {
                GradientDrawable.Orientation.TL_BR
            }
            else -> {
                GradientDrawable.Orientation.TOP_BOTTOM
            }
        }
    }
}