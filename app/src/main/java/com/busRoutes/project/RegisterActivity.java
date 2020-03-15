package com.busRoutes.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.busRoutes.project.APIService.ApiInterface;
import com.busRoutes.project.APIService.RetrofitSingleton;
import com.busRoutes.project.model.ErrorResponse;
import com.busRoutes.project.model.Response;
import com.busRoutes.project.model.User;
import com.busRoutes.project.utils.Constants;
import com.busRoutes.project.utils.Validations;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class RegisterActivity extends AppCompatActivity {
    private EditText mNameEditText;
    private EditText mPasswordEditText;
    private EditText mEmailEditText;

    private TextInputLayout mNameTextInputLayout;
    private TextInputLayout mEmailTextInputLayout;
    private TextInputLayout mPasswordTextInputLayout;

    private TextView mLoginTextView;
    private Button mRegisterButton;

    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        initViews();


    }

    private void initViews(){
        mNameEditText = findViewById(R.id.edit_text_name);
        mEmailEditText = findViewById(R.id.edit_text_email);
        mPasswordEditText = findViewById(R.id.edit_text_password);

        mNameTextInputLayout = findViewById(R.id.text_input_name);
        mEmailTextInputLayout = findViewById(R.id.text_input_email);
        mPasswordTextInputLayout = findViewById(R.id.text_input_password);

        mProgressBar = findViewById(R.id.progress);

        mRegisterButton = findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        mLoginTextView = findViewById(R.id.text_view_login);
        mLoginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }

    public void register(){
        setError();
        int noErrors = 0;

        //get data
        String name = mNameEditText.getText().toString().trim();
        String email = mEmailEditText.getText().toString().trim();
        String password = mPasswordEditText.getText().toString().trim();

        //validate
        if(!Validations.validateFields(name)){
            noErrors++;
            mNameTextInputLayout.setError("Name cannot be empty");
        }
        if(!Validations.validateEmail(email)){
            noErrors++;
            mEmailTextInputLayout.setError("Invalid Email");
        }

        if(!Validations.validatePassword(password)){
            noErrors++;
            mPasswordTextInputLayout.setError("Weak Password");
        }

        if(noErrors == 0){
            User user = new User(name,email,password);
            mProgressBar.setVisibility(View.VISIBLE);
            registerUser(user);
        }else{

        }


    }


    private void registerUser(User user){
        Retrofit retrofit = RetrofitSingleton.getRetrofit(Constants.BASE_URL); //get retrofit
        ApiInterface serviceInterface = retrofit.create(ApiInterface.class);   //create apiInterface

        Call<Response> call = serviceInterface.registerUser(user);

        call.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if(!response.isSuccessful()){
                    Gson gson = new Gson();
                    ErrorResponse errorResponse = gson.fromJson(response.errorBody().charStream(),ErrorResponse.class);
                    Toast.makeText(RegisterActivity.this,errorResponse.getMessage(),Toast.LENGTH_LONG).show();
                    mProgressBar.setVisibility(View.GONE);
                    return;
                }
                String userId = response.body().getUser();
                login(userId);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(RegisterActivity.this,t.getMessage(),Toast.LENGTH_LONG);
            }
        });

    }

    private void login(String userId)
    {
        mProgressBar.setVisibility(View.GONE);
    }

    private void setError(){
        mNameTextInputLayout.setError(null);
        mEmailTextInputLayout.setError(null);
        mPasswordTextInputLayout.setError(null);
    }
}
