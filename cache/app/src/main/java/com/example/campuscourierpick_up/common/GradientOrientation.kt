package com.example.campuscourierpick_up.common

import androidx.annotation.IntDef

/**
 * 朝向
 */
@Retention
@IntDef(
    GradientOrientation.TOP_BOTTOM,
    GradientOrientation.TR_BL,
    GradientOrientation.RIGHT_LEFT,
    GradientOrientation.BR_TL,
    GradientOrientation.BOTTOM_TOP,
    GradientOrientation.BL_TR,
    GradientOrientation.LEFT_RIGHT,
    GradientOrientation.TL_BR
)
annotation class GradientOrientation {
    companion object {
        /** draw the gradient from the top to the bottom  */
        const val TOP_BOTTOM: Int = 0

        /** draw the gradient from the top-right to the bottom-left  */
        const val TR_BL: Int = 1

        /** draw the gradient from the right to the left  */
        const val RIGHT_LEFT: Int = 2

        /** draw the gradient from the bottom-right to the top-left  */
        const val BR_TL: Int = 3

        /** draw the gradient from the bottom to the top  */
        const val BOTTOM_TOP: Int = 4

        /** draw the gradient from the bottom-left to the top-right  */
        const val BL_TR: Int = 5

        /** draw the gradient from the left to the right  */
        const val LEFT_RIGHT: Int = 6

        /** draw the gradient from the top-left to the bottom-right  */
        const val TL_BR: Int = 7
    }
}