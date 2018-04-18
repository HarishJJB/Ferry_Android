package com.developeronrent.ferry.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.developeronrent.ferry.R;
import com.developeronrent.ferry.Response.LoginResponse;
import com.developeronrent.ferry.Response.RegisterResponse;
import com.developeronrent.ferry.ServerCall.ServerCall;
import com.developeronrent.ferry.Utils.CommonConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.et_phone)
    EditText Phone;
    @BindView(R.id.et_password)
    EditText Password;
    @BindView(R.id.btn_login)
    Button Login;
    @BindView(R.id.txt_forgot_pswd)
    TextView Forgot_Password;
    @BindView(R.id.txt_Register)
    TextView Register;
    ProgressDialog progressDialog = null;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        Login.setOnClickListener(this);
        Forgot_Password.setOnClickListener(this);
        Register.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Progress");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                ValidateCredentials();
                break;
            case R.id.txt_forgot_pswd:
                startActivity(new Intent(this, RegistrationActivity.class));
                break;
            case R.id.txt_Register:
                startActivity(new Intent(this, RegistrationActivity.class));
                break;
        }
    }

    private void ValidateCredentials() {

        if (TextUtils.isEmpty(Phone.getText().toString().trim())) {
            Toast.makeText(this, R.string.Phone_error, Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(Password.getText().toString().trim())) {
            Toast.makeText(this, R.string.password_error, Toast.LENGTH_SHORT).show();
            return;
        } else if (Password.getText().toString().trim().length() < 6) {
            Toast.makeText(this, R.string.error_invalid_password, Toast.LENGTH_SHORT).show();
            return;
        } else {
            ValidateLoginAPI();
        }

    }

    private void ValidateLoginAPI() {

        if (CommonConstant.isOnline(this)) {
            progressDialog.show();

            Call<LoginResponse> result = ServerCall.getInstance(context).get().Login(Phone.getText().toString(), Password.getText().toString().trim(), "");
            result.enqueue(new Callback<LoginResponse>() {

                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    if (response.body().getResponse().equalsIgnoreCase("success")) {

                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                        progressDialog.dismiss();
                        Toast.makeText(context, "Server Error !!.", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Server Error !!.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Server Error !!.", Toast.LENGTH_SHORT).show();
                }
            });

        } else{
            Toast.makeText(context, "Please check your internet connection.", Toast.LENGTH_SHORT).show();

        }

    }
}
