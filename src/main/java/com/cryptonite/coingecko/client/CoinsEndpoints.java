package com.cryptonite.coingecko.client;

public enum CoinsEndpoints {
    COINS("/coins/markets");

    private final String endpoint;

    CoinsEndpoints(String endpoint){
        this.endpoint = endpoint;
    }

    public String getEndpoint(){
        return endpoint;
    }
}
