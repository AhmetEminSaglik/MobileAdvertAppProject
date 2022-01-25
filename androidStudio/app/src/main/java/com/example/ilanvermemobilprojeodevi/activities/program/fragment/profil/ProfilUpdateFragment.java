package com.example.ilanvermemobilprojeodevi.activities.program.fragment.profil;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

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
import com.example.ilanvermemobilprojeodevi.db.user.Customer;
import com.example.ilanvermemobilprojeodevi.services.ValidationService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfilUpdateFragment extends Fragment {
    AppCompatActivity appCompatActivity;
    EditText usernameEditTxt, fullNameEditTxt, phoneNoEditTxt, eMailEditTxt, passwordEditTxt;
    Customer customer;
    Button btnUpdate;
    ValidationService validationService = new ValidationService();
    Fragment profilFragment;

    public ProfilUpdateFragment(AppCompatActivity appCompatActivity, Customer customer, Fragment profilFragment) {
        this.appCompatActivity = appCompatActivity;
        this.customer = customer;
        this.profilFragment = profilFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.profilupdate_fragment, container, false);
//        Button button = rootView.findViewById(R.id.btnUpdate_ProfilUpdateFragment);
//        setButtonFunction(button);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        usernameEditTxt = appCompatActivity.findViewById(R.id.usernametxt_profilUpdateFragment);
        fullNameEditTxt = appCompatActivity.findViewById(R.id.fullNameTxt_profilUpdateFragment);
        phoneNoEditTxt = appCompatActivity.findViewById(R.id.phoneNoTxt_profilUpdateFragment);
        eMailEditTxt = appCompatActivity.findViewById(R.id.mailTxt_profilUpdateFragment);
        passwordEditTxt = appCompatActivity.findViewById(R.id.passwordTxt_profilUpdateFragment);
        usernameEditTxt.setText(customer.getUsername());
        fullNameEditTxt.setText(customer.getFullName());
        phoneNoEditTxt.setText(customer.getPhoneNo());
        eMailEditTxt.setText(customer.geteMail());
        passwordEditTxt.setText(customer.getPassword());
        btnUpdate = appCompatActivity.findViewById(R.id.btnUpdate_ProfilUpdateFragment);

        setbtnUpdateFunction(btnUpdate);
/*        activity.findViewById(R.id.btnUpdate_ProfilUpdateFragment).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

    }

    void setbtnUpdateFunction(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customer.setUsername(usernameEditTxt.getText().toString());
                customer.setFullName(fullNameEditTxt.getText().toString());
                customer.setPassword(passwordEditTxt.getText().toString());
                customer.setPhoneNo(phoneNoEditTxt.getText().toString());
                customer.seteMail(eMailEditTxt.getText().toString());

                if (validateInputValues(customer)) {
                    sendProfilUpdateRequest();
                    Fragment fragment = new ProfilFragment(appCompatActivity, customer);
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ProfilFragment(appCompatActivity, customer)).commit();

                }
            }
        });

    }

    boolean validateInputValues(Customer customer) {

        try {
            if (validationService.validateProfilUpdateProcess(customer)) {

                return true;
            }
        } catch (Exception e) {
            Toast.makeText(appCompatActivity.getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public void sendProfilUpdateRequest() {

        StringRequest istek = new StringRequest(Request.Method.PUT, "http://10.0.2.2:3000/api/user/" + customer.getId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    //Gelen cevabı jsonarray e çevirdik

                    System.out.println("gelen cevap : " + response);
                    JSONObject jsonObject = new JSONObject(response);
                    System.out.println("+++++");
                    System.out.println(jsonObject.toString());
                    System.out.println("-----");
                    if (response.isEmpty()) {
                        Toast.makeText(appCompatActivity.getBaseContext(), "ERROR (Profil Update Fragment --> sendProfilUpdateRequest()): RESPONSE IS EMPTY ", Toast.LENGTH_SHORT).show();

                    } else {
                        System.out.println("GELEN OBJECT : " + jsonObject);
//                        System.out.println(jsonObject.getString("id"));
//                        Toast.makeText(activity.getBaseContext(), "RESPONSE IS NOT EMPTY ", Toast.LENGTH_SHORT).show();
//                        ownerEMail = jsonObject.getString("mail");
//                        ownerFullName = jsonObject.getString("fullName");
//                        ownerPhoneNo = jsonObject.getString("phone");
//                        ownerEMailTxtView.setText(ownerEMail);
//                        ownerFullNameTxtView.setText(ownerFullName);
//                        ownerPhoneNoTxtView.setText(ownerPhoneNo);
                    }
//                    if (jsonObject.length() == 0) {
//                        Toast.makeText(appCompatActivity.getBaseContext(), "json objext lenggh = 0", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(appCompatActivity.getBaseContext(), "json objext lenggh " + jsonObject.length(), Toast.LENGTH_SHORT).show();
//
//                        //Gelen arrayden ilk objeyi aldık
////                      JSONObject b = jsonObject.getJSONObject();
////                        System.out.println(b);
//
////                        ownerEMail = b.getString("mail");
////                        ownerFullName = b.getString("fullName");
////                        ownerPhoneNo = b.getString("phone");
////                        ownerEMailTxtView.setText(ownerEMail);
////                        ownerFullNameTxtView.setText(ownerFullName);
////                        ownerPhoneNoTxtView.setText(ownerPhoneNo);*//**//*
//                    }

                    Toast.makeText(appCompatActivity.getBaseContext(), " Updated Succesfully", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    System.out.println("hataya dustuuuuuuuuuuuu " + e.getMessage());
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(appCompatActivity.getBaseContext(), "ERROR : onErrorResponse: (Profil Update Fragment --> sendProfilUpdateRequest()):  Girdi\n" + error.getMessage(), Toast.LENGTH_LONG).show();

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    //This indicates that the reuest has either time out or there is no connection
                    System.out.println("timeout");
                } else if (error instanceof AuthFailureError) {
                    // Error indicating that there was an Authentication Failure while performing the request
                    System.out.println("AuthFailureError");

                } else if (error instanceof ServerError) {

                    // Hatayi yakalama
                    System.out.println(error.getMessage());
                    System.out.println(error.toString());
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
                params.put("id", customer.getId());
                params.put("username", customer.getUsername());
                params.put("fullName", customer.getFullName());
                params.put("password", customer.getPassword());
                params.put("phone", customer.getPhoneNo());
                params.put("mail", customer.geteMail());

                System.out.println("params.toString() : " + params.toString());
                return params;
            }
        };

        Volley.newRequestQueue(appCompatActivity.getBaseContext()).add(istek);


    }

}
