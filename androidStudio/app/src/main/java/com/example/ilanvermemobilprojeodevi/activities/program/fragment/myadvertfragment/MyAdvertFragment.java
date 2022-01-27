package com.example.ilanvermemobilprojeodevi.activities.program.fragment.myadvertfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.ilanvermemobilprojeodevi.db.user.Customer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MyAdvertFragment extends Fragment {
    private AppCompatActivity appCompatActivity;
    ArrayList<Advert> adverList = new ArrayList<>();
    AdvertAdapter adapter;
    Customer customer;

    public MyAdvertFragment(AppCompatActivity appCompatActivity, Customer customer) {
        this.customer = customer;
        this.appCompatActivity = appCompatActivity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.myadvert_fragment, container, false);
        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        Button button = appCompatActivity.findViewById(R.id.btnAddNewAdvert_myadvert_fragment);
        setAddAdvertBtnFunction(button);
        createHomePageAdverts();

    }

    public void setAddAdvertBtnFunction(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainMenuActivity) getActivity()).getSupportFragmentManager()
                        .beginTransaction().replace(R.id.fragmentContainer, new NewAdvertPageFragment(appCompatActivity, customer)).commit();
                System.out.println("LISTE SIZE : " + adverList.size());
            }
        });
    }

    public void createHomePageAdverts() {
        RecyclerView recyclerView = appCompatActivity.findViewById(R.id.recycleView_myAdvertFragment);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(appCompatActivity));

        adapter = new AdvertAdapter(appCompatActivity, adverList);
        recyclerView.setAdapter(adapter);

        verileriGetir();

    }

    public void verileriGetir() {
        StringRequest istek = new StringRequest(Request.Method.GET, "http://10.0.2.2:3000/api/advert/user/" + customer.getId(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //Gelen cevabı jsonarray e çevirdik
                            JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String title = jsonObject.getString("title");
                                String description = jsonObject.getString("description");
                                String image = jsonObject.getString("image");
//                        String date = jsonObject.getString("updatedAt");
                                String userId = jsonObject.getString("userId");
                                String price = jsonObject.getString("price");

                                Advert advert = new Advert(id, title, description, image, price, userId);
                                System.out.println("IMAGE : " + advert.getImageString());
                                advert.setClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        getActivity().getSupportFragmentManager().
                                                beginTransaction().replace(R.id.fragmentContainer, new AdvertInfoFragmentClickedMyAdvertPage(appCompatActivity, advert, customer)).commit();
                                    }
                                });
                                adverList.add(advert);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(appCompatActivity.getBaseContext(), "On ErrorResponse \n", Toast.LENGTH_SHORT).show();

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
        });

        Volley.newRequestQueue(appCompatActivity.getBaseContext()).add(istek);


    }


}
