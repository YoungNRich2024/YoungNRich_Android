package com.youngnrich.android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CardStackViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var image: ImageView = view.findViewById(R.id.investmentBehaviorTestCardImageView)
}

class CardStackAdapter(
    private var investmentBehaviorTestQuestions: List<Question> = emptyList()
): RecyclerView.Adapter<CardStackViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardStackViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return CardStackViewHolder(inflater.inflate(R.layout.item_investment_behavior_test_card, parent, false))
    }

    override fun onBindViewHolder(holder: CardStackViewHolder, position: Int) {
        val investmentBehaviorTestQuestion = investmentBehaviorTestQuestions[position]
        Glide.with(holder.image)
            .load(investmentBehaviorTestQuestion.drawableRes)
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return investmentBehaviorTestQuestions.size
    }
}