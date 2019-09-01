package jp.egg.fried.soundrecorderlight.presentation.view.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.SimpleItemAnimator
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import jp.egg.fried.soundrecorderlight.R
import jp.egg.fried.soundrecorderlight.databinding.FragmentSettingBinding
import jp.egg.fried.soundrecorderlight.presentation.view.item.decoration.SimpleItemDecoration
import jp.egg.fried.soundrecorderlight.presentation.view.item.setting.SettingRowItem
import jp.egg.fried.soundrecorderlight.utility.extension.updateToolbar


class SettingFragment : Fragment() {

    //region: properties
    private lateinit var binding: FragmentSettingBinding
    //endregion

    //region: groupie components
    private val groupAdapter = GroupAdapter<ViewHolder>()
    //endregion


    //region: override Fragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        updateToolbar(R.string.menu_tab_name_setting, false)

        // RecyclerViewの初期設定
        with(binding.recyclerView) {
            adapter = groupAdapter
            // Item更新時の抑える
            (itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
            addItemDecoration(
                SimpleItemDecoration(
                    dividerStroke = SimpleItemDecoration.DividerStroke()
                )
            )
        }

        bindItems()

    }
    //endregion


    //region: private methods
    private fun bindItems() {
        val items = (0 until 20).map {
            SettingRowItem("設定${it + 1}", "設定の説明${it + 1}")
        }

        groupAdapter.update(items)
    }
    //endregion
}