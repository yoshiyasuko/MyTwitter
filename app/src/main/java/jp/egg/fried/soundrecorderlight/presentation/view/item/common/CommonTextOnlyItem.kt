package jp.egg.fried.soundrecorderlight.presentation.view.item.common

import com.xwray.groupie.databinding.BindableItem
import jp.egg.fried.soundrecorderlight.R
import jp.egg.fried.soundrecorderlight.databinding.ItemCommonTextOnlyViewBinding


/**
 * CommonTextOnlyItem
 *
 * Created by Yoshiyasu on 2019-09-01
 */

class CommonTextOnlyItem(
    private val content: String
) : BindableItem<ItemCommonTextOnlyViewBinding>() {

    //region: override BindableItem
    override fun getLayout(): Int = R.layout.item_common_text_only_view

    override fun bind(viewBinding: ItemCommonTextOnlyViewBinding, position: Int) {
        viewBinding.content = content
    }
    //endregion

}