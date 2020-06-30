package com.limapps.base.rest.retrofit.services;


import com.limapps.base.models.ToppingsForProduct;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ToppingsService {

    String PRODUCT_ID = "productId";
    String STORE_ID = "storeId";

    String TOPPINGS = "api/products/{" + STORE_ID + "}/{" + PRODUCT_ID + "}/topping-categories/toppings";

    @GET(TOPPINGS)
    Single<ToppingsForProduct> getToppingsByProductId(@Path(PRODUCT_ID) String productId, @Path(STORE_ID) String storeId);
}
