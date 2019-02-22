package sam.com.retrofitandrxjava;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RedtrofitClient {
    
    public static Retrofit getClient(){
    
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
    
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
    
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.cryptonator.com/api/full/")
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        
        return retrofit;
    }
}
