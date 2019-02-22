package sam.com.retrofitandrxjava;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ThirdActivity extends AppCompatActivity {
    
    
    private RecyclerView mRecycleView;
    
    private Ticker ticker;
    private RecyclerViewAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        mRecycleView = findViewById(R.id.recyclerView);
        getSupportActionBar().setTitle("Third Example");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter();
        mRecycleView.setAdapter(adapter);
        
        
        APIMethods cryptocurrencyService = RedtrofitClient.getClient().create(APIMethods.class);
        
        Observable<List<Market>> btcObservable = cryptocurrencyService.getData("btc")
                .map(result -> Observable.fromIterable(result.getTicker().getMarkets()))
                .flatMap(x -> x).filter(y -> {
                    y.coinName = "btc";
                    return true;
                }).toList().toObservable();
        
        Observable<List<Market>> ethObservable = cryptocurrencyService.getData("eth")
                .map(result -> Observable.fromIterable(result.getTicker().getMarkets()))
                .flatMap(x -> x).filter(y -> {
                    y.coinName = "eth";
                    return true;
                }).toList().toObservable();
        
        Observable.merge(btcObservable, ethObservable)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResults, this::handleError);
        
        
    }
    
    private void handleResults(List<Market> markets) {
        if (markets != null && markets.size() != 0) {
            adapter.setData(markets);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "NO RESULTS FOUND",
                    Toast.LENGTH_LONG).show();
        }
        
    }
    
    private void handleError(Throwable t) {
        Toast.makeText(this, "ERROR IN FETCHING API RESPONSE. Try again " + t.getMessage(),
                Toast.LENGTH_LONG).show();
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
    
}
