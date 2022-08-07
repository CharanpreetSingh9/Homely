package com.example.homely.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homely.Model.Global;
import com.example.homely.Model.PostProject;
import com.example.homely.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

public class AdminPostAdapter  extends FirebaseRecyclerAdapter<PostProject, AdminPostAdapter.myViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public AdminPostAdapter(@NonNull FirebaseRecyclerOptions<PostProject> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull PostProject model) {


        holder.name.setText(model.getName());
        holder.area.setText(model.getArea() +"Sq");
        holder.budget.setText(model.getBudget()+"CAD");
        holder.location.setText(model.getLocation());
        holder.date.setText(model.getDate());
        holder.material.setText(model.getMaterial()+ " Material");
        holder.endDate.setText("Bid End Date: "+model.getEndDate());
        holder.posted_user_name.setText(model.getPostUser());
        holder.btn_edit.findViewById(R.id.edit_post);
        holder.btn_edit.setVisibility(View.GONE);
        holder.check_biding.setVisibility(View.GONE);




        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(holder.name.getContext());
                builder.setTitle("Are you sure");
                builder.setMessage("Deleted data can't be undo");
                builder.setMessage("deleted data can't be undo");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance().getReference().child("Project Post").child(getRef(position).getKey()).removeValue();  }
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_project_item,parent,false);
        return new AdminPostAdapter.myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView name , area , location , budget , date , material,endDate ,posted_user_name,check_biding;
        TextView btn_delete,btn_edit;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            posted_user_name = itemView.findViewById(R.id.posted_user_name);
            material = itemView.findViewById(R.id.materialtxt);
            name = itemView.findViewById(R.id.projectnamext);
            area = itemView.findViewById(R.id.areatxt);
            location = itemView.findViewById(R.id.locationtxt);
            budget = itemView.findViewById(R.id.budgettxt);
            date = itemView.findViewById(R.id.datetxt);
            endDate = itemView.findViewById(R.id.end_datetxt);
            btn_delete = itemView.findViewById(R.id.btn_delete);
            btn_edit = itemView.findViewById(R.id.edit_post);
            check_biding = itemView.findViewById(R.id.checkBiding);

        }
    }
}


