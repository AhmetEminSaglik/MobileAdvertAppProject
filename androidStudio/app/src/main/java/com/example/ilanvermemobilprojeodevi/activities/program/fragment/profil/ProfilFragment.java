package com.example.ilanvermemobilprojeodevi.activities.program.fragment.profil;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.ilanvermemobilprojeodevi.R;
import com.example.ilanvermemobilprojeodevi.activities.program.mainmenu.MainMenuActivity;
import com.example.ilanvermemobilprojeodevi.db.user.Customer;


public class ProfilFragment extends Fragment {
    AppCompatActivity appCompatActivity;
    Customer customer;
    //    String kayitliId, username, phone, mail, password, fullName;
    TextView usernameTxtView, phoneNoTxtView, eMailTxtView, fullNameTxtView;

    public ProfilFragment(AppCompatActivity appCompatActivity, Customer customer) {
        this.appCompatActivity = appCompatActivity;
        this.customer = customer;
//        this.kayitliId = customer.getId();
//        this.username = customer.getUsername();
//        this.phone = customer.getPhoneNo();
//        this.mail = customer.geteMail();
//        this.password = customer.getPassword();
//        this.fullName = customer.getFullName();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.profil_fragment, container, false);
        Button button = rootView.findViewById(R.id.btnUpdateProfile_ProfilFragment);
        setButtonFunction(button);


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        usernameTxtView = appCompatActivity.findViewById(R.id.username_profilFragment);
        phoneNoTxtView = appCompatActivity.findViewById(R.id.phoneNo_profilFragment);
        eMailTxtView = appCompatActivity.findViewById(R.id.eMail_profilFragment);
        fullNameTxtView = appCompatActivity.findViewById(R.id.fullName_profilFragment);

        usernameTxtView.setText(customer.getUsername());
        phoneNoTxtView.setText(customer.getPhoneNo());
        eMailTxtView.setText(customer.geteMail());
        fullNameTxtView.setText(customer.getFullName());
    }

    void setButtonFunction(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainMenuActivity) getActivity()).getSupportFragmentManager()
                        .beginTransaction().replace(R.id.fragmentContainer, new ProfilUpdateFragment(appCompatActivity, customer,ProfilFragment.this)).commit();

            }
        });

    }


}
