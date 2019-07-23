package myshop.com.myshop.services;

import com.google.gson.JsonObject;

import myshop.com.myshop.interfaces.MyShopInterface;
import myshop.com.myshop.models.Respuesta;
import myshop.com.myshop.models.Usuario;
import myshop.com.myshop.utils.Constants;
import myshop.com.myshop.utils.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateUserService {

    public static void startService(Usuario usuario, final RegisterInterface callback){

        MyShopInterface service = RetrofitInstance
                .getInstance()
                .create(MyShopInterface.class);


        Call<Respuesta> call = service.createUser(usuario);
        call.enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                if (response.body().getResponseCode() == Constants.RESPONSECODE_OK){
                    callback.onSuccess();
                }else {
                    callback.onFail(response.body().getResponseMessage());
                }
            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {

            }
        });
    }

    public interface RegisterInterface{
        void onSuccess();
        void onFail(String message);
    }
}
