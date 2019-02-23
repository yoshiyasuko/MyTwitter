package jp.egg.fried.soundrecorderlight.presentation.view.fragment.file

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.egg.fried.soundrecorderlight.R
import jp.egg.fried.soundrecorderlight.databinding.FragmentFileBinding


class FileFragment : Fragment() {

    //region: properties
    private lateinit var binding: FragmentFileBinding
    //endregion


    //region: override Fragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_file, container, false)
        return binding.root
    }
    //endregion
}