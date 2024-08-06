package com.test.flagschallenge.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.test.flagschallenge.R
import com.test.flagschallenge.databinding.FragmentSetTimeBinding
import com.test.flagschallenge.exts.getPreference
import com.test.flagschallenge.ui.base.BaseFragment

class SetTimeFragment : BaseFragment() {

    private lateinit var binding:FragmentSetTimeBinding


    /*private var listener:TextWatcher = object : TextWatcher{
        private var oldText = ""
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            oldText = p0?.toString() ?: ""
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if (!p0.isNullOrEmpty()){
                if (p0.length ==1){
                    getNextFocusEditText()?.requestFocus()
                }else if (p0.length > 1){
                    val curFoc = requireActivity().currentFocus
                    if (curFoc is EditText){
                        curFoc.setText(p0.toString().replace(oldText,""))
                    }
                }
            }
        }

        override fun afterTextChanged(p0: Editable?) {

        }

    }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentSetTimeBinding.inflate(
            inflater,container,false
        )
        /*binding.et1Hr.addTextChangedListener(listener)
        binding.et2Hr.addTextChangedListener(listener)
        binding.et1Min.addTextChangedListener(listener)
        binding.et2Min.addTextChangedListener(listener)
        binding.et1Sec.addTextChangedListener(listener)
        binding.et2Sec.addTextChangedListener(listener)*/

        binding.btnSave.setOnClickListener {
            val timePicked = binding.timePickerView.getTime()
            if (timePicked != null){
                val time = getViewModel().getTimeInMillis(timePicked.hour,timePicked.minute,timePicked.second)
                if (time > 0 && getViewModel().isAfterNow(time)){
                    getViewModel().updateStartTime(requireContext(),time)
                    changeFragment(TimerFragment())
                    return@setOnClickListener
                }
            }
            Toast.makeText(requireContext(), "Wrong date", Toast.LENGTH_SHORT).show()
        }

        /*binding.btnSave.setOnClickListener {
            val hr = "${binding.et1Hr.text}${binding.et2Hr.text}"
            val min = "${binding.et1Min.text}${binding.et2Min.text}"
            val sec = "${binding.et1Sec.text}${binding.et2Sec.text}"
            val time = getViewModel().validateTime(hr, min, sec)
            if (time > 0){
                requireContext().getPreference().saveTime(time)
                changeFragment(TimerFragment())
            }else{
                Toast.makeText(requireContext(), "Wrong date", Toast.LENGTH_SHORT).show()
            }
        }*/

        return binding.root
    }

    /*private fun getNextFocusEditText():EditText?{
        val vw = requireActivity().currentFocus ?: return null
        return when(vw.id){
            R.id.et1_hr -> {
                binding.et2Hr
            }
            R.id.et2_hr -> {
                binding.et1Min
            }
            R.id.et1_min -> {
                binding.et2Min
            }
            R.id.et2_min -> {
                binding.et1Sec
            }
            R.id.et1_sec -> {
                binding.et2Sec
            }
            else -> {
                null
            }
        }
    }*/

}