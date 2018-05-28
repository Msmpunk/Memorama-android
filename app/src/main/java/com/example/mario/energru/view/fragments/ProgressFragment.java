package com.example.mario.energru.view.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mario.energru.R;
import com.example.mario.energru.User;
import com.example.mario.energru.UsersAdapter;
import com.example.mario.energru.UsersService;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgressFragment extends Fragment {

    String baseUrl = "https://memorama-fi-unam.herokuapp.com/";

    private RecyclerView rvUsers;
    private ArrayList<User> list;
    private UsersAdapter adapter;

    public ProgressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container, false);


        rvUsers = (RecyclerView) view.findViewById(R.id.rvUsers);
        rvUsers.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        rvUsers.setLayoutManager(llm);

        adapter = new UsersAdapter(getActivity().getApplicationContext(), list);
        rvUsers.setAdapter(adapter);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(rvUsers.getContext(),llm.getOrientation());
        rvUsers.addItemDecoration(itemDecoration);

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        UsersService usersService =retrofit.create(UsersService.class);

        Call<User> lista = usersService.getUsers();

        lista.enqueue(new Callback<User>() {

            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()){
                    User list = response.body();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("error", t.toString());

            }
        });

        return view;
    }


}
