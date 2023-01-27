package com.example.example_recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    List<Person> personList = new ArrayList<>();
    PersonAdapter adapter = new PersonAdapter(personList);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //1. recyclerView 객체 생성
        RecyclerView recyclerView = findViewById(R.id.recyclerView1);
        //2. 객체가 놓여질 layout 객체 생성
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL,false);
        //3. recyclerView에 layout객체 연결
        recyclerView.setLayoutManager(linearLayoutManager);
        //4. Adapter 객체 생성

        //5.adapter 연결
        recyclerView.setAdapter(adapter);

        PersonService personService = Retrofit2Client.getInstance().getPersonService();
        Call<List<Person>> call = personService.findAll();

        call.enqueue(new Callback<List<Person>>() {
            @Override
            public void onResponse(Call<List<Person>> call, Response<List<Person>> response) {
                personList = response.body();
              //  Toast.makeText(MainActivity.this, personList.size(), Toast.LENGTH_SHORT).show();
                adapter.listItem(personList);


            }

            @Override
            public void onFailure(Call<List<Person>> call, Throwable t) {

            }
        });

//        for(int i=0; i<20; i++){
//            adapter.addItem(new Person("홍길동"+i,"010-1111-2222"));
//        }


        adapter.setListener(new PersonListener() {
            @Override
            public void onItemClick(int position) {
                //adaptet에서 객체에 들어있는 이름 선택
                Person person = adapter.getItem(position);

                final EditText etName = findViewById(R.id.editName);
                final EditText etPhone = findViewById(R.id.editPhone);

                etName.setText(person.getName());
                etPhone.setText(person.getPhone());

                Toast.makeText(MainActivity.this,person.getName() +" "+"선택됨",Toast.LENGTH_SHORT).show();
            }
        });



        adapter.setOnLongItemClickListener(new PersonAdapter.OnLongItemClickListener() {
            @Override
            public void onLongItemClick(int pos) {
                Person person = adapter.getItem(pos);
                Toast.makeText(MainActivity.this,person.getPhone() +" "+"선택됨",Toast.LENGTH_SHORT).show();
                adapter.removeItem(pos);
            }
        });

        Button btnInsert = findViewById(R.id.btnInsert);
        Button btnUpdate = findViewById(R.id.btnUpdate);
        Button btnDelete = findViewById(R.id.btnUpdate);

        EditText etname = findViewById(R.id.editName);
        EditText etPhone = findViewById(R.id.editPhone);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person personDto = new Person();
                personDto.setName(etname.getText().toString());
                personDto.setPhone(etPhone.getText().toString());

                PersonService personService = Retrofit2Client.getInstance().getPersonService();
                Call<Person> call = personService.save(personDto);


                call.enqueue(new Callback<Person>() {
                    @Override
                    public void onResponse(Call<Person> call, Response<Person> response) {
                        adapter.addItem(response.body());
                    }

                    @Override
                    public void onFailure(Call<Person> call, Throwable t) {

                    }
                });

            }
        });


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Person personDto = new Person();
                personDto.setName(etname.getText().toString());
                personDto.setPhone(etPhone.getText().toString());


                PersonService personService = Retrofit2Client.getInstance().getPersonService();
                Log.d("update", personDto.getId()+"");
                Call<Person> call = personService.update(personDto.getId(),personDto);
                call.enqueue(new Callback<Person>() {
                    @Override
                    public void onResponse(Call<Person> call, Response<Person> response) {

                       // adapter.updateItem(response.body(),..........);
                    }

                    @Override
                    public void onFailure(Call<Person> call, Throwable t) {

                    }
                });
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }



    }
