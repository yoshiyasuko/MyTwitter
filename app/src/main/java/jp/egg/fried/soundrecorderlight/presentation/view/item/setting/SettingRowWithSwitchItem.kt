package jp.egg.fried.soundrecorderlight.presentation.view.item.setting

import android.view.View
import com.xwray.groupie.databinding.BindableItem
import jp.egg.fried.soundrecorderlight.R
import jp.egg.fried.soundrecorderlight.databinding.ItemSettingRowViewBinding


/**
 * SettingRowWithSwitchItem
 *
 * Created by Yoshiyasu on 2019-09-01
 */

class SettingRowWithSwitchItem(
    private val content: String,
    private val description: String? = null
) : BindableItem<ItemSettingRowViewBinding>() {


    //region: override BindableItem
    override fun getLayout(): Int = R.layout.item_setting_row_view

    override fun bind(viewBinding: ItemSettingRowViewBinding, position: Int) {
        viewBinding.content = content

        description?.let {
            viewBinding.description = it
            viewBinding.descriptionLabel.visibility = View.VISIBLE
        } ?: run {
            viewBinding.descriptionLabel.visibility = View.GONE
        }
    }
    //endregion


}