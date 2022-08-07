package com.example.homely.Chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.homely.Adapter.ChatAdapter;
import com.example.homely.Contractor.CheckBidingActivity;
import com.example.homely.LoginActivity;
import com.example.homely.Model.Global;
import com.example.homely.Model.MessageModel;
import com.example.homely.R;
import com.example.homely.User.UserDashboardActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChattingActivity extends AppCompatActivity {

    FirebaseDatabase database;
    FirebaseAuth auth;
    RecyclerView recyclerView;
    EditText etMsg;
    ImageView btn_send;
    ImageView btn_auction;
    ImageView img_toolbar;
    TextView name_toolbar;
    String BID_AMOUNT;
    String CONTRACTOR_NAME;
    LinearLayout toolbar;
    String CONTRACTOR_COMPANY_NAME;
    ImageView img_back;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        getSupportActionBar().hide();

        String receiverId = getIntent().getExtras().getString("receiverId");
        String projectId = getIntent().getExtras().getString("projectId");
        String userName = getIntent().getExtras().getString("userName");

        Global.RECEIVER_ID = receiverId;
        String projectName = getIntent().getExtras().getString("projectName");
        toolbar = (LinearLayout) findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ChattingActivity.this,ChatProfileActivity.class));
            }
        });

        img_back = (ImageView) findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LoginActivity.userTypes==1){
                    Intent intent = new Intent(ChattingActivity.this, CheckBidingActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(ChattingActivity.this, UserDashboardActivity.class);
                    startActivity(intent);
                }
            }
        });

        btn_auction = (ImageView) findViewById(R.id.btn_auction);
        img_toolbar = (ImageView) findViewById(R.id.img_toolbar);
        name_toolbar = (TextView) findViewById(R.id.name_toolbar);
        name_toolbar.setText(userName);
        if (Global.BID_AVAILABLE=="yes"){
            btn_auction.setVisibility(View.VISIBLE);
        }



        //Toast.makeText(this, userName, Toast.LENGTH_SHORT).show();
        //getActionBar().setTitle(receiverId);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_chat);
        etMsg = (EditText) findViewById(R.id.msg_type);
        btn_send = (ImageView) findViewById(R.id.btn_send_msg);

        database.getReference().child("User").child(auth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        CONTRACTOR_NAME = snapshot.child("fullname").getValue(String.class);
                        CONTRACTOR_COMPANY_NAME = snapshot.child("companyName").getValue(String.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


        final ArrayList<MessageModel>messageModels = new ArrayList<>();

        final ChatAdapter chatAdapter = new ChatAdapter(messageModels,this);
        recyclerView.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final String senderRoom = auth.getCurrentUser().getUid()+receiverId;
        final String receiverRoom = receiverId+auth.getCurrentUser().getUid();


        btn_auction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ChattingActivity.this);


// Set up the input
                View viewInflated = LayoutInflater.from(ChattingActivity.this).inflate(R.layout.alert_bid_layout, (ViewGroup) getWindow().getDecorView().getRootView(), false);
// Set up the input
                final EditText input = (EditText) viewInflated.findViewById(R.id.input);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                builder.setView(viewInflated);

// Set up the buttons
                builder.setPositiveButton("BID", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        BID_AMOUNT = input.getText().toString().trim();
                        if (BID_AMOUNT!="") {
                            String date = DateFormat.getDateInstance().format(new Date());

                            Map<String,Object>map =new HashMap<>();
                            map.put("amount",BID_AMOUNT);
                            map.put("id",auth.getCurrentUser().getUid());
                            map.put("status","pending");
                            map.put("fullname",UserDashboardActivity.userData.getFullname());
                            map.put("companyName", UserDashboardActivity.userData.getCompanyName());
                            map.put("projectName", projectName);
                            map.put("date",date);

                            database.getReference("Project Post").child(projectId).child("Biding")
                                    .child(auth.getCurrentUser().getUid()).setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Map<String,Object>map1 = new HashMap<>();
                                            map1.put("amount",BID_AMOUNT);
                                            map1.put("status","pending");
                                            map1.put("projectName",projectName);
                                            map1.put("userName",userName);
                                            map1.put("date",date);

                                            database.getReference("User").child(auth.getCurrentUser().getUid())
                                                            .child("Bid").child(projectId).setValue(map1).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            Toast.makeText(ChattingActivity.this, "Bid Sucessfull", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }).addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            Toast.makeText(ChattingActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(ChattingActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });

                            String MESSAGE = CONTRACTOR_NAME+" from " + CONTRACTOR_COMPANY_NAME +" just Bid your project " + projectName + " at Price "+BID_AMOUNT+" CAD";

                            final MessageModel model = new MessageModel(auth.getCurrentUser().getUid(), MESSAGE);
                            sendMSG(senderRoom, receiverRoom, model,receiverId);
                        }else {
                            Toast.makeText(ChattingActivity.this, "Enter Bid Amount", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });



        database.getReference().child("User").child(auth.getCurrentUser().getUid())
                        .child("chat").child(receiverId).child(senderRoom)
                        .addValueEventListener(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                messageModels.clear();
                                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                                    MessageModel model = snapshot1.getValue(MessageModel.class);
                                    messageModels.add(model);
                                }
                                chatAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String MSG =   etMsg.getText().toString().trim();
              final MessageModel model = new MessageModel(auth.getCurrentUser().getUid(),MSG );
              etMsg.setText("");

              sendMSG(senderRoom,receiverRoom,model,receiverId);


            }
        });





    }

    private void sendMSG(String senderRoom, String receiverRoom, MessageModel model, String receiverId) {

        database.getReference().child("User").child(auth.getCurrentUser().getUid())
                .child("chat").child(receiverId).child(senderRoom)
                .push()
                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        database.getReference().child("User").child(auth.getCurrentUser().getUid())
                                .child("chat").child(receiverId).child(receiverRoom)
                                .push()
                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        database.getReference().child("User").child(receiverId)
                                                .child("chat").child(auth.getCurrentUser().getUid())
                                                .child(senderRoom)
                                                .push()
                                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {

                                                        database.getReference().child("User").child(receiverId)
                                                                .child("chat").child(auth.getCurrentUser().getUid())
                                                                .child(receiverRoom)
                                                                .push()
                                                                .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                    @Override
                                                                    public void onSuccess(Void unused) {
                                                                        Toast.makeText(ChattingActivity.this, "msg sent", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });

                                                    }
                                                });

                                    }
                                });
                    }
                });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (LoginActivity.userTypes==1){
            Intent intent = new Intent(ChattingActivity.this, CheckBidingActivity.class);
            startActivity(intent);
        }
    }


}