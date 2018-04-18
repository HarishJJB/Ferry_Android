package com.developeronrent.ferry.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.et_phone)
    EditText Phone;
    @BindView(R.id.et_email)
    EditText Email;
    @BindView(R.id.et_password)
    EditText Password;
    @BindView(R.id.et_confirm_password)
    EditText ConfirmPassword;
    @BindView(R.id.btn_register)
    Button Register;
    ProgressDialog progressDialog = null;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        ButterKnife.bind(this);
        Register.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Progress");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_register:
                ValidateCredentials();
            break;

        }
    }
    private void ValidateCredentials() {
        if(TextUtils.isEmpty(Email.getText().toString().trim())){
            Toast.makeText(this, R.string.Email_error,Toast.LENGTH_SHORT).show();
            return;
        }else if(! Email.getText().toString().trim().matches(emailPattern)){
            Toast.makeText(this, R.string.error_invalid_email,Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty(Phone.getText().toString().trim())){
            Toast.makeText(this, R.string.Phone_error,Toast.LENGTH_SHORT).show();
            return;
        }else if(TextUtils.isEmpty(Password.getText().toString().trim())){
            Toast.makeText(this, R.string.password_error,Toast.LENGTH_SHORT).show();
            return;
        }else if(Password.getText().toString().trim().length()<6){
            Toast.makeText(this, R.string.error_invalid_password,Toast.LENGTH_SHORT).show();
            return;
        }else if(ConfirmPassword.getText().toString().trim().length()<6){
            Toast.makeText(this, R.string.error_invalid_password,Toast.LENGTH_SHORT).show();
            return;
        }else if(!Password.getText().toString().trim().equals(ConfirmPassword.getText().toString().trim())){
            Toast.makeText(this, R.string.error_incorrect_password,Toast.LENGTH_SHORT).show();
            return;
        }else{
            ValidateRegisterAPI();
        }

    }

    private void ValidateRegisterAPI() {
        if (CommonConstant.isOnline(this)) {
            progressDialog.show();

            Call<RegisterResponse> result = ServerCall.getInstance(context).get().Register(Email.getText().toString().trim(),Phone.getText().toString(),
                    Password.getText().toString().trim(), "2","");
            result.enqueue(new Callback<RegisterResponse>() {

                @Override
                public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                    if (response.body().getResponse().equalsIgnoreCase("success")) {

                        startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                        finish();
                        progressDialog.dismiss();
                        Toast.makeText(context, "Server Error !!.", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Server Error !!.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RegisterResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Server Error !!.", Toast.LENGTH_SHORT).show();
                }
            });

        } else{
            Toast.makeText(context, "Please check your internet connection.", Toast.LENGTH_SHORT).show();

        }

    }
}
