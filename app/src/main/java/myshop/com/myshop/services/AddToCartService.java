package myshop.com.myshop.services;

import myshop.com.myshop.interfaces.MyShopInterface;
import myshop.com.myshop.models.Carrito;
import myshop.com.myshop.models.Producto;
import myshop.com.myshop.models.Respuesta;
import myshop.com.myshop.utils.Constants;
import myshop.com.myshop.utils.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddToCartService {

    public static void startService(Producto producto, String id, final AddToCartInterface callback){

        MyShopInterface service = RetrofitInstance
                .getInstance()
                .create(MyShopInterface.class);


        Call<Respuesta> call = service.addToCart(id, producto);
        call.enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                if (response.isSuccessful()){
                    callback.onSuccessAddCarrito();
                }else {
                    callback.onFail("El producto ya se encuentra en el carrito");
                }
            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {

            }
        });
    }

    public interface AddToCartInterface{
        void onSuccessAddCarrito();
        void onFail(String message);
    }
}
