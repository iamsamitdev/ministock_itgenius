package com.itgenius.ministock;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itgenius.ministock.api.RestAPI;
import com.itgenius.ministock.api.RetrofitServer;
import com.itgenius.ministock.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    // การเรียกใช้ตัวแบบ sharePrefference
    SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // ตรวจว่าถ้ามีการล็อกอินอยู่แล้วให้ส่งไปหน้า Dashboard เลย
        pref = getSharedPreferences("pref_login", Context.MODE_PRIVATE);
        if(pref.contains("pref_username")){
            // ส่งไปหน้า Dashboard
            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
            startActivity(intent);
        }

        // ผูก View
        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);
        Button btnLogin = findViewById(R.id.btnLogin);

        // เขียนเหตุการณ์คลิ๊กปุ่ม
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // เรียกใช้งาน API Login ที่สร้างไว้ RestAPI
                RestAPI api  = RetrofitServer.getClient().create(RestAPI.class);
                Call<LoginResponse> checkLogin = api.checkLogin(
                        username.getText().toString(),
                        password.getText().toString()
                );

                checkLogin.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if(response.body().getStatus().equals("success")){
                            // login success

                            // เก็บข้อมูลลงตัวแปรแบบ SharePreferrence
                            pref = getSharedPreferences("pref_login", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = pref.edit();
                            editor.putString("pref_username", username.getText().toString());
                            editor.putString("pref_fullname", response.body().getFullname());
                            editor.putString("pref_imgprofile", response.body().getImgProfile());
                            editor.apply();

                            // เขียนคำสั่งส่งไปหน้า Dashboard
                            Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                            startActivity(intent);
                        }else{
                            // login fail
                            Toast.makeText(MainActivity.this, "ข้อมูลเข้าระบบไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "มีข้อผิดพลาด เชื่อมต่อ API ไม่ได้", Toast.LENGTH_SHORT).show();
                    }
                });

                /*
                if(username.getText().toString().equals("admin") && password.getText().toString().equals("1234")) {
                    // เขียนคำสั่งส่งไปหน้า Dashboard
                    Intent intent = new Intent(MainActivity.this, DashboardActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(MainActivity.this, "ข้อมูลเข้าระบบไม่ถูกต้อง", Toast.LENGTH_SHORT).show();
                }
                 */

            }
        });
    }
}
