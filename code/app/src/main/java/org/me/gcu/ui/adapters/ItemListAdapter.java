//S1803445
//Andrew Minja
package org.me.gcu.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bon.earthquake.databinding.ListItemBinding;
import org.me.gcu.models.Item;
import org.me.gcu.utils.ColorCode;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder> {
    private final List<Item> items;
    private final ClickListener listener;

    public ItemListAdapter(List<Item> items, ClickListener listener){
        this.items = items;
        this.listener = listener;
    }

    // create a viewholder onto which we will bind our data
    @NonNull
    @Override
    public ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ListItemBinding binding = ListItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ItemListViewHolder(binding);
    }

    // Bind the data on an existing viewHolder
    @Override
    public void onBindViewHolder(@NonNull ItemListViewHolder holder, int position) {
        holder.binding.status.setVisibility(View.VISIBLE);
        ColorCode colorCode = new ColorCode();
        Item item = items.get(position);
        colorCode.setColor(item.getMagnitude());
        holder.binding.title.setText(item.getLocation());
        holder.binding.magnitude.setText("Magnitude : "+item.getMagnitude());
        holder.binding.colorCode.getBackground().setTint(colorCode.getColor());

        switch (item.getStatus()) {
            case "":
                holder.binding.status.setVisibility(View.GONE);
                break;
            case "largest":
                holder.binding.status.setText("Largest Magnitude");
                break;
            case "deepest":
                holder.binding.status.setText("Deepest earthquake");
                break;
            case "shallowest":
                holder.binding.status.setText("Shallowest earthquake");
                break;
            default:
                holder.binding.status.setText("Most " + item.getStatus());
                break;
        }

        // implement our interface once an item is clicked
        holder.binding.getRoot().setOnClickListener(v -> {
            listener.onClick(item);
        });
    }

    // Tell the recyclerview how many items we have in the list
    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ItemListViewHolder extends RecyclerView.ViewHolder {
        public ListItemBinding binding;

        public ItemListViewHolder(@NonNull ListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
