package com.example.ilanvermemobilprojeodevi.activities.loginsignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ilanvermemobilprojeodevi.R;
import com.example.ilanvermemobilprojeodevi.db.user.Customer;
import com.example.ilanvermemobilprojeodevi.services.ValidationService;

import java.util.HashMap;
import java.util.Map;

public class SingUpActivity extends AppCompatActivity {

    EditText usernameEditText, passwordEditText, phoneNoEditText, fullNameEditText, eMailEditText;
    ValidationService validationService = new ValidationService();
//    ICRUD icrud = Database.getCustomerDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);

        setloginPageBtnProcess(findViewById(R.id.loginPageBtn_SingUp));
        setSignUpBtnProcess(findViewById(R.id.signUpBtn_SingUp));


    }


    String getTextFromEditText(EditText editText) {
        return editText.getText().toString();
    }


    void switchActivityToNewActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);

        finish();

    }

    void setSignUpBtnProcess(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signUpUser();
//                if (validateSingUp(newCustomer)) {
//                    icrud.add(newCustomer);

//                    switchActivityToNewActivity(LoginActivity.class);
//                }

            }
        });
    }

    void signUpUser() {
        String username, password, phoneNo, fullName, eMail;
        fullNameEditText = findViewById(R.id.fullNameTxt_SingUp);
        usernameEditText = findViewById(R.id.usernameTxt_SingUp);
        passwordEditText = findViewById(R.id.passwordTxt_SingUp);
        phoneNoEditText = findViewById(R.id.phoneTxt_SingUp);
        eMailEditText = findViewById(R.id.emailTxt_SingUp);

        fullName = getTextFromEditText(fullNameEditText);
        username = getTextFromEditText(usernameEditText);
        password = getTextFromEditText(passwordEditText);
        phoneNo = getTextFromEditText(phoneNoEditText);
        eMail = getTextFromEditText(eMailEditText);
        Customer newCustomer = createCustomerFromEnteredValues(fullName, username, password, phoneNo, eMail);
        System.out.println(newCustomer.toString());


        //TODO validate yapilacak
        if (validateSingUp(newCustomer)) {
            signUpUser(getBaseContext(), newCustomer);
        }

    }

    public void signUpUser(Context context, Customer customer) {


        StringRequest istek = new StringRequest(Request.Method.POST, "http://10.0.2.2:3000/api/user", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String toastMessage = "Signed up successfully, you can log in";
                Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
                switchActivityToNewActivity(LoginActivity.class);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    //This indicates that the reuest has either time out or there is no connection
                    System.out.println("timeout");
                } else if (error instanceof AuthFailureError) {
                    // Error indicating that there was an Authentication Failure while performing the request
                    System.out.println("AuthFailureError");

                } else if (error instanceof ServerError) {

                    // Hatayi yakalama
                    System.out.println(error.networkResponse.headers.get("hata"));
                    Toast.makeText(context, "Kayit basarisiz " + error.networkResponse.headers.get("hata"), Toast.LENGTH_SHORT).show();
                } else if (error instanceof NetworkError) {
                    //Indicates that there was network error while performing the request
                    System.out.println("NetworkError");

                } else if (error instanceof ParseError) {
                    // Indicates that the server response could not be parsed
                    System.out.println("ParseError");

                }

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", customer.getUsername());
                params.put("fullName", customer.getFullName());
                params.put("password", customer.getPassword());
                params.put("phone", customer.getPhoneNo());
                params.put("mail", customer.geteMail());
                return params;
            }
        };

        Volley.newRequestQueue(context).add(istek);


    }

    Customer createCustomerFromEnteredValues(String fullName, String username, String passwordTxt, String phoneTxt, String eMail) {
        Customer customer = new Customer();
        customer.setFullName(fullName);
        customer.setUsername(username);
        customer.setPassword(passwordTxt);
        customer.setPhoneNo(phoneTxt);
        customer.seteMail(eMail);
        return customer;
    }

    void setloginPageBtnProcess(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivityToNewActivity(LoginActivity.class);
            }
        });

    }

    boolean validateSingUp(Customer customer) {
        String toastMessage;
        try {
            if (validationService.validateSingUpProcess(customer)) {
                return true;
            }
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}