package com.example.myaerkande.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.myaerkande.R;
import com.example.myaerkande.entity.User;
import com.example.myaerkande.fragment.NavigationFragment;
import com.example.myaerkande.presenter.MyPresenter;
import com.example.myaerkande.presenter.MySubscriber;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    NavigationFragment navigationFragment;
    FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        navigationFragment = new NavigationFragment();
        ft.add(R.id.fram_mian,navigationFragment).commit();
    }
    /* public void doGet(View view){
         Retrofit retrofit = new Retrofit.Builder()
                 .baseUrl(baseUrl)
                 .addConverterFactory(GsonConverterFactory.create())
                 .build();
     }*/
    public void doHttpGet(View view) {
        MyPresenter.getUser("aaaa", new MySubscriber<List<User>>(this) {
                    @Override
                    public void onNext(List<User> users) {
                        System.out.println(users.get(0).getLoginId());
                    }
                }
        );
    }
}