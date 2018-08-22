package com.lockwood.kaomoji.ui.fragments

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.lockwood.kaomoji.R
import com.lockwood.kaomoji.domain.model.Kaomoji
import com.lockwood.kaomoji.extensions.changeState
import com.lockwood.kaomoji.extensions.ctx
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.list_item_kaomoji.*

class KaomojisAdapter(private val kamojisList: ArrayList<Kaomoji>,
                      private val itemClick: (Kaomoji) -> Unit,
                      private val itemFavoriteClick: (ImageView, Kaomoji) -> Unit,
                      private val isFavoriteEnabled: Boolean) :
        RecyclerView.Adapter<KaomojisAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.ctx).inflate(R.layout.list_item_kaomoji, parent, false)
        return ViewHolder(view, itemClick, itemFavoriteClick, isFavoriteEnabled)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindKaomoji(kamojisList[position])
    }

    override fun getItemCount() = kamojisList.size

    fun addItems(listToAdd: ArrayList<Kaomoji>) {
        for (kaomoji in listToAdd) {
            kamojisList.add(kaomoji)
        }
        notifyDataSetChanged()
    }

    class ViewHolder(override val containerView: View,
                     private val itemClick: (Kaomoji) -> Unit,
                     private val itemFavoriteClick: (ImageView, Kaomoji) -> Unit,
                     private val isFavoriteEnabled: Boolean)
        : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bindKaomoji(kaomoji: Kaomoji) {
            with(kaomoji) {
                value_tv.text = text
                if (!isFavoriteEnabled) {
                    favorite_iv.visibility = View.GONE
                } else {
                    favorite_iv.changeState(isFavorite, R.drawable.ic_baseline_star_fill, R.drawable.ic_baseline_star_border)
                    favorite_iv.setOnClickListener { itemFavoriteClick(favorite_iv, this) }
                }
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}