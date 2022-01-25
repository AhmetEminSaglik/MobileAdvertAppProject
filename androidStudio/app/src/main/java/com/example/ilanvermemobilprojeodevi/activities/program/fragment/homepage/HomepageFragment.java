package com.example.ilanvermemobilprojeodevi.activities.program.fragment.homepage;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.ilanvermemobilprojeodevi.db.advert.Advert;
import com.example.ilanvermemobilprojeodevi.db.advert.AdvertAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomepageFragment extends Fragment {
    private Activity activity;
    AdvertAdapter adapter;
    ArrayList<Advert> adverList = new ArrayList<>();

    public HomepageFragment(Activity activity) {
        this.activity = activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.homepage_fragment, container, false);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        createHomePageAdverts();// benim kendi kodlarim calisiyor
    }


    public void createHomePageAdverts() {
        RecyclerView recyclerView = activity.findViewById(R.id.recycleView_HomePageFragment);


        System.out.println("recyclerView " + recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));

        verileriGetir();
        adapter = new AdvertAdapter(activity, adverList);
        recyclerView.setAdapter(adapter);


    }

    public void verileriGetir() {
//        Toast.makeText(activity.getBaseContext(), "FONKSIYONA GIRDI ", Toast.LENGTH_SHORT).show();
        StringRequest istek = new StringRequest(Request.Method.GET, "http://10.0.2.2:3000/api/advert", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    //Gelen cevabı jsonarray e çevirdik
                    JSONArray jsonArray = new JSONArray(response);


                    if (jsonArray.length() == 0) {
                        Toast.makeText(activity.getBaseContext(), "Could not find any Advert to load", Toast.LENGTH_SHORT).show();

                    } else {
//                        Toast.makeText(activity.getBaseContext(), "GELDIIII", Toast.LENGTH_SHORT).show();
                        //Gelen arrayden ilk objeyi aldık
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String id = jsonObject.getString("id");
                            String title = jsonObject.getString("title");
                            String description = jsonObject.getString("description");
                            String image = jsonObject.getString("image");
//                            String date = jsonObject.getString("updatedAt");
                            String userId = jsonObject.getString("userId");
                            String price = jsonObject.getString("price");

                            Advert advert = new Advert(id, title, description, image, price, userId);
                            advert.setClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    getActivity().getSupportFragmentManager().
                                            beginTransaction().replace(R.id.fragmentContainer, new AdvertInfoFragmentClickedHomePage(activity, advert)).commit();
                                }
                            });
                            adverList.add(advert);

//                            adverList.sort();
                        }
                        adapter.notifyDataSetChanged();
                        System.out.println("ekleme sonrasi DIZI VERI KONTROLU : " + adverList.size());
                     /*   System.out.println("++++++++++++++++++++++++++++++++" +));
                        JSONObject b = jsonArray.getJSONObject(0);

                        System.out.println("HOMEPAGEFRAGMENT  : " + b);
                        //İlk objenin id isimli value'sini aldık
                        String id = b.getString("id");
                        String username = b.getString("username");
                        String mail = b.getString("mail");
                        String fullName = b.getString("fullName");
                        String password = b.getString("password");
                        String phone = b.getString("phone");*/


                        //Veri alma
//                        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
//                        String kayitliId = prefs.getString("id", "null");


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(activity.getBaseContext(), "hata ", Toast.LENGTH_SHORT).show();

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
        })/* {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);

                return params;
            }
        }*/;

        Volley.newRequestQueue(activity.getBaseContext()).add(istek);


    }

}
