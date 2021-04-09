package com.senosy.svgtask

import android.media.Image
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.senosy.svgtask.models.Line

/**
 * A fragment representing a single Line detail screen.
 * This fragment is either contained in a [LineListActivity]
 * in two-pane mode (on tablets) or a [LineDetailActivity]
 * on handsets.
 */
class LineDetailFragment : Fragment() {

    val viewmodel: LineViewModel by activityViewModels{
        var linesRepo = activity?.resources?.openRawResource(R.raw.map_test)?.let { LinesRepo(it) }
        LineViewModel.LineViewModelFactory(linesRepo!!)
    }
    private var item: Line? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                item = viewmodel.getLine((it.getString(ARG_ITEM_ID)))
                activity?.findViewById<CollapsingToolbarLayout>(R.id.toolbar_layout)?.title =
                    item?.id
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.line_detail, container, false)

        item?.let {
            rootView.findViewById<ImageView>(R.id.line_detail).setImageResource(R.drawable.ic_maptest)
        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }
}