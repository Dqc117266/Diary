package com.example.myapplication.presentation.screen.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myapplication.data.database.model.DiaryModel;
import com.example.myapplication.databinding.ItemDiaryBinding;

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
        holder.bind(diaryModel);
    }

    class DiaryViewHolder extends RecyclerView.ViewHolder {
        private ItemDiaryBinding binding;

        public DiaryViewHolder(ItemDiaryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(DiaryModel diaryModel) {
            Glide.with(itemView.getContext()).load(diaryModel.imagePath).into(binding.image);
            binding.title.setText(diaryModel.diaryName);
            binding.date.setText(diaryModel.date);
        }
    }
}
