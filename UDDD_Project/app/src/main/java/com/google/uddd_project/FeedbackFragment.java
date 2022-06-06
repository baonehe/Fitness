package com.google.uddd_project;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import papaya.in.sendmail.SendMail;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedbackFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedbackFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FeedbackFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedbackFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedbackFragment newInstance(String param1, String param2) {
        FeedbackFragment fragment = new FeedbackFragment();
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
    TextView nameava;
    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_feedback, container, false);
        database = FirebaseDatabase.getInstance();

        String iduser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        myRef = database.getReference(iduser);
        nameava= view.findViewById(R.id.tv_nameavatar);
        ReadData();
        view.findViewById(R.id.btnsendemail).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();

            }
        });
        return view;
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(getView().getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.custom_emailcheck);
        final EditText mail = dialog.findViewById(R.id.edtgmail);
        final EditText pass = dialog.findViewById(R.id.edtpass);
        mail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        SendMail sendMail= new SendMail("giabao1352002@gmail.com","giabao@123456@",
                "20520409@gm.uit.edu.vn","FEEDBACK","HEHE");
        dialog.findViewById(R.id.btnveryfi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendMail.execute();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private  void ReadData(){
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AccountInfo post = snapshot.getValue(AccountInfo.class);
                nameava.setText(post.getFirstname());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}