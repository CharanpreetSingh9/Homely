package com.example.homely.Adapter;

import androidx.annotation.NonNull;

import com.example.homely.Contractor.CheckBidingActivity;
import com.example.homely.Model.Global;
import com.example.homely.Model.PostProject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homely.ProfileActivity;
import com.example.homely.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;


import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PostProjectAdapter extends  FirebaseRecyclerAdapter<PostProject, PostProjectAdapter.myViewHolder>{

    public PostProjectAdapter(@NonNull FirebaseRecyclerOptions<PostProject> options) {
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


        holder.checkBiding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Global.BIDING_PUSH_ID = model.getPushid();

                Intent intent = new Intent(holder.name.getContext(), CheckBidingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                holder.name.getContext().startActivity(intent);
            }
        });


        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(view.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_post))
                        .setExpanded(true,1500)
                        .create();




                View view1 = dialogPlus.getHolderView();


                EditText name = view1.findViewById(R.id.update_projectname);
                EditText area = view1.findViewById(R.id.update_area);
                EditText budget = view1.findViewById(R.id.update_budget);
                EditText location = view1.findViewById(R.id.update_location);
                TextView date = view1.findViewById(R.id.update_date);
                Button btn_edit = view1.findViewById(R.id.btn_update_post);
                CheckBox check_with = view1.findViewById(R.id.check_with);
                CheckBox check_without = view1.findViewById(R.id.check_without);
                final Calendar myCalendar= Calendar.getInstance();

                DatePickerDialog.OnDateSetListener date1 =new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH,month);
                        myCalendar.set(Calendar.DAY_OF_MONTH,day);

                        String myFormat="dd/MM/yy";
                        SimpleDateFormat dateFormat=new SimpleDateFormat(myFormat, Locale.US);
                        date.setText(dateFormat.format(myCalendar.getTime()));
                    }
                };


                name.setText(model.getName());
                area.setText(model.getArea());
                budget.setText(model.getBudget());
                location.setText(model.getLocation());


                date.setText(model.getEndDate());

                date.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(holder.name.getContext(),date1,myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });






                check_with.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (compoundButton.isChecked()){
                            check_without.setChecked(false);
                            Global.MATERIAL = "with";

                        }
                    }
                });

                check_without.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (compoundButton.isChecked()){
                            check_with.setChecked(false);
                            Global.MATERIAL = "without";

                        }
                    }
                });

                dialogPlus.show();


                btn_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {





                        Map<String,Object>map = new HashMap<>();
                        map.put("area",area.getText().toString().trim());
                        map.put("budget",budget.getText().toString().trim());
                        map.put("endDate",date.getText().toString().trim());
                        map.put("location",location.getText().toString().trim());
                        map.put("name",name.getText().toString().trim());
                        map.put("material",Global.MATERIAL);

                        FirebaseDatabase.getInstance().getReference().child("Project Post").child(getRef(position).getKey()).updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(holder.name.getContext(),"data is updated",Toast.LENGTH_LONG).show();
                                dialogPlus.dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(holder.name.getContext(),"Error while updating",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });

            }
        });

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
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView name , area , location , budget , date , material,endDate ,posted_user_name;
        TextView btn_delete,btn_edit,checkBiding;
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
            checkBiding = itemView.findViewById(R.id.checkBiding);

        }
    }


}
