package myshop.com.myshop.services;

import myshop.com.myshop.interfaces.MyShopInterface;
import myshop.com.myshop.models.Carrito;
import myshop.com.myshop.models.Producto;
import myshop.com.myshop.utils.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetCartService {

    public static void startService(String id, final GetCarritoInterface callback){

        MyShopInterface service = RetrofitInstance
                .getInstance()
                .create(MyShopInterface.class);


        Call<Carrito> call = service.getCarrito(id);
        call.enqueue(new Callback<Carrito>() {
            @Override
            public void onResponse(Call<Carrito> call, Response<Carrito> response) {
                if (response.isSuccessful()){
                    callback.onSuccess(response.body());
                }else {
                    callback.onFail(response.message());
                }
            }

            @Override
            public void onFailure(Call<Carrito> call, Throwable t) {

            }
        });
    }

    public interface GetCarritoInterface{
        void onSuccess(Carrito Carrito);
        void onFail(String message);
    }
}
