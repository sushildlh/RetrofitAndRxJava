package sam.com.retrofitandrxjava;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    
    private List<Market> marketList;
    
    
    public RecyclerViewAdapter() {
        marketList = new ArrayList<>();
    }
    
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item_layout, parent, false);
        
        RecyclerViewAdapter.ViewHolder viewHolder = new RecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }
    
    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        Market market = marketList.get(position);
        holder.txtCoin.setText(market.getCoinName());
        holder.txtMarket.setText(market.getMarket());
        holder.txtPrice.setText("$" + String.format("%.2f", Double.parseDouble(market.getPrice())));
        if (market.getCoinName().equalsIgnoreCase("eth")) {
            holder.cardView.setCardBackgroundColor(Color.GRAY);
        } else {
            holder.cardView.setCardBackgroundColor(Color.GREEN);
        }
    }
    
    @Override
    public int getItemCount() {
        return marketList.size();
    }
    
    public void setData(List<Market> data) {
        this.marketList.addAll(data);
        notifyDataSetChanged();
    }
    
    public class ViewHolder extends RecyclerView.ViewHolder {
        
        public TextView txtCoin;
        public TextView txtMarket;
        public TextView txtPrice;
        public CardView cardView;
        
        public ViewHolder(View view) {
            super(view);
            
            txtCoin = view.findViewById(R.id.coin);
            txtMarket = view.findViewById(R.id.market);
            txtPrice = view.findViewById(R.id.price);
            cardView = view.findViewById(R.id.cardView);
        }
    }
}
