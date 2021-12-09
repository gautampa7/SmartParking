package com.example.smartparking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    TextView text_slot_1, text_slot_2, text_slot_3;
    String slot1_status,slot2_status,slot3_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text_slot_1 = (TextView) findViewById(R.id.textView4);
        text_slot_2 = (TextView) findViewById(R.id.textView5);
        text_slot_3 = (TextView) findViewById(R.id.textView6);

        slot1_status = "0";
        slot2_status = "0";
        slot3_status = "0";

        myRef.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){
                    DataSnapshot snapshot = task.getResult();
                    SlotStatus slot_obj = snapshot.getValue(SlotStatus.class);

                    slot1_status=slot_obj.Slot1;
                    slot2_status=slot_obj.Slot2;
                    slot3_status=slot_obj.Slot3;

                    set_slot_color(slot1_status,text_slot_1);
                    set_slot_color(slot2_status,text_slot_2);
                    set_slot_color(slot3_status,text_slot_3);

                    Toast.makeText(getApplicationContext(), slot1_status, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "FireBase Error!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SlotStatus slot_obj = snapshot.getValue(SlotStatus.class);

                slot1_status=slot_obj.Slot1;
                slot2_status=slot_obj.Slot2;
                slot3_status=slot_obj.Slot3;

                set_slot_color(slot1_status,text_slot_1);
                set_slot_color(slot2_status,text_slot_2);
                set_slot_color(slot3_status,text_slot_3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    public void set_slot_color(String status, TextView slot){
        if(status.matches("0")){
            slot.setBackgroundColor(Color.GREEN);
            slot.setText("Free");
        }else{
            slot.setBackgroundColor(Color.RED);
            slot.setText("Occupied");
        }
    }
}