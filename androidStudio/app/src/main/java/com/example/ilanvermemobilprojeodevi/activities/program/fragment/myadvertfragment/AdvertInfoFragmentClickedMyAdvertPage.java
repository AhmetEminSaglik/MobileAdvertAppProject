package com.example.ilanvermemobilprojeodevi.activities.program.fragment.myadvertfragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.example.ilanvermemobilprojeodevi.db.advert.Advert;
import com.example.ilanvermemobilprojeodevi.db.user.Customer;
import com.example.ilanvermemobilprojeodevi.services.validation.AdvertValidationService;
import com.squareup.picasso.Picasso;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class AdvertInfoFragmentClickedMyAdvertPage extends Fragment {
    AppCompatActivity appCompatActivity;
    Advert advert;
    EditText titleEditTxt, descriptionEditTxt, priceEditTxt;
    Button btnUpdateAdvert, btnDeleteAdvert, btnSelectPhoto;
    ImageView imageView;
    Customer customer;
    Bitmap bitmap;
    int SELECT_IMAGE_CODE = 1;

    public AdvertInfoFragmentClickedMyAdvertPage(AppCompatActivity activity, Advert advert, Customer customer) {
        this.appCompatActivity = activity;
        this.advert = advert;
        this.customer = customer;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.advert_info_clicked_myadvertpage, container, false);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        ReklamVerileriniYukle();

    }

    void ReklamVerileriniYukle() {
        titleEditTxt = appCompatActivity.findViewById(R.id.title_adverInfoClicked_MyAdvertPage);
        descriptionEditTxt = appCompatActivity.findViewById(R.id.description_adverInfoClicked_MyAdvertPage);
        priceEditTxt = appCompatActivity.findViewById(R.id.price_adverInfoClicked_MyAdvertPage);
        imageView = appCompatActivity.findViewById(R.id.imageView_adverInfoClicked_MyAdvertPage);

        titleEditTxt.setText(advert.getTitle());
        descriptionEditTxt.setText(advert.getDescription());
        priceEditTxt.setText(advert.getPrice());
        new DownLoadImageTask(imageView).execute("http://10.0.2.2:3000" + advert.getImageString());

        btnSelectPhoto = appCompatActivity.findViewById(R.id.btmSelectPhoto_adverInfoClicked_MyAdvertPage);
        btnUpdateAdvert = appCompatActivity.findViewById(R.id.btnUpdateAdvert_adverInfoClicked_MyAdvertPage);
        btnDeleteAdvert = appCompatActivity.findViewById(R.id.btnDeleteAdvert_adverInfoClicked_MyAdvertPage);

        setSelectPhotoFunction(btnSelectPhoto);
        setUpdateBtnFunction(btnUpdateAdvert);
        setDeleteBtnFunction(btnDeleteAdvert);
    }

    void setSelectPhotoFunction(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resimGoster();
            }
        });
    }

    void setUpdateBtnFunction(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    advert.setTitle(titleEditTxt.getText().toString());
                    advert.setDescription(descriptionEditTxt.getText().toString());
                    advert.setPrice(priceEditTxt.getText().toString());
                    if (new AdvertValidationService().validate(advert)) {
                        updateDataRequest();
                    }
                } catch (Exception e) {
                    Toast.makeText(appCompatActivity.getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    void setDeleteBtnFunction(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteDataRequest();
                switchFragmentToMyAdvertPageFragment();
            }
        });

    }


    void switchFragmentToMyAdvertPageFragment() {
        Fragment fragment = new MyAdvertFragment(appCompatActivity, customer);
        appCompatActivity.getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, fragment).commit();
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

    private class DownLoadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView) {
            this.imageView = imageView;
        }

        /*
            doInBackground(Params... params)
                Override this method to perform a computation on a background thread.
         */
        protected Bitmap doInBackground(String... urls) {
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try {
                InputStream is = new URL(urlOfImage).openStream();
                /*
                    decodeStream(InputStream is)
                        Decode an input stream into a bitmap.
                 */
                logo = BitmapFactory.decodeStream(is);
            } catch (Exception e) { // Catch the download exception

            }
            return logo;
        }

        /*
            onPostExecute(Result result)
                Runs on the UI thread after doInBackground(Params...).
         */
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SELECT_IMAGE_CODE && resultCode == appCompatActivity.RESULT_OK && data != null) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(appCompatActivity.getContentResolver(), uri);
                advert.setImageString(imageToString());

                Picasso.get().load(uri).into(imageView);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteDataRequest() {
        StringRequest istek = new StringRequest(Request.Method.DELETE, "http://10.0.2.2:3000/api/advert/" + advert.getId()
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(appCompatActivity.getBaseContext(), "Advert Deleted Succesfully", Toast.LENGTH_SHORT).show();
                switchFragmentToMyAdvertPageFragment();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(appCompatActivity.getBaseContext(), "onErrorResponse Girdi\n" + error.getMessage(), Toast.LENGTH_LONG).show();

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
        });
        Volley.newRequestQueue(appCompatActivity.getBaseContext()).add(istek);
    }

    public void updateDataRequest() {
        StringRequest istek = new StringRequest(
                Request.Method.PUT,
                "http://10.0.2.2:3000/api/advert/" + advert.getId()
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(appCompatActivity.getBaseContext(), "Advert updated Succesfully", Toast.LENGTH_SHORT).show();
                switchFragmentToMyAdvertPageFragment();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(appCompatActivity.getBaseContext(), "onErrorResponse Girdi\n" + error.getMessage(), Toast.LENGTH_LONG).show();

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
                params.put("title", advert.getTitle());
                params.put("description", advert.getDescription());
                params.put("price", advert.getPrice());
                params.put("image", advert.getImageString());
                System.out.println("params.toString() : " + params.toString());
                return params;
            }
        };
        Volley.newRequestQueue(appCompatActivity.getBaseContext()).add(istek);
    }

}
