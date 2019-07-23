package myshop.com.myshop.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import myshop.com.myshop.R;

public class CerrarSesionDialog extends DialogFragment implements View.OnClickListener {

    private Button btnCancelar, btnCerrarSesion;
    private View.OnClickListener clickListener;

    public static CerrarSesionDialog create(View.OnClickListener clickListener){
        CerrarSesionDialog dialog = new CerrarSesionDialog();
        dialog.setClickListener(clickListener);
        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_cerrar_sesion, container);
        btnCancelar = view.findViewById(R.id.btn_dialog_cancelar);
        btnCerrarSesion = view.findViewById(R.id.btn_dialog_cerrar_sesion);

        btnCerrarSesion.setOnClickListener(clickListener);
        btnCancelar.setOnClickListener(this);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void setClickListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}
