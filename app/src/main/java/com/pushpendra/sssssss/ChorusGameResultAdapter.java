package com.pushpendra.sssssss;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChorusGameResultAdapter extends RecyclerView.Adapter<ChorusGameResultAdapter.ResultViewHolder> {
    private List<Integer> list = new ArrayList<>();


    public void refresh(List<Integer> mList) {
        list.clear();
        list.addAll(mList);
        notifyDataSetChanged();
    }

    public void refreshPartItem(int position) {
        notifyItemChanged(position, 1);
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ResultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chorus_user_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position, List payloads) {
        if (payloads.isEmpty()) {
            holder.bindData(list.get(position));
        } else {
            holder.showLikeAnim();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ResultViewHolder extends RecyclerView.ViewHolder {
        private ImageView likeIv;
        private TextView numTv;

        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);
            likeIv = itemView.findViewById(R.id.like_iv);
            numTv = itemView.findViewById(R.id.number_tv);
        }

        public void bindData(int uid) {
            numTv.setText(String.valueOf(uid));
            likeIv.setOnClickListener(v -> {
                showLikeAnim();
            });
        }

        private void showLikeAnim() {
            likeIv.setImageResource(R.drawable.chorus_like);

        }
    }
}
