package com.udacity.shoestore.walkthrough.instruction

import androidx.navigation.ActionOnlyNavDirections
import androidx.navigation.NavDirections
import com.udacity.shoestore.R

class InstructionFragmentDirections private constructor() {
    companion object {
        fun actionInstructionFragmentToListFragment(): NavDirections =
                ActionOnlyNavDirections(R.id.action_InstructionFragment_to_ListFragment)
    }
}
