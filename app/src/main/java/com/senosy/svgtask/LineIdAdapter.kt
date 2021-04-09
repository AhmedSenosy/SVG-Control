package com.senosy.svgtask

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.senosy.svgtask.models.Line


class LineIdAdapter(
    private val parentActivity: LineListActivity,
    private val values: List<Line>,
    private val twoPane: Boolean
) :
    RecyclerView.Adapter<LineIdAdapter.ViewHolder>() {

    private val onClickListener: View.OnClickListener = View.OnClickListener { v ->
        val item = v.tag as Line
        if (twoPane) {
            val fragment = LineDetailFragment().apply {
                arguments = Bundle().apply {
                    putString(LineDetailFragment.ARG_ITEM_ID, item.id)
                }
            }
            parentActivity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.line_detail_container, fragment)
                .commit()
        } else {
            val intent = Intent(v.context, LineDetailActivity::class.java).apply {
                putExtra(LineDetailFragment.ARG_ITEM_ID, item.id)
            }
            v.context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.line_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.idView.text = item.id

        with(holder.itemView) {
            tag = item
            setOnClickListener(onClickListener)
        }
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val idView: TextView = view.findViewById(R.id.id_text)
    }
}