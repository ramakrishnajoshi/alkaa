package com.escodro.alkaa.ui.category.create

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.navigation.fragment.NavHostFragment
import com.escodro.alkaa.R
import com.escodro.alkaa.databinding.FragmentCategoryNewBinding
import org.koin.android.architecture.ext.viewModel
import timber.log.Timber

/**
 * [Fragment] responsible to create a new [com.escodro.alkaa.data.local.model.Category].
 */
class NewCategoryFragment : Fragment() {

    private var binding: FragmentCategoryNewBinding? = null

    private val viewModel: NewCategoryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.d("onCreateView()")

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_category_new, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated()")

        binding?.setLifecycleOwner(this)
        binding?.buttonCategorynewAdd?.setOnClickListener {
            viewModel.addCategory(
                onEmptyField = { onEmptyField() },
                onCategoryAdded = { onNewCategoryAdded() },
                getCategoryColor = { getCategoryColor() })
        }
        binding?.viewModel = viewModel
    }

    private fun onEmptyField() {
        Timber.d("onEmptyField()")

        binding?.edittextCategorynewDescription?.error = getString(R.string.task_error_empty)
    }

    private fun onNewCategoryAdded() {
        Timber.d("onNewCategoryAdded()")

        binding?.edittextCategorynewDescription?.text = null
        NavHostFragment.findNavController(this).navigateUp()
    }

    private fun getCategoryColor(): String? {
        Timber.d("getCategoryColor()")

        val radioGroup = binding?.radiogroupCategorynewLabel
        val checkedId = radioGroup?.checkedRadioButtonId
        val checked = checkedId?.let { radioGroup.findViewById<RadioButton>(it) }

        return checked?.tag as? String?
    }
}
