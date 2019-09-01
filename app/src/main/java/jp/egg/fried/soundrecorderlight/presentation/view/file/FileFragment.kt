package jp.egg.fried.soundrecorderlight.presentation.view.file

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import jp.egg.fried.soundrecorderlight.R
import jp.egg.fried.soundrecorderlight.databinding.FragmentFileBinding
import jp.egg.fried.soundrecorderlight.utility.extension.updateToolbar


class FileFragment : Fragment() {

    //region: properties
    private lateinit var binding: FragmentFileBinding
    //endregion


    //region: override Fragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_file, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updateToolbar(R.string.menu_tab_name_file, false)
    }
    //endregion
}