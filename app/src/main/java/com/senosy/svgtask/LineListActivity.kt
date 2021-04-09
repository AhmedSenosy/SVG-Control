package com.senosy.svgtask

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.NestedScrollView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.senosy.svgtask.models.Line

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [LineDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class LineListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false

    private val viewmodel: LineViewModel by lazy {
//        ViewModelProvider(this).get(LineViewModel::class.java)
        var linesRepo = LinesRepo(resources.openRawResource(R.raw.map_test))
        LineViewModel.LineViewModelFactory(linesRepo).create(LineViewModel::class.java)
    }
    private var lines = ArrayList<Line>()
    private lateinit var adapter :LineIdAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_line_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title

        if (findViewById<NestedScrollView>(R.id.line_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }
        adapter =LineIdAdapter(this, lines, twoPane)
        viewmodel.lineList.observe(this, Observer {
            lines.clear()
            lines.addAll(it)
            adapter.notifyDataSetChanged()
        })
        setupRecyclerView(findViewById(R.id.line_list))

    }

    private fun setupRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = adapter
    }

}