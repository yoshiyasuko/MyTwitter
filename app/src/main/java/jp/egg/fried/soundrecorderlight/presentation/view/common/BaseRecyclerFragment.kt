package jp.egg.fried.soundrecorderlight.presentation.view.common

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder


/**
 * BaseRecyclerFragment
 *
 * Created by Yoshiyasu on 2019/02/24
 */

abstract class BaseRecyclerFragment : Fragment() {

    //region: properties
    private val groupAdapter: GroupAdapter<ViewHolder> = GroupAdapter()
    //endregion


    //region: abstracts
    abstract val layoutResourceId: Int
    abstract val recyclerViewId: Int
    abstract val gridMargin: Int

    abstract fun onLoadData()
    //endregion


    //region: override Fragment
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
            = inflater.inflate(layoutResourceId, null)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val view = this.view ?: return
        val context = this.context ?: return

        // gridMarginはリソースIDではなく値を返す
        if (gridMargin > 0x7f000000) throw AssertionError("Invalid gridMargin: $gridMargin")

        // RecyclerViewの初期化
        val recyclerView = view.findViewById<RecyclerView>(recyclerViewId) ?: return
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = groupAdapter
            isNestedScrollingEnabled = false
            // notifyItemChangedを行なった時のちらつきを抑える
            (recyclerView.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
            addItemDecoration(ItemDecoration(gridMargin))
        }

        onLoadData()
    }
    //endregion


    //region: classes
    inner class ItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            outRect.bottom = spacing
        }
    }
    //endregion

}