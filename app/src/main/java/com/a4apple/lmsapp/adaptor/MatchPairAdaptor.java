package com.a4apple.lmsapp.adaptor;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.a4apple.lmsapp.R;
import com.a4apple.lmsapp.utility.AnsMatchPair;
import com.a4apple.lmsapp.utility.CustomMatchPairClick;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MatchPairAdaptor extends RecyclerView.Adapter<MatchPairAdaptor.MyHolderMatchPair> {

    private List<AnsMatchPair> pairList;

    private CustomMatchPairClick matchPairClick;

    public MatchPairAdaptor(List<AnsMatchPair> pairList,CustomMatchPairClick click) {
        this.pairList = pairList;
        this.matchPairClick=click;
    }

    public void updateData(AnsMatchPair ansMatchPair){

        notifyDataSetChanged();

    }

    public void remove(int pos){

        pairList.remove(pos);
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MyHolderMatchPair onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_match_pair_ans_list, parent, false);
        return new MyHolderMatchPair(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull MyHolderMatchPair holder, final int position) {

        holder.one.setText(pairList.get(position).getLeft());
        holder.two.setText(pairList.get(position).getRight());
        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // remove(position);
                matchPairClick.onItemClickPair(position,pairList.get(position).getLeft(),pairList.get(position).getRight());
            }
        });

    }

    @Override
    public int getItemCount() {
        return pairList.size();
    }

    class MyHolderMatchPair extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_one)
        TextView one;
        @BindView(R.id.tv_two)
        TextView two;
        @BindView(R.id.iv_close)
        ImageView close;

        public MyHolderMatchPair(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
