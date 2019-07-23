package myshop.com.myshop.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import myshop.com.myshop.R;
import myshop.com.myshop.fragments.ProductDetailFragment;
import myshop.com.myshop.models.Carrito;
import myshop.com.myshop.models.Producto;
import myshop.com.myshop.services.AddToCartService;
import myshop.com.myshop.services.GetProductService;
import myshop.com.myshop.utils.Constants;

public class DetalleActivity extends AppCompatActivity implements GetProductService.ProductInterface, AddToCartService.AddToCartInterface {

    TextView tvCartItemCounter;
    ProductDetailFragment detailFragment;
    private ProgressBar progressBar;
    private Producto producto;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        progressBar = findViewById(R.id.pb_detail);

        int id = getIntent().getIntExtra(Constants.EXTRA_ID_PRODUCTO,1);
        userId = getIntent().getStringExtra(Constants.EXTRA_ID_USUARIO);

        GetProductService.startService(id, this);
    }

    @Override
    public void onSuccess(Producto producto) {
        this.producto = producto;
        progressBar.setVisibility(View.GONE);
        detailFragment = ProductDetailFragment.newInstance(this, producto, userId, this);
        getSupportFragmentManager().beginTransaction().add(R.id.detail_container, detailFragment).commit();
    }

    @Override
    public void onSuccess() {
        Toast.makeText(this,getString(R.string.product_added),Toast.LENGTH_SHORT);
        setResult(Constants.RESULT_OK);
        finish();
    }

    @Override
    public void onFail(String message) {

    }
}
