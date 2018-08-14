package com.lockwood.kaomoji.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lockwood.kaomoji.R
import com.lockwood.kaomoji.extensions.addDividerItemDecoration
import com.lockwood.kaomoji.extensions.fakeDataSet

class KaomojisFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.frag_kaomojis, container, false)
        val withFavorite = arguments?.getBoolean(ARGUMENT_FAVORITE)

        val viewManager = LinearLayoutManager(activity)
        val viewAdapter = KaomojisAdapter(fakeDataSet, withFavorite!!)
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addDividerItemDecoration()
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val category = arguments?.getString(ARGUMENT_CATEGORY)
    }

    companion object {

        const val ARGUMENT_CATEGORY = "KaomojisFragment:category"
        const val ARGUMENT_FAVORITE = "KaomojisFragment:favorite"

        fun newInstance(category: String, withFavorite: Boolean = true): KaomojisFragment {
            val fragment = KaomojisFragment()
            val args = Bundle()
            args.putString(ARGUMENT_CATEGORY, category)
            args.putBoolean(ARGUMENT_FAVORITE, withFavorite)
            fragment.arguments = args
            return fragment
        }
    }
}