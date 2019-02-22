package sam.com.retrofitandrxjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SecondActivity extends AppCompatActivity {
    
    
    private RecyclerView mRecycleView;
    
    private Ticker ticker;
    private RecyclerViewAdapter adapter;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        getSupportActionBar().setTitle("Another Example");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    
        mRecycleView = findViewById(R.id.recyclerView);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclerViewAdapter();
        mRecycleView.setAdapter(adapter);
        
        
        APIMethods cryptocurrencyService = RedtrofitClient.getClient().create(APIMethods.class);
        
        
        Observable<ResultBody> btcObservable = cryptocurrencyService.getData("btc");
        
        Observable<ResultBody> ethObservable = cryptocurrencyService.getData("eth");
        
        Observable.merge(btcObservable, ethObservable)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .map(result -> result.getTicker())
                .subscribe(this::handleResults, this::handleError);
    }
    
    private void handleResults(Ticker ticker) {
        if (ticker.getMarkets() != null && ticker.getMarkets().size() != 0) {
            adapter.setData(ticker.getMarkets());
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.next, menu);
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.next)
            startActivity(new Intent(this, ThirdActivity.class));
        else
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
