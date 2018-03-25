package com.toastersocks.ratios

import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.util.Log

/**
 * Created by James Pamplona on 3/3/18.
 */

class Coordinator(private var fragmentManager: FragmentManager): RatiosFragmentDelegate, StrainsFragmentDelegate, ResultsFragmentDelegate {

    private var ratioState: RatiosFragment.State = RatiosFragment.State()

    fun start() {

        Log.d("d", "Start is running")
        val ratiosFragment = RatiosFragment()
        ratiosFragment.delegate = this
        // TODO: Add input validation?

        fragmentManager.beginTransaction()
                .replace(R.id.main_view, ratiosFragment)
                .addToBackStack("ratios")
                .commit()
    }

    // MARK: RatiosFragmentDelegate
    //region RatiosFragmentDelegate
    override fun nextTapped(state: RatiosFragment.State) {
        ratioState = state
        val strainsFragment = StrainsFragment()
        strainsFragment.delegate = this

        fragmentManager.beginTransaction()
                .replace(R.id.main_view, strainsFragment)
                .addToBackStack("strains")
                .commit()

    }
    //endregion


    // MARK: StrainsFragmentDelegate
    //region StrainsFragmentDelegate
    override fun nextTapped(state: StrainsFragment.State) {
        val thcStrain = Strain(state.thcStrainTHCPercentage.toDouble(),
                state.thcStrainCBDPercentage.toDouble())

        val cbdStrain = Strain(state.cbdStrainTHCPercentage.toDouble(),
                state.cbdStrainCBDPercentage.toDouble())

        val desiredTHCFactor = ratioState.thcRatio.toDouble()
        val desiredCBDFactor = ratioState.cbdRatio.toDouble()

        val ratiosAlgorithm = RatiosAlgorithm(
                thcStrain,
                cbdStrain,
                desiredTHCFactor,
                desiredCBDFactor
        )

        try {
            val finalMix = ratiosAlgorithm.calculateRatio()
            val cannabinoidPercentages = ratiosAlgorithm.cannabinoidPercentagesAtCBDRatio(ratiosAlgorithm.finalCBDMixPercentage())

            if (ratiosAlgorithm.finalCBDMixPercentage() < 0 || ratiosAlgorithm.finalTHCMixPercentage() < 0) {
                showErrordialog()
            }

            var forMessage: String

            forMessage =
                    """
                       ${ratioState.thcRatio}:${ratioState.cbdRatio} thc to cbd
                       ${"%.2f".format(cannabinoidPercentages.thc)}% thc ${"%.2f".format(cannabinoidPercentages.cbd)}% cbd
                        """.trimIndent()
            val mixMessage: String

            if (ratioState.grams.isEmpty()) {
                mixMessage =
                        """
                            ${finalMix.numerator} parts of high thc strain
                            ${finalMix.denominator} parts of high cbd strain
                            """.trimIndent()
            } else {
                val totalGrams = ratioState.grams.toDouble()
                val thcGrams = ratiosAlgorithm.finalTHCMixPercentage() * 0.01 * totalGrams
                val cbdGrams = ratiosAlgorithm.finalCBDMixPercentage() * 0.01 * totalGrams

                forMessage = "${ratioState.grams} grams of " + forMessage
                mixMessage =
                        """
                        ${"%.2f".format(thcGrams)} grams of thc strain
                        ${"%.2f".format(cbdGrams)} grams of cbd strain
                        """.trimIndent()
            }

            val results = ResultsFragment.State(forMessage, mixMessage)
            val resultsFragment = ResultsFragment()
            resultsFragment.delegate = this
            resultsFragment.state = results

            fragmentManager.beginTransaction()
                    .replace(R.id.main_view, resultsFragment)
                    .addToBackStack("results")
                    .commit()
        } catch(error: Exception) {
            Log.e("CoordinatorError", error.localizedMessage)
            Log.e("CoordinatorError", error.stackTrace[0].toString())
            showErrordialog()
        }

    }
    //endregion

    // MARK: ResultsFragmentDelegate
    //region ResultsFragmentDelegate
    override fun newRatioButtonTappedWithState(state: ResultsFragment.State) {
        fragmentManager.popBackStack("ratios", FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
    //endregion

    override fun helpTapped() {
        fragmentManager.beginTransaction()
                .replace(R.id.main_view, HelpFragment())
                .addToBackStack("help")
                .commit()
    }

    private fun showErrorDialogue() {
        val errorDialogue = DialogFragment()
        errorDialogue.dialog.setTitle("Oops!")
//        errorDialogue.dialog.set
        TODO("Not implemented")
    }

}
