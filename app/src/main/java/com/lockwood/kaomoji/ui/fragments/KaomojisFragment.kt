package com.lockwood.kaomoji.ui.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.lockwood.kaomoji.R
import com.lockwood.kaomoji.data.KaomojiDb
import com.lockwood.kaomoji.domain.commands.RequestAllKaomojiCommand
import com.lockwood.kaomoji.domain.commands.RequestFavoriteKaomojiCommand
import com.lockwood.kaomoji.domain.commands.RequestHomeKaomojiCommand
import com.lockwood.kaomoji.domain.commands.RequestKaomojiTypeCommand
import com.lockwood.kaomoji.domain.model.Kaomoji
import com.lockwood.kaomoji.domain.model.KaomojiList
import com.lockwood.kaomoji.extensions.addDividerItemDecoration
import com.lockwood.kaomoji.extensions.copyToClipboard
import com.lockwood.kaomoji.extensions.isLastItemReached
import com.lockwood.kaomoji.ui.components.SwitchImage
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.alert
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.toast

class KaomojisFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView

    private lateinit var kaomojis: KaomojiList
    private lateinit var description: String
    private lateinit var category: String
    private lateinit var homeCategory: String
    private var isFavoriteEnabled: Boolean = false
    private var isLargeData: Boolean = false
    private var isDataLoading: Boolean = false

    private var offset: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isFavoriteEnabled = arguments?.getBoolean(ARGUMENT_FAVORITE)!!
        category = arguments?.getString(ARGUMENT_CATEGORY).toString()
        homeCategory = activity?.getPreferences(Context.MODE_PRIVATE)!!.getString(KaomojiList.PREF_TYPE, KaomojiList.DEF_TYPE_VALUE)!!
        isLargeData = category == RequestAllKaomojiCommand.LIST_TYPE || homeCategory == RequestAllKaomojiCommand.LIST_TYPE
        loadKaomoji()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.frag_kaomojis, container, false)
        recyclerView = rootView.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = LinearLayoutManager(context)
            addDividerItemDecoration()
            setHasFixedSize(!isLargeData)
        }
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (isLargeData && !isDataLoading && recyclerView.isLastItemReached()) {
                    loadAdditonalKaomoji()
                }
            }
        })
        setHasOptionsMenu(true)
        return rootView
    }

    private fun loadKaomoji() = async(UI) {
        val result = bg { commandByCategory(category) }
        updateUI(result.await())
    }

    private fun loadAdditonalKaomoji() = async(UI) {
        offset += RequestAllKaomojiCommand.LIMIT_COUNT
        isDataLoading = true
        val result = bg { commandByCategory(category) }
        updateUI(result.await(), true)
    }

    private fun commandByCategory(category: String): KaomojiList {
        return when (category) {
            RequestAllKaomojiCommand.LIST_TYPE -> RequestAllKaomojiCommand(offset).execute()
            RequestFavoriteKaomojiCommand.LIST_TYPE -> RequestFavoriteKaomojiCommand().execute()
            RequestHomeKaomojiCommand.LIST_TYPE -> commandByCategory(homeCategory)
            else -> RequestKaomojiTypeCommand(category).execute()
        }
    }

    private fun updateUI(kaomojis: KaomojiList, additional: Boolean = false) {
        if (!additional) {
            val kaomojiAdapter = KaomojisAdapter(kaomojis.kaomojiList,
                    {
                        with(it) {
                            val showText = "$text\n${getString(R.string.action_copied)}"
                            val label = "kamoji $text"
                            context!!.toast(showText).show()
                            context!!.copyToClipboard(label, text)
                        }
                    },
                    { favoriteImage: SwitchImage, kaomoji: Kaomoji ->
                        kaomoji.isFavorite = !kaomoji.isFavorite
                        KaomojiDb().updateKaomoji(kaomoji)
                        favoriteImage.changeState()
                    },
                    isFavoriteEnabled)
            recyclerView.adapter = kaomojiAdapter
            description = kaomojis.description
        } else {
            (recyclerView.adapter as KaomojisAdapter).addItems(kaomojis.kaomojiList)
            isDataLoading = false
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        if (category == RequestHomeKaomojiCommand.LIST_TYPE) {
            menu.findItem(R.id.action_home).isVisible = false
        }
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_info -> {
                context!!.alert(description).show()
                true
            }
            R.id.action_home -> {
                val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                with(sharedPref!!.edit()) {
                    putString(KaomojiList.PREF_TYPE, category)
                    apply()
                }
                context!!.toast("$category ${getString(R.string.action_set_home)}").show()
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