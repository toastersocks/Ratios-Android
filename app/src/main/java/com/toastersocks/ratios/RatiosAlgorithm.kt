package com.toastersocks.ratios

/**
 * Created by James Pamplona on 3/1/18.
 */

import kotlin.math.roundToInt

data class Strain(
        val thc: Double,
        val cbd: Double
)

data class Ratio(val numerator: Int, val denominator: Int) {

    val reduced: Ratio
        get() {
            val gcd = greatestCommonDivisor(numerator, denominator)
            return Ratio(numerator / gcd, denominator / gcd)
        }

    fun greatestCommonDivisor(a: Int, b: Int): Int {
        if (a == 0) {
            return b
        }
        return greatestCommonDivisor(b % a, a)
    }
}


/**
 * Encapsulates the algorithms needed to calculate various properties pertaining to mixing strains of cannabis
 * */
class RatiosAlgorithm(
        val thcStrain: Strain,
        val cbdStrain: Strain,
        val desiredTHCFactor: Double,
        val desiredCBDFactor: Double) {

    private val thcYIntercept: Double = thcStrain.thc
    private val cbdYIntercept: Double = thcStrain.cbd
    private val thcSlope: Double = (cbdStrain.thc - thcStrain.thc) / 100
    private val cbdSlope: Double = (cbdStrain.cbd - thcStrain.cbd) / 100


    /**
     *
     * Calculates the percentages of cannabinoids (THC & CBD) given a percent of CBD strain in the final mix
     *
     * @param cbdRatio The ratio of CBD strain in the mix (between 0 and 100 percent)
     * @return Returns a Strain value with the percentages of THC and CBD in the mix
     */
    fun cannabinoidPercentagesAtCBDRatio(cbdRatio: Double): Strain {
        val totalFinalTHCY = thcSlope * cbdRatio + thcYIntercept
        val totalFinalCBDY = cbdSlope * cbdRatio + cbdYIntercept

        return Strain(totalFinalTHCY, totalFinalCBDY)
    }

    /**
     * Calculates the percent of CBD strain needed to get the desired ratio of CBD & THC percentages in the final mix
     *
     * @return: The percent of CBD strain which will be in the final mix
     */
    fun finalCBDMixPercentage(): Double {
        val b1 = thcStrain.cbd // CBD y-intercept
        val b2 = thcStrain.thc // THC y-intercept
        val m1 = (cbdStrain.cbd - thcStrain.cbd) / 100 // CBD slope
        val m2 = (cbdStrain.thc - thcStrain.thc) / 100 // THC slope
        // (t * b1 - c * b2) / (c * m2 - t * m1)

//        val result = (desiredTHCFactor * b1 - desiredCBDFactor * b2) / (desiredCBDFactor * m2 - desiredTHCFactor * m1)

            /*if result.isNaN {
            throw AlgorithmError.notANumber
        }*/

//        return result
        return (desiredTHCFactor * b1 - desiredCBDFactor * b2) / (desiredCBDFactor * m2 - desiredTHCFactor * m1)
    }

    /** Calculates the percent of THC strains needed to get the desired ratio of CBD & THC percentages in the final mix
     *
     * @return The percent of THC strain which will be in the final mix
     */
    fun finalTHCMixPercentage(): Double {

        return 100.0 - finalCBDMixPercentage()
    }

    fun calculateRatio(): Ratio {

        return Ratio(finalTHCMixPercentage().roundToInt(),
                            finalCBDMixPercentage().roundToInt())
                .reduced
                }
}