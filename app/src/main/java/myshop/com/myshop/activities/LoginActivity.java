package myshop.com.myshop.activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import myshop.com.myshop.R;
import myshop.com.myshop.services.LoginService;
import myshop.com.myshop.utils.Constants;
import myshop.com.myshop.utils.Session;
import myshop.com.myshop.utils.Utils;

public class LoginActivity extends AppCompatActivity
        implements TextWatcher,
            LoginService.LoginInterface,
            View.OnClickListener {

    private EditText etUsuario;
    private EditText etContrasena;
    private View mProgressView;
    private View mLoginFormView;
    private Button btnLogin;
    private Context context;
    private TextView tvCreateUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
    }

    private void initViews(){
        etUsuario = findViewById(R.id.et_login_usuario);
        btnLogin = findViewById(R.id.btn_logIn);
        tvCreateUser = findViewById(R.id.tv_create_user);

        etContrasena = findViewById(R.id.password);
        etContrasena.addTextChangedListener(this);
        etUsuario.addTextChangedListener(this);
        etContrasena.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        tvCreateUser.setOnClickListener(this);

        btnLogin.setOnClickListener(this);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void attemptLogin() {

        // Reset errors.
        etUsuario.setError(null);
        etContrasena.setError(null);

        // Store values at the time of the login attempt.
        String email = etUsuario.getText().toString();
        String password = etContrasena.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            etContrasena.setError(getString(R.string.error_invalid_password));
            focusView = etContrasena;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            etUsuario.setError(getString(R.string.error_field_required));
            focusView = etUsuario;
            cancel = true;
        } else if (!isEmailValid(email)) {
            etUsuario.setError(getString(R.string.error_invalid_email));
            focusView = etUsuario;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            LoginService.startService(
                    etUsuario.getText().toString(),
                    etContrasena.getText().toString(),
                    this);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        Utils.hideKeyboard(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    private boolean isEmailValid(String email) {
        if (email.contains("@"))
            return true;
        else
            return false;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (etContrasena.getText().length() > 4 &&
                etUsuario.getText().length() > 3){
            btnLogin.setEnabled(true);
            btnLogin.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }else {
            btnLogin.setEnabled(false);
            btnLogin.setBackgroundColor(getResources().getColor(R.color.gris));
        }
    }

    @Override
    public void afterTextChanged(Editable s) {}

    @Override
    public void onLoginSuccess() {
        showProgress(false);
        String email = etUsuario.getText().toString();
        Session.getInstance().saveLogged(true);
        Session.getInstance().saveEmail(email);
        Intent i = new Intent(this, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    @Override
    public void onLoginFail(String message) {
        AlertDialog alertDialog =
                new AlertDialog.Builder(this)
                        .setMessage(message)
                        .setTitle(getString(R.string.error_alertDialog_title))
                        .setPositiveButton(getString(R.string.accept), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create();
        alertDialog.show();
        showProgress(false);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == tvCreateUser.getId()){
            Intent i = new Intent(this, RegistryActivity.class);
            startActivity(i);
        }
        if (view.getId() == btnLogin.getId()){
            attemptLogin();
        }
    }
}
