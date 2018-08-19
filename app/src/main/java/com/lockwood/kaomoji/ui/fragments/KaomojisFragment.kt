package com.lockwood.kaomoji.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.lockwood.kaomoji.R
import com.lockwood.kaomoji.domain.commands.RequestAllKaomojiCommand
import com.lockwood.kaomoji.domain.commands.RequestFavoriteKaomojiCommand
import com.lockwood.kaomoji.domain.commands.RequestHomeKaomojiCommand
import com.lockwood.kaomoji.domain.commands.RequestKaomojiTypeCommand
import com.lockwood.kaomoji.domain.model.KaomojiList
import com.lockwood.kaomoji.extensions.addDividerItemDecoration
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.alert
import org.jetbrains.anko.coroutines.experimental.bg

class KaomojisFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var description: String
    private lateinit var category: String
    private var withFavorite: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.frag_kaomojis, container, false)
        // init fragment args
        category = arguments?.getString(ARGUMENT_CATEGORY).toString()
        withFavorite = arguments?.getBoolean(ARGUMENT_FAVORITE)!!
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            addDividerItemDecoration()
            setHasFixedSize(true)
        }
        setHasOptionsMenu(true)
        return rootView
    }

    override fun onResume() {
        super.onResume()
        loadKaomoji()
    }

    private fun loadKaomoji() = async(UI) {
        val result = bg { commandByCategory(category) }
        updateUI(result.await())
    }

    private fun commandByCategory(category: String): KaomojiList {
        return when (category) {
            RequestAllKaomojiCommand.LIST_TYPE -> RequestAllKaomojiCommand().execute()
            RequestFavoriteKaomojiCommand.LIST_TYPE -> RequestFavoriteKaomojiCommand().execute()
            RequestHomeKaomojiCommand.LIST_TYPE -> RequestHomeKaomojiCommand(category).execute()
            else -> RequestKaomojiTypeCommand(category).execute()
        }
    }

    private fun updateUI(kaomojis: KaomojiList) {
        val kaomojiAdapter = KaomojisAdapter(kaomojis.kaomojiList, withFavorite)
        recyclerView.adapter = kaomojiAdapter
        description = kaomojis.description
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_info -> {
                context!!.alert(description).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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