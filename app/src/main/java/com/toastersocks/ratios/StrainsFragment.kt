package com.toastersocks.ratios

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_strains.*

interface StrainsFragmentDelegate {
    fun nextTapped(state: StrainsFragment.State)
    fun helpTapped()
}

class StrainsFragment : Fragment() {

    class State(
            var thcStrainTHCPercentage: String = "",
            var thcStrainCBDPercentage: String = "",
            var cbdStrainTHCPercentage: String = "",
            var cbdStrainCBDPercentage: String = ""
    )

    var state: State
        set(value) {
//            field = value
            thcStrainTHCPercentageField?.setText(value.thcStrainTHCPercentage)
            thcStrainCBDPercentageField?.setText(value.thcStrainCBDPercentage)
            cbdStrainTHCPercentageField?.setText(value.cbdStrainTHCPercentage)
            cbdStrainCBDPercentageField?.setText(value.cbdStrainCBDPercentage)
        }
    get() = State(
            thcStrainTHCPercentageField.text.toString(),
            thcStrainCBDPercentageField.text.toString(),
            cbdStrainTHCPercentageField.text.toString(),
            cbdStrainCBDPercentageField.text.toString())

    var delegate: StrainsFragmentDelegate? = null


    private fun nextButtonShouldBeEnabled(): Boolean {
        return  (!state.thcStrainTHCPercentage.isEmpty() &&
                 !state.thcStrainCBDPercentage.isEmpty() &&
                 !state.cbdStrainTHCPercentage.isEmpty() &&
                 !state.cbdStrainCBDPercentage.isEmpty())

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        nextButton.isEnabled = nextButtonShouldBeEnabled()
        nextButton.setOnClickListener { delegate?.nextTapped(state) }
        helpButton.setOnClickListener { delegate?.helpTapped() }

        val onTextChange: (String) -> Unit = { nextButton.isEnabled = nextButtonShouldBeEnabled() }

        thcStrainTHCPercentageField.afterTextChange(onTextChange)
        thcStrainCBDPercentageField.afterTextChange(onTextChange)
        cbdStrainTHCPercentageField.afterTextChange(onTextChange)
        cbdStrainCBDPercentageField.afterTextChange(onTextChange)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_strains, container, false)
    }
}
