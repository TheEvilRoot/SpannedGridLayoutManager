package com.arasthel.spannedgridlayoutmanager.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.arasthel.spannedgridlayoutmanager.SpanSize
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager.Orientation.*

/**
 * Created by Jorge Martín on 24/5/17.
 */
class MainActivity: AppCompatActivity() {

    private val recyclerview: RecyclerView by lazy { findViewById<RecyclerView>(R.id.recyclerView) }
    private val adapter: GridItemAdapter by lazy { GridItemAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val spannedGridLayoutManager = SpannedGridLayoutManager(orientation = VERTICAL, spans = 4)
        spannedGridLayoutManager.itemOrderIsStable = true

        recyclerview.layoutManager = spannedGridLayoutManager

        recyclerview.addItemDecoration(SpaceItemDecorator(left = 10, top = 10, right = 10, bottom = 10))

        savedInstanceState?.getBooleanArray("clicked")?.run {
            adapter.clickedItems.clear()
            adapter.clickedItems.addAll(toList())
        }

        spannedGridLayoutManager.spanSizeLookup = SpannedGridLayoutManager.SpanSizeLookup { position ->
            if (adapter.clickedItems[position]) {
                SpanSize(2, 2)
            } else {
                SpanSize(1, 1)
            }
        }

        recyclerview.adapter = adapter
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putBooleanArray("clicked", adapter.clickedItems.toBooleanArray())

    }

}