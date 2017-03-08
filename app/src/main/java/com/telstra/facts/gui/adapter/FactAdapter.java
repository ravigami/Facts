package com.telstra.facts.gui.adapter;

/**
 * Created by ravi.gami on 3/8/17.
 */

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.telstra.facts.R;
import com.telstra.facts.model.Fact;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FactAdapter extends RecyclerView.Adapter<FactAdapter.FactViewHolder> {

    private final Context context;
    private final List<Fact> factList;
    private LayoutInflater layoutInflater = null;

    public FactAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.factList = new ArrayList<>();
    }

    public void updateFactList(final ArrayList<Fact> factList) {
        this.factList.clear();
        this.factList.addAll(factList);
        notifyDataSetChanged();
    }

    @Override
    public FactViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        View itemView = this.layoutInflater.inflate(R.layout.fact_card_layout, viewGroup, false);
        return new FactViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FactViewHolder holder, int position) {
        Fact fact = this.factList.get(position);
        holder.itemTitleTextView.setText(fact.getTitle());
        holder.itemDescriptionTextView.setText(fact.getDescription());

        holder.itemImageView.setImageResource(R.mipmap.placeholder);
        if (!StringUtils.isEmpty(fact.getImageUrl())) {
            holder.itemImageView.setVisibility(View.VISIBLE);
            Picasso.with(context).load(fact.getImageUrl()).placeholder(R.mipmap.placeholder).into(holder.itemImageView);
        }else{
            holder.itemImageView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return this.factList.size();
    }

    public class FactViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_title)
        AppCompatTextView itemTitleTextView;
        @BindView(R.id.item_description)
        AppCompatTextView itemDescriptionTextView;
        @BindView(R.id.item_image_view)
        AppCompatImageView itemImageView;

        public FactViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}

