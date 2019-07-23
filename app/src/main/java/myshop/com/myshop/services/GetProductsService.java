package myshop.com.myshop.services;

import java.util.List;

import myshop.com.myshop.interfaces.MyShopInterface;
import myshop.com.myshop.models.Producto;
import myshop.com.myshop.models.Respuesta;
import myshop.com.myshop.models.Usuario;
import myshop.com.myshop.utils.Constants;
import myshop.com.myshop.utils.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetProductsService {

    public static void startService(final ProductoInterface callback){

        MyShopInterface service = RetrofitInstance
                .getInstance()
                .create(MyShopInterface.class);


        Call<List<Producto>> call = service.getProductos();
        call.enqueue(new Callback<List<Producto>>() {
            @Override
            public void onResponse(Call<List<Producto>> call, Response<List<Producto>> response) {
                if (response.isSuccessful()){
                    callback.onSuccess(response.body());
                }else {
                    callback.onFail(response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Producto>> call, Throwable t) {

            }
        });
    }

    public interface ProductoInterface{
        void onSuccess(List<Producto> productos);
        void onFail(String message);
    }
}
