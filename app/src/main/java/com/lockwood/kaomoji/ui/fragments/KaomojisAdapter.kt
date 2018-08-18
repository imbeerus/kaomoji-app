package com.lockwood.kaomoji.ui.fragments

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lockwood.kaomoji.R
import com.lockwood.kaomoji.domain.model.KamojiListener
import com.lockwood.kaomoji.domain.model.Kaomoji
import com.lockwood.kaomoji.extensions.copyToClipboard
import com.lockwood.kaomoji.extensions.ctx
import com.lockwood.kaomoji.extensions.drawable
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_kaomoji.*
import org.jetbrains.anko.toast

class KaomojisAdapter(private val kamojisList: List<Kaomoji>, private val isFavoriteEnabled: Boolean) :
        RecyclerView.Adapter<KaomojisAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.list_item_kaomoji, parent, false)
        return ViewHolder(view, isFavoriteEnabled)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindKaomoji(kamojisList[position])
    }

    override fun getItemCount() = kamojisList.size

    class ViewHolder(override val containerView: View, private val isFavoriteEnabled: Boolean)
        : RecyclerView.ViewHolder(containerView), LayoutContainer, KamojiListener {

        override fun bindKaomoji(kaomoji: Kaomoji) {
            with(kaomoji) {
                value_tv.text = text
                if (!isFavoriteEnabled) {
                    favorite_iv.visibility = View.GONE
                } else {
                    checkState(this)
                    favorite_iv.setOnClickListener { onFavoriteClicked(itemView, this) }
                }
                itemView.setOnClickListener { onClicked(itemView, this) }
            }
        }

        override fun onClicked(view: View, kaomoji: Kaomoji) {
            with(kaomoji) {
                val text = text + "\n" + itemView.ctx.getString(R.string.action_copied)
                val label = "kamoji $text"
                itemView.ctx.toast(text).show()
                itemView.ctx.copyToClipboard(label, text)
            }
        }

        override fun onFavoriteClicked(view: View, kaomoji: Kaomoji) {
            kaomoji.isFavorite = !kaomoji.isFavorite
            checkState(kaomoji)
        }

        override fun checkState(kaomoji: Kaomoji) {
            if (kaomoji.isFavorite) {
                favorite_iv.setImageDrawable(itemView.ctx.drawable(R.drawable.ic_baseline_star_fill))
            } else {
                favorite_iv.setImageDrawable(itemView.ctx.drawable(R.drawable.ic_baseline_star_border))
            }
        }
    }
}