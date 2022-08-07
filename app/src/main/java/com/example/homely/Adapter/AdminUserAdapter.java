package com.example.homely.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homely.Model.Data;
import com.example.homely.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AdminUserAdapter extends FirebaseRecyclerAdapter<Data, AdminUserAdapter.myViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdminUserAdapter(@NonNull FirebaseRecyclerOptions<Data> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Data model) {


        if (model.getUserTypes()==4){
            holder.btn_block.setText("Unblock");
        }



        holder.fullname.setText("Full name : "+model.getFullname());
        holder.email.setText("E-mail : "+model.getEmail());
        holder.phone.setText("Phone no. : "+model.getPhoneno());
        holder.date.setText("Account created : "+model.getDate());




        holder.btn_block.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.getUserTypes() == 4) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("userTypes", 1);
                    FirebaseDatabase.getInstance().getReference().child("User").child(model.getId()).updateChildren(map);
                } else {
                    Map<String, Object> map = new HashMap<>();
                    map.put("userTypes", 4);
                    FirebaseDatabase.getInstance().getReference().child("User").child(model.getId()).updateChildren(map);
                }
            }
        });




        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.fullname.getContext());
                builder.setTitle("Are you sure");
                builder.setMessage("Deleted data can't be undo");
                builder.setMessage("deleted data can't be undo");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("User").child(getRef(position).getKey()).removeValue();  }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(holder.name.getContext(),"cancelled",Toast.LENGTH_LONG).show();
                    }
                });
                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_user,parent,false);
        return new AdminUserAdapter.myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView name , fullname ,email,phone,companyfound,house,date;
        TextView btn_delete , btn_block;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);


            fullname = itemView.findViewById(R.id.user_fullname);
            email = itemView.findViewById(R.id.user_email);
            phone = itemView.findViewById(R.id.user_phone);
            date = itemView.findViewById(R.id.user_date);

            btn_block = itemView.findViewById(R.id.user_btn_block);
            btn_delete = itemView.findViewById(R.id.user_btn_delete);
        }
    }
}
