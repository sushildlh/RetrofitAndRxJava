package sam.com.retrofitandrxjava;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

class Market {
    
    @SerializedName("market")
    @Expose
    private String market;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("volume")
    @Expose
    private Double volume;
    
    public String coinName;
    
    public String getCoinName() {
        if (coinName != null)
            return coinName;
        else
            return "blank one";
    }
    
    public void setCoinName(String coinName) {
        this.coinName = coinName;
    }
    
    public String getMarket() {
        return market;
    }
    
    public void setMarket(String market) {
        this.market = market;
    }
    
    public String getPrice() {
        return price;
    }
    
    public void setPrice(String price) {
        this.price = price;
    }
    
    public Double getVolume() {
        return volume;
    }
    
    public void setVolume(Double volume) {
        this.volume = volume;
    }
    
}
