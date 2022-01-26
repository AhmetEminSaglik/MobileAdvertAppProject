package com.example.ilanvermemobilprojeodevi.activities.program.fragment.myadvertfragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.ilanvermemobilprojeodevi.activities.program.fragment.profil.ProfilFragment;
import com.example.ilanvermemobilprojeodevi.activities.program.mainmenu.MainMenuActivity;
import com.example.ilanvermemobilprojeodevi.db.advert.Advert;
import com.example.ilanvermemobilprojeodevi.db.user.Customer;
import com.example.ilanvermemobilprojeodevi.services.ValidationService;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class NewAdvertPageFragment extends Fragment {
    AppCompatActivity appCompatActivity;
    Customer customer;
    EditText titleEditTxt, descriptionEditTxt, priceEditTxt;
    Button btnShareAdvert, btmSelectPhoto;
    ImageView imageView;
    Bitmap bitmap;

    int SELECT_IMAGE_CODE = 1;
    Advert advert = new Advert();

    public NewAdvertPageFragment(AppCompatActivity appCompatActivity, Customer customer) {
        this.appCompatActivity = appCompatActivity;
        this.customer = customer;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.newadvert_fragment, container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        titleEditTxt = appCompatActivity.findViewById(R.id.title_newAdvertFragment);
        descriptionEditTxt = appCompatActivity.findViewById(R.id.description_newAdvertFragment);
        priceEditTxt = appCompatActivity.findViewById(R.id.price_newAdvertFragment);
        btnShareAdvert = appCompatActivity.findViewById(R.id.btnShareAdvert_newAdvertFragment);
        btmSelectPhoto = appCompatActivity.findViewById(R.id.btmSelectPhoto_newAdvertFragment);
        imageView = appCompatActivity.findViewById(R.id.imageView_newAdvertFragment);

        setBtnSelectPhotoFunction(btmSelectPhoto);
        setBtnShareAdvertFunction(btnShareAdvert);

    }

    /*
                if (validateInputValues(customer)) {
                    sendProfilUpdateRequest();
                    Fragment fragment = new ProfilFragment(appCompatActivity, customer);
                    appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ProfilFragment(appCompatActivity, customer)).commit();

                }*/
    void setBtnSelectPhotoFunction(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resimGoster();

            }
        });
    }

    void setBtnShareAdvertFunction(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                advert.setTitle(titleEditTxt.getText().toString());
                advert.setDescription(descriptionEditTxt.getText().toString());
                advert.setPrice(priceEditTxt.getText().toString());
                advert.setUserId(customer.getId());
                try {
                    new ValidationService().validateAdvertCreationInputs(advert);
                    shareAdvert();


                } catch (Exception e) {
                    Toast.makeText(appCompatActivity.getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void resimGoster() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, SELECT_IMAGE_CODE);


    }

    public String imageToString() {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byt = byteArrayOutputStream.toByteArray();
        String imageToString = android.util.Base64.encodeToString(byt, Base64.DEFAULT);
        return imageToString;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_IMAGE_CODE && resultCode == appCompatActivity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                imageView.setImageURI(uri);
                bitmap = MediaStore.Images.Media.getBitmap(appCompatActivity.getContentResolver(), uri);
                imageView.setVisibility(View.VISIBLE);
                advert.setImageString(imageToString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void shareAdvert() {


        StringRequest istek = new StringRequest(
                Request.Method.POST,
                "http://10.0.2.2:3000/api/advert", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(appCompatActivity.getBaseContext(),
                        "Advert Shared succesfully", Toast.LENGTH_SHORT).show();
                Fragment fragment = new MyAdvertFragment(appCompatActivity, customer);
                appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(appCompatActivity.getBaseContext(), "onErrorResponse  :" + error.getMessage(), Toast.LENGTH_SHORT).show();

                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    //This indicates that the reuest has either time out or there is no connection
                    System.out.println("timeout");
                } else if (error instanceof AuthFailureError) {
                    // Error indicating that there was an Authentication Failure while performing the request
                    System.out.println("AuthFailureError");

                } else if (error instanceof ServerError) {

                    // Hatayi yakalama
                    System.out.println(error.networkResponse.headers.get("hata"));
                    Toast.makeText(appCompatActivity.getBaseContext(), "Kayit basarisiz " + error.networkResponse.headers.get("hata"), Toast.LENGTH_SHORT).show();
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
                params.put("userId", advert.getUserId());
                params.put("title", advert.getTitle());
                params.put("description", advert.getDescription());
                params.put("price", advert.getPrice());
                params.put("image", advert.getImageString());
                return params;
            }
        };

        Volley.newRequestQueue(appCompatActivity.getBaseContext()).add(istek);


    }

}
