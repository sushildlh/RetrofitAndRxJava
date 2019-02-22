package sam.com.retrofitandrxjava;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIMethods {
    
    @GET("{coin}-usd")
    Observable<ResultBody> getData(@Path(value = "coin", encoded = true) String path);
}
