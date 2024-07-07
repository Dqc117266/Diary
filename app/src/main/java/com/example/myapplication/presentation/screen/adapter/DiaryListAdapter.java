package com.example.myapplication.presentation.screen.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.myapplication.R;
import com.example.myapplication.data.database.model.DiaryModel;
import com.example.myapplication.databinding.ItemDiaryBinding;
import com.example.myapplication.presentation.screen.activity.AddDiaryActivity;
import com.example.myapplication.presentation.screen.activity.DiaryDetailActivity;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class DiaryListAdapter extends ListAdapter<DiaryModel, DiaryListAdapter.DiaryViewHolder> {

    private static final DiffUtil.ItemCallback<DiaryModel> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<DiaryModel>() {
                @Override
                public boolean areItemsTheSame(@NonNull DiaryModel oldItem, @NonNull DiaryModel newItem) {
                    return oldItem.id == newItem.id;
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull DiaryModel oldItem, @NonNull DiaryModel newItem) {
                    return oldItem.equals(newItem);
                }
            };

    private OnItemDeleteListener onItemDeleteListener;

    public void setOnItemDeleteListener(OnItemDeleteListener onItemDeleteListener) {
        this.onItemDeleteListener = onItemDeleteListener;
    }

    public DiaryListAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemDiaryBinding binding = ItemDiaryBinding.inflate(layoutInflater, parent, false);
        return new DiaryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaryViewHolder holder, int position) {
        DiaryModel diaryModel = getItem(position);
        holder.bind(diaryModel, onItemDeleteListener);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DiaryDetailActivity.class);
            intent.putExtra("diaryModel", diaryModel);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    class DiaryViewHolder extends RecyclerView.ViewHolder {
        private ItemDiaryBinding binding;

        public DiaryViewHolder(ItemDiaryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(DiaryModel diaryModel, OnItemDeleteListener onItemDeleteListener) {

            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_foreground);

            Glide.with(itemView.getContext())
                    .load(diaryModel.imagePath)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .apply(requestOptions)
                    .into(binding.image);

            binding.title.setText(diaryModel.diaryName);
            binding.date.setText(diaryModel.date);

            binding.editLayout.setOnClickListener(v -> {
                Intent intent = new Intent(itemView.getContext(), AddDiaryActivity.class);
                intent.putExtra("diaryModel", diaryModel);
                itemView.getContext().startActivity(intent);
            });

            binding.getRoot().setOnLongClickListener(v -> {
                new MaterialAlertDialogBuilder(itemView.getContext())
                        .setTitle("确认删除")
                        .setMessage("您确定要删除该日记吗？")
                        .setNegativeButton("取消", (dialog, which) -> {
                            // Do nothing
                        })
                        .setPositiveButton("确认", (dialog, which) -> {
                            if (onItemDeleteListener != null) {
                                onItemDeleteListener.onItemDelete(diaryModel);
                            }
                        })
                        .show();
                return true;
            });

        }

    }

    public interface OnItemDeleteListener {
        void onItemDelete(DiaryModel diaryModel);
    }
}
