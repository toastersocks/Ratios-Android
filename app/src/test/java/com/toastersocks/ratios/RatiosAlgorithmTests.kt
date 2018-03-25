package com.toastersocks.ratios

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class RatiosAlgorithmTests {

    @Test
    fun testRatiosAlgorithm() {
        val thcStrain = Strain(25.0, 1.0)
        val cbdStrain = Strain(0.02, 10.0)

        val ratiosAlgorithm = RatiosAlgorithm(
                thcStrain,
                cbdStrain,
                1.0,
                1.0
        )

        val finalMix = ratiosAlgorithm.calculateRatio()
        assertEquals(Ratio(29, 71), finalMix)

    }

    @Test
    fun testRatioReduced() {
        val unreducedRatio = Ratio(4, 8)
        val reducedRatio = unreducedRatio.reduced
        assertEquals(Ratio(1, 2), reducedRatio)
    }

    @Test
    fun testGreatestCommonDivisor() {
        val unreducedRatio = Ratio(4, 8)
        val greatestCommonDivisor = unreducedRatio.greatestCommonDivisor(4,8)
        assertEquals(4, greatestCommonDivisor)
    }

}
