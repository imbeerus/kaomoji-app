package com.lockwood.kaomoji.ui.fragments

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lockwood.kaomoji.R
import com.lockwood.kaomoji.extensions.ctx
import com.lockwood.kaomoji.extensions.drawable
import com.lockwood.kaomoji.models.Kaomoji
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_kaomoji.*

class KaomojisAdapter(private val kamojisList: ArrayList<Kaomoji>,
                      private val itemClick: (Kaomoji) -> Unit) :
        RecyclerView.Adapter<KaomojisAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.list_item_kaomoji, parent, false)
        return ViewHolder(view, itemClick)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindKaomoji(kamojisList[position])
    }

    override fun getItemCount() = kamojisList.size

    class ViewHolder(override val containerView: View, private val itemClick: (Kaomoji) -> Unit)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindKaomoji(kamoji: Kaomoji) {
            with(kamoji) {
                value_tv.text = value
                if (isFavorite) {
                    favorite_iv.setImageDrawable(itemView.ctx.drawable(R.drawable.ic_baseline_star_fill))
                }
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}