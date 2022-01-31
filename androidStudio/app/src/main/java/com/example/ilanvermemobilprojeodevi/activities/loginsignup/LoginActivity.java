package com.example.ilanvermemobilprojeodevi.activities.loginsignup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.ilanvermemobilprojeodevi.activities.program.mainmenu.MainMenuActivity;
import com.example.ilanvermemobilprojeodevi.services.ValidationService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    public static final String MY_PREFS_NAME = "MyPrefsFile";


    EditText usernameEditText, passwordEditText;
    ValidationService validationService = new ValidationService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameEditText = findViewById(R.id.usernameTxt_Login);
        passwordEditText = findViewById(R.id.passwordTxt_Login);

        setSingupPageBtnProcess(findViewById(R.id.singupPageBtn_Login));
        setLoginBtnProcess(findViewById(R.id.loginBtn_Login));
        iconGuncelleme();
    }

    String getTextFromEditText(EditText editText) {
        return editText.getText().toString();
    }

    void setLoginBtnProcess(Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, password;

                usernameEditText = findViewById(R.id.usernameTxt_Login);
                passwordEditText = findViewById(R.id.passwordTxt_Login);
                username = getTextFromEditText(usernameEditText);
                password = getTextFromEditText(passwordEditText);

                if (validateLoginInputValues(username, password))
                    loginToApplication(username, password);

                resetLoginInput();

            }
        });
    }

    void resetLoginInput() {
        usernameEditText.setText("");
        passwordEditText.setText("");
    }

    boolean validateLoginInputValues(String username, String password) {

        try {
            if (validationService.validateLoginProcess(username, password)) {

                return true;
            }
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    void setSingupPageBtnProcess(Button btn) {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchActivityToNewActivity(SingUpActivity.class, true);

            }
        });
    }

    void switchActivityToNewActivity(Class clazz, boolean isEnableFinishFunction) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
        if (isEnableFinishFunction)
            finish();
    }

    public void loginToApplication(String username, String password) {

        StringRequest istek = new StringRequest(Request.Method.POST, "http://10.0.2.2:3000/api/user/login", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Gelen cevabı jsonarray e çevirdik
                    JSONArray jsonArray = new JSONArray(response);

                    if (jsonArray.length() == 0) {
                        Toast.makeText(getBaseContext(), "Wrong username or password", Toast.LENGTH_SHORT).show();
                    } else {
                        //Gelen arrayden ilk objeyi aldık
                        JSONObject b = jsonArray.getJSONObject(0);
                        System.out.println(b);
                        //İlk objenin id isimli value'sini aldık
                        String id = b.getString("id");
                        String username = b.getString("username");
                        String mail = b.getString("mail");
                        String fullName = b.getString("fullName");
                        String password = b.getString("password");
                        String phone = b.getString("phone");

                        SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                        //Veri kaydetme
                        editor.putString("id", id);
                        editor.putString("username", username);
                        editor.putString("mail", mail);
                        editor.putString("fullName", fullName);
                        editor.putString("phone", phone);
                        editor.putString("password", password);
                        editor.apply();

                        switchActivityToNewActivity(MainMenuActivity.class, false);
                    }
                    System.out.println(">>>>>>>>>>>> " + jsonArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getBaseContext(), "On ErrorResponse \n", Toast.LENGTH_SHORT).show();

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    //This indicates that the reuest has either time out or there is no connection
                    System.out.println("timeout");
                } else if (error instanceof AuthFailureError) {
                    // Error indicating that there was an Authentication Failure while performing the request
                    System.out.println("AuthFailureError");

                } else if (error instanceof ServerError) {

                    // Hatayi yakalama
                    System.out.println(error.networkResponse.headers.get("hata"));
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
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };
        Volley.newRequestQueue(getBaseContext()).add(istek);
    }

    void iconGuncelleme(){
//        setContentView(R.layout.homepage_fragment);

    }
}