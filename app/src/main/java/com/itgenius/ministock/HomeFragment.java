package com.itgenius.ministock;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    ImageView imgProfile;
    TextView username, fullname;
    Button btnLogout;
    SharedPreferences pref;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView =  inflater.inflate(R.layout.fragment_home, container, false);

        imgProfile = rootView.findViewById(R.id.imgProfile);
        username = rootView.findViewById(R.id.username);
        fullname  = rootView.findViewById(R.id.fullname);
        btnLogout  = rootView.findViewById(R.id.btnLogout);

        // อ่านข้อมูลจาก sharePrefference
        pref = getContext().getSharedPreferences("pref_login", Context.MODE_PRIVATE);
        username.setText(pref.getString("pref_username",""));
        fullname.setText(pref.getString("pref_fullname",""));

        // Logout ออกจากระบบ
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pref = getContext().getSharedPreferences("pref_login", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.remove("pref_username");
                editor.remove("pref_fullname");
                editor.remove("pref_imgprofile");
                editor.commit();

                // ทำการ Intent ไปหน้า MainActivity
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

}
