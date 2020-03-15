package com.busRoutes.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.busRoutes.project.APIService.ApiInterface;
import com.busRoutes.project.APIService.RetrofitSingleton;
import com.busRoutes.project.model.ErrorResponse;
import com.busRoutes.project.model.User;
import com.busRoutes.project.utils.Constants;
import com.busRoutes.project.utils.PreferenceUtils;
import com.busRoutes.project.utils.Validations;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText mPasswordEditText;
    private EditText mEmailEditText;

    private TextInputLayout mEmailTextInputLayout;
    private TextInputLayout mPasswordTextInputLayout;

    private TextView mRegisterTextView;
    private Button mLoginButton;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        initViews();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.text_view_register:
                Intent intentRegister = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(intentRegister);
                finish();
                break;

            case R.id.login_button:
                login();
                break;
        }
    }


    private void initViews(){
        mEmailEditText = findViewById(R.id.edit_text_email);
        mPasswordEditText = findViewById(R.id.edit_text_password);

        mEmailTextInputLayout = findViewById(R.id.text_input_email);
        mPasswordTextInputLayout = findViewById(R.id.text_input_password);

        mProgressBar = findViewById(R.id.progress);

        mLoginButton = findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(this);

        mRegisterTextView = findViewById(R.id.text_view_register);
        mRegisterTextView.setOnClickListener(this);

        PreferenceUtils utils = new PreferenceUtils(getApplicationContext());

        if(utils.getEmail() != null){
            //if the user already logged in go to main activity
        }else{

        }
    }

    private void setError(){
        mEmailTextInputLayout.setError(null);
        mPasswordTextInputLayout.setError(null);
    }

    private void login() {
        setError();
        int noError = 0;

        //get data
        String email = mEmailEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();

        //validate
        if(!Validations.validateEmail(email)){
            noError++;
            mEmailTextInputLayout.setError("Enter Valid email");
        }
        if(!Validations.validatePassword(password)){
            noError++;
            mPasswordTextInputLayout.setError("Password");
        }

        if(noError == 0){
            User user = new User(email,password);
            loginUser(user);
        }else{

        }


    }

    private void loginUser(final User user)
    {
        final PreferenceUtils preferenceUtils = new PreferenceUtils(getApplicationContext());
        Retrofit retrofit = RetrofitSingleton.getRetrofit(Constants.BASE_URL);
        ApiInterface serviceInterface = retrofit.create(ApiInterface.class);

        Call<ResponseBody> call = serviceInterface.loginUser(user);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(!response.isSuccessful()){
                    Gson gson = new Gson();
                    ErrorResponse errorResponse = gson.fromJson(response.errorBody().charStream(),ErrorResponse.class);
                    Toast.makeText(LoginActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
                    return;
                }

                //preferences and start main activity
                preferenceUtils.saveEmail(user.getEmail());



            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

}
