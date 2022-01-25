package com.example.ilanvermemobilprojeodevi.activities.program.fragment.homepage;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.ilanvermemobilprojeodevi.activities.program.fragment.myadvertfragment.AdvertInfoFragmentClickedMyAdvertPage;
import com.example.ilanvermemobilprojeodevi.activities.program.mainmenu.MainMenuActivity;
import com.example.ilanvermemobilprojeodevi.db.advert.Advert;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class AdvertInfoFragmentClickedHomePage extends Fragment {

    Activity activity;
    Advert advert;
    Button updateBtnAdvert, deleteBtnAdvert;
    String ownerFullName, ownerPhoneNo, ownerEMail;
    TextView titleTxtView, descriptionTxtView, priceTxtView, ownerFullNameTxtView, ownerPhoneNoTxtView, ownerEMailTxtView;
    ImageView imageView;

    public AdvertInfoFragmentClickedHomePage(Activity activity, Advert advert) {
        this.activity = activity;
        this.advert = advert;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.advert_info_clicked_homepage, container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        bilgileriYerlestir();
    }

    void bilgileriYerlestir() {

        titleTxtView = activity.findViewById(R.id.title_advertInfo_clickedItemFromHomePageFragment);
        descriptionTxtView = activity.findViewById(R.id.description_advertInfo_clickedItemFromHomePageFragment);
        priceTxtView = activity.findViewById(R.id.price_advertInfo_clickedItemFromHomePageFragment);
        ownerEMailTxtView = activity.findViewById(R.id.mail_advertInfo_clickedItemFromHomePageFragment);
        ownerFullNameTxtView = activity.findViewById(R.id.fullName_advertInfo_clickedItemFromHomePageFragment);
        ownerPhoneNoTxtView = activity.findViewById(R.id.phoneNo_advertInfo_clickedItemFromHomePageFragment);
        imageView = activity.findViewById(R.id.imageView_advertInfo_clickedItemFromHomePageFragment);

        titleTxtView.setText(advert.getTitle());
        descriptionTxtView.setText(advert.getDescription());
        priceTxtView.setText(advert.getPrice() + " TL");
        Picasso.get().load("http://10.0.2.2:3000" + advert.getImageString()).into(imageView);

        kullaniciBilgileriniGetir();

    }
    public void kullaniciBilgileriniGetir() {

        StringRequest istek = new StringRequest(Request.Method.GET, "http://10.0.2.2:3000/api/user/find/" + advert.getUserId(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    //Gelen cevabı jsonarray e çevirdik

                    System.out.println("gelen cevap : " + response);
                    JSONObject jsonObject = new JSONObject(response);
                    if (response.isEmpty()) {
                        Toast.makeText(activity.getBaseContext(), "BASARISIZ", Toast.LENGTH_SHORT).show();
                    } else {
                        System.out.println("GELEN OBJECT : " + jsonObject);
                        System.out.println(jsonObject.getString("id"));

                        ownerEMail = jsonObject.getString("mail");
                        ownerFullName = jsonObject.getString("fullName");
                        ownerPhoneNo = jsonObject.getString("phone");
                        ownerEMailTxtView.setText(ownerEMail);
                        ownerFullNameTxtView.setText(ownerFullName);
                        ownerPhoneNoTxtView.setText(ownerPhoneNo);
                    }
                /*    if (jsonObject.length() == 0) {
                        Toast.makeText(activity.getBaseContext(), "BASARISIZ", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(activity.getBaseContext(), "User bilgileri getirildi", Toast.LENGTH_SHORT).show();

                        //Gelen arrayden ilk objeyi aldık
                       *//* JSONObject b = jsonObject.getJSONObject();
                        System.out.println(b);

                        ownerEMail = b.getString("mail");
                        ownerFullName = b.getString("fullName");
                        ownerPhoneNo = b.getString("phone");
                        ownerEMailTxtView.setText(ownerEMail);
                        ownerFullNameTxtView.setText(ownerFullName);
                        ownerPhoneNoTxtView.setText(ownerPhoneNo);*//*
                    }*/

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(activity.getBaseContext(), "onErrorResponse Girdi\n" + error.getMessage(), Toast.LENGTH_LONG).show();

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
                params.put("id", advert.getUserId());
                System.out.println("params.toString() : " + params.toString());
                return params;
            }
        };

        Volley.newRequestQueue(activity.getBaseContext()).add(istek);


    }


}
