package com.toastersocks.ratios

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_results.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

interface ResultsFragmentDelegate {
    fun newRatioButtonTappedWithState(state: ResultsFragment.State)
    fun helpTapped()
}

class ResultsFragment : Fragment() {

    var delegate: ResultsFragmentDelegate? = null

    class State(
            val forMessage: String = "",
            val mixMessage: String = ""
    )

    var state: State
        set(value) {
            launch {
                while (forMessageLabel == null) {}; launch(UI) { forMessageLabel!!.text = value.forMessage }
                println("for DONE")
            }
            launch {
                while (mixMessageLabel == null) {}; launch(UI) { mixMessageLabel!!.text = value.mixMessage }
                println("mix DONE")
            }
        }
    get() = State(forMessage = forMessageLabel?.text.toString(), mixMessage = mixMessageLabel?.text.toString())

    override fun onStart() {
        super.onStart()
        println("ONSTART STARTING")
//        forMessageLabel.text = state.forMessage
//        mixMessageLabel.text = state.mixMessage

        newRatioButton.setOnClickListener {
            delegate?.newRatioButtonTappedWithState(state)
        }
        helpButton.setOnClickListener { delegate?.helpTapped() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_results, container, false)
    }
}
