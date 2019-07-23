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
import myshop.com.myshop.models.Usuario;
import myshop.com.myshop.services.CreateUserService;
import myshop.com.myshop.utils.Constants;

public class RegistryActivity extends AppCompatActivity
        implements TextWatcher,
        CreateUserService.RegisterInterface {

    private EditText etName, etEmail, etPassword;
    private View mProgressView;
    private View mRegisterFormView;
    private Button btnRegister;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);


    }

    private void initViews(){
        etEmail = findViewById(R.id.et_registry_email);
        btnRegister = findViewById(R.id.btn_register);

        etPassword = findViewById(R.id.password);
        etPassword.addTextChangedListener(this);
        etEmail.addTextChangedListener(this);
        etPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptCreateUser();
                    return true;
                }
                return false;
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptCreateUser();
            }
        });

        mRegisterFormView = findViewById(R.id.registry_container_form);
        mProgressView = findViewById(R.id.register_progress);
    }

    private void attemptCreateUser() {

        // Reset errors.
        etEmail.setError(null);
        etPassword.setError(null);

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            etPassword.setError(getString(R.string.error_invalid_password));
            focusView = etPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            etEmail.setError(getString(R.string.error_field_required));
            focusView = etEmail;
            cancel = true;
        } else if (!isEmailValid(email)) {
            etEmail.setError(getString(R.string.error_invalid_email));
            focusView = etEmail;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            CreateUserService.startService(
                    new Usuario(
                        etEmail.getText().toString(),
                            etName.getText().toString(),
                            etPassword.getText().toString()
                    ),
                    this);
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (etPassword.getText().length() > 4 &&
                etEmail.getText().length() > 3){
            btnRegister.setEnabled(true);
            btnRegister.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }else {
            btnRegister.setEnabled(false);
            btnRegister.setBackgroundColor(getResources().getColor(R.color.gris));
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onSuccess() {
        showProgress(false);
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra(Constants.EXTRA_ID_USUARIO, etEmail.getText().toString());
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    @Override
    public void onFail(String message) {
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
}
