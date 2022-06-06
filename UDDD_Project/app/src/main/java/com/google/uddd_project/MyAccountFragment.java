package com.google.uddd_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyAccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyAccountFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyAccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MyAccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyAccountFragment newInstance(String param1, String param2) {
        MyAccountFragment fragment = new MyAccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    EditText firstname,lastname,city,country,address,age,height,weight,email;
    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_account, container, false);

        firstname = view.findViewById(R.id.edtfirstname);
        lastname= view.findViewById(R.id.edtlastname);
        city = view.findViewById(R.id.edtcity);
        country= view.findViewById(R.id.edtcountry);
        address= view.findViewById(R.id.edtaddress);
        age= view.findViewById(R.id.edtage);
        height=view.findViewById(R.id.edtheight);
        weight=view.findViewById(R.id.edtweight);
        email= view.findViewById(R.id.edtemail);

        String username= FirebaseAuth.getInstance().getCurrentUser().getEmail();
        String iduser = FirebaseAuth.getInstance().getCurrentUser().getUid();

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(iduser);
        ReadData();
        email.setText(username);

        view.findViewById(R.id.btnsavechange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if((firstname.getText().length() == 0) ||  (lastname.getText().length() == 0) || (address.getText().length() == 0) || (city.getText().length() == 0) || (country.getText().length() == 0)
                || (age.getText().length() == 0) || (height.getText().length() == 0) || (weight.getText().length() == 0))
                    Toast.makeText(view.getContext(), "Please fill full your inFormation", Toast.LENGTH_SHORT).show();
                else{
                    String Fname= firstname.getText().toString();
                    String Lname= lastname.getText().toString();
                    String Address= address.getText().toString();
                    String City = city.getText().toString();
                    String Country= country.getText().toString();
                    String Age =age.getText().toString();
                    String Height = height.getText().toString();
                    String Weight =weight.getText().toString();

                    if(!Fname.isEmpty()){
                        myRef.child("firstname").setValue(Fname);
                    }

                    if(!Lname.isEmpty()){
                        myRef.child("lastname").setValue(Lname);
                    }

                    if(!Address.isEmpty()){
                        myRef.child("address").setValue(Address);
                    }

                    if(!City.isEmpty()){
                        myRef.child("city").setValue(City);
                    }

                    if(!Country.isEmpty()){
                        myRef.child("country").setValue(Country);
                    }

                    if(!Age.isEmpty()){
                        myRef.child("age").setValue(Age);
                    }

                    if(!Height.isEmpty()){
                        myRef.child("height").setValue(Height);
                    }

                    if(!Weight.isEmpty()){
                        myRef.child("weight").setValue(Weight);
                    }
                }
            }
        });

        return view;
    }
    private  void ReadData(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AccountInfo post = snapshot.getValue(AccountInfo.class);
                firstname.setText(post.getFirstname());
                lastname.setText(post.getLastname());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}