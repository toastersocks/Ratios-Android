package com.toastersocks.ratios

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_ratios.*

interface RatiosFragmentDelegate {
    fun nextTapped(state: RatiosFragment.State)
    fun helpTapped()
}

class RatiosFragment : Fragment() /*, RatiosFragmentDelegate by delegate*/ {

    data class State(
            var thcRatio: String = "",
            var cbdRatio: String = "",
            var grams: String = ""
    )


    var state: State
        set(value) {
//            field = value
            desiredTHCRatioField?.setText(value.thcRatio)
            desiredCBDRatioField?.setText(value.cbdRatio)
            gramsField?.setText(value.grams)
        }
        get() = State(desiredTHCRatioField.text.toString(), desiredCBDRatioField.text.toString(), gramsField.text.toString())

    var delegate: RatiosFragmentDelegate? = null

    private fun nextButtonShouldBeEnabled(): Boolean {
        Log.i("info", "state.thcRatio: ${state.thcRatio}")
        Log.i("info", "state.cbdRatio: ${state.cbdRatio}")
        return (!state.thcRatio.isEmpty() &&
                !state.cbdRatio.isEmpty())
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

        desiredTHCRatioField.afterTextChange(onTextChange)
        desiredCBDRatioField.afterTextChange(onTextChange)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_ratios, container, false)
    }
}
