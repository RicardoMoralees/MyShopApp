package myshop.com.myshop.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Carrito implements Serializable {

    @SerializedName("idCarritoUsuario")
    private String idCarritoUsuario;

    @SerializedName("productoList")
    private List<Producto> productoList;

    @SerializedName("total")
    private double total;

    public Carrito() {
    }

    public String getIdCarritoUsuario() {
        return idCarritoUsuario;
    }

    public void setIdCarritoUsuario(String idCarritoUsuario) {
        this.idCarritoUsuario = idCarritoUsuario;
    }

    public List<Producto> getProductoList() {
        return productoList;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
