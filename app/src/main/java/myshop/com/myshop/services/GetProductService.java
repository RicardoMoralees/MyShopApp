package myshop.com.myshop.services;

import myshop.com.myshop.interfaces.MyShopInterface;
import myshop.com.myshop.models.Producto;
import myshop.com.myshop.models.Respuesta;
import myshop.com.myshop.models.Usuario;
import myshop.com.myshop.utils.Constants;
import myshop.com.myshop.utils.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetProductService {

    public static void startService(int productId, final ProductInterface callback){

        MyShopInterface service = RetrofitInstance
                .getInstance()
                .create(MyShopInterface.class);


        Call<Producto> call = service.getProducto(productId);
        call.enqueue(new Callback<Producto>() {
            @Override
            public void onResponse(Call<Producto> call, Response<Producto> response) {
                if (response.isSuccessful()){
                    callback.onSuccess(response.body());
                }else {
                    callback.onFail(response.message());
                }
            }

            @Override
            public void onFailure(Call<Producto> call, Throwable t) {

            }
        });
    }

    public interface ProductInterface{
        void onSuccess(Producto producto);
        void onFail(String message);
    }
}
