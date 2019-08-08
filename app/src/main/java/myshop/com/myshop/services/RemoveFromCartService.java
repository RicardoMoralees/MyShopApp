package myshop.com.myshop.services;

import myshop.com.myshop.interfaces.MyShopInterface;
import myshop.com.myshop.models.Carrito;
import myshop.com.myshop.models.Producto;
import myshop.com.myshop.models.Respuesta;
import myshop.com.myshop.utils.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoveFromCartService {

    public static void startService(Producto producto, String id, final RemoveFromCartInterface callback){

        MyShopInterface service = RetrofitInstance
                .getInstance()
                .create(MyShopInterface.class);


        Call<Carrito> call = service.removeFromCart(id, producto.getIdProducto());
        call.enqueue(new Callback<Carrito>() {
            @Override
            public void onResponse(Call<Carrito> call, Response<Carrito> response) {
                if (response.isSuccessful()){
                    callback.onSuccessRemoveCarrito(response.body());
                }else {
                    callback.onFailRemove(response.message());
                }
            }

            @Override
            public void onFailure(Call<Carrito> call, Throwable t) {

            }
        });
    }

    public interface RemoveFromCartInterface{
        void onSuccessRemoveCarrito(Carrito carrito);
        void onFailRemove(String message);
    }
}
