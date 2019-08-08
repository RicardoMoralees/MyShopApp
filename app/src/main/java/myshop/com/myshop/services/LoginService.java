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

public class LoginService {

    public static void startService(String email, String contra, final LoginInterface callback){

        MyShopInterface service = RetrofitInstance
                .getInstance()
                .create(MyShopInterface.class);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        jsonObject.addProperty("password", contra);

        Call<Respuesta> call = service.login(new Usuario(email, contra));
        call.enqueue(new Callback<Respuesta>() {
            @Override
            public void onResponse(Call<Respuesta> call, Response<Respuesta> response) {
                if (response.body().getResponseCode() == Constants.RESPONSECODE_OK){
                    callback.onLoginSuccess();
                }else {
                    callback.onLoginFail(response.body().getResponseMessage());
                }
            }

            @Override
            public void onFailure(Call<Respuesta> call, Throwable t) {

            }
        });
    }

    public interface LoginInterface{
        void onLoginSuccess();
        void onLoginFail(String message);
    }
}
