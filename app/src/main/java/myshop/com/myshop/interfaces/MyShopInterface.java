package myshop.com.myshop.interfaces;

import java.util.List;

import myshop.com.myshop.models.Carrito;
import myshop.com.myshop.models.Producto;
import myshop.com.myshop.models.Respuesta;
import myshop.com.myshop.models.Usuario;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MyShopInterface {

    @GET("/productos")
    Call<List<Producto>> getProductos();

    @GET("/productos/{productId}")
    Call<Producto> getProducto(
            @Path("productId") int productId
    );

    @POST("/login")
    Call<Respuesta> login(
            @Body Usuario body
    );

    @POST("/usuarios")
    Call<Respuesta> createUser(
            @Body Usuario body
    );

    @POST("/carrito/{id}")
    Call<Respuesta> addToCart(
            @Path("id") String id,
            @Body Producto body
    );

    @GET("/carrito/{id}")
    Call<Carrito> getCarrito(
            @Path("id") String id
    );
}
