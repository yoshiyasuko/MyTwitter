package jp.egg.fried.soundrecorderlight.presentation.view.fragment.record

import androidx.databinding.DataBindingUtil
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.egg.fried.soundrecorderlight.R
import jp.egg.fried.soundrecorderlight.databinding.FragmentRecordBinding


class RecordFragment : Fragment() {

    //region: properties
    private lateinit var binding: FragmentRecordBinding
    //endregion


    //region: override Fragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_record, container, false)
        return binding.root
    }
    //endregion
}