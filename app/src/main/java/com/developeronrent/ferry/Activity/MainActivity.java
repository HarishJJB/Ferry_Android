package com.developeronrent.ferry.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.developeronrent.ferry.FerryListAdapter.CardItem;
import com.developeronrent.ferry.FerryListAdapter.CardPagerAdapter;
import com.developeronrent.ferry.FerryListAdapter.ShadowTransformer;
import com.developeronrent.ferry.R;
import com.developeronrent.ferry.Utils.PrefsManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Radio button
    @BindView(R.id.rbt_1way)
    RadioButton rbt_1way;
    @BindView(R.id.rbt_2way)
    RadioButton rbt_2way;

    //Text Source and destination
    @BindView(R.id.txt_source)
    TextView txt_source;
    @BindView(R.id.txt_destination)
    TextView txt_destination;
    @BindView(R.id.img_exchange)
    ImageView img_exchange;

    //journey and return date
    @BindView(R.id.txt_journery_date)
    TextView txt_journery_date;
    @BindView(R.id.txt_return_date)
    TextView txt_return_date;
    @BindView(R.id.ll_twoway)
    LinearLayout ll_twoway;

    //Passenger Details
    @BindView(R.id.txt_seat_type)
    TextView txt_seat_type;

    @BindView(R.id.txt_passengers)
    TextView txt_passengers;
    @BindView(R.id.txt_vehicle)
    TextView txt_vehicles;
    @BindView(R.id.ll_deck)
    LinearLayout ll_deck;
    @BindView(R.id.ll_passenger)
    LinearLayout ll_passengers;
    @BindView(R.id.ll_vehicles)
    LinearLayout ll_vehicles;

    //Total and booking
    @BindView(R.id.txt_total)
    TextView txt_total;
    @BindView(R.id.btn_book)
    TextView btn_book;

    // footer
    @BindView(R.id.ll_discover)
    LinearLayout ll_discover;
    @BindView(R.id.ll_more)
    LinearLayout ll_more;
    @BindView(R.id.ll_trips)
    LinearLayout ll_trips;
    String From, final_from;
    String To, final_to;
    private Calendar calendar;
    String formattedDate, currentDate, finaldate;
    Boolean animate = false;
    int Check_return = 0;


    @BindView(R.id.id_passengers_layout)
    RelativeLayout id_passengers_layout_sheet;
    @BindView(R.id.id_seat_layout)
    RelativeLayout id_seat_layout_sheet;
    @BindView(R.id.id_vehicle_layout)
    RelativeLayout id_vehicle_layout_sheet;
    BottomSheetBehavior passenger_sheetBehavior;
    BottomSheetBehavior seat_sheetBehavior;
    BottomSheetBehavior vehicle_sheetBehavior;


    //Passenger layout ids
    @BindView(R.id.img_minus_adult)
    ImageView img_minus_adult;
    @BindView(R.id.txt_adults)
    TextView txt_adults;
    @BindView(R.id.img_add_adult)
    ImageView img_add_adult;
    /*Passenger layout*/
    @BindView(R.id.img_minus_child)
    ImageView img_minus_child;
    @BindView(R.id.txt_childs)
    TextView txt_childs;
    @BindView(R.id.img_add_child)
    ImageView img_add_child;
    @BindView(R.id.img_minus_infant)
    ImageView img_minus_infant;
    @BindView(R.id.txt_infants)
    TextView txt_infants;
    @BindView(R.id.img_add_infant)
    ImageView img_add_infant;
    @BindView(R.id.btn_passenger)
    TextView btn_passenger;

    /*Seat layout*/
    @BindView(R.id.txt_upper)
    TextView txt_upper;
    @BindView(R.id.txt_lower)
    TextView txt_lower;
    @BindView(R.id.btn_seat)
    TextView btn_seat;

    /*Vehicle layout */
    @BindView(R.id.rbt_no_vehicle)
    RadioButton rbt_no_vehicle;
    @BindView(R.id.rbt_vehicle)
    RadioButton rbt_vehicle;
    @BindView(R.id.txt_vehicle_msg)
    TextView txt_vehicle_msg;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.txt_bicycle)
    TextView txt_bicycle;
    @BindView(R.id.txt_car)
    TextView txt_car;
    @BindView(R.id.txt_camper)
    TextView txt_camper;
    @BindView(R.id.txt_motorcycle)
    TextView txt_motorcycle;
    @BindView(R.id.txt_minibus)
    TextView txt_minibus;
    @BindView(R.id.btn_vehicle)
    TextView btn_vehicle;
    @BindView(R.id.rl_vechicle_type)
    RelativeLayout rl_vechicle_type;
    @BindView(R.id.rl_vechicle_types)
    RelativeLayout rl_vechicle_types;

    PrefsManager prefsManager;
    String final_seat = null;
    int Adult = 1;
    int Child = 0;
    int Infant = 0;
    String final_vehicle = null;
    boolean doubleBackToExitPressedOnce = false;

    String sail_from = "Port A";
    String sail_to = "Port B";
    int version;

    int adult_price = 50;
    int child_price = 20;
    int infant_price = 10;

    int bicycle = 10;
    int car = 20;
    int camper = 20;
    int motorcycle = 10;
    int minimus = 50;
    int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        calendar = Calendar.getInstance();
        System.out.println("Current time => " + calendar.getTime());
        From = txt_source.getText().toString().trim();
        To = txt_destination.getText().toString().trim();
        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        SimpleDateFormat cf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        formattedDate = df.format(calendar.getTime());
        currentDate = df.format(calendar.getTime());
        finaldate = cf.format(calendar.getTime());
        System.out.println("Current date => " + formattedDate);
        txt_source.setText(sail_from);
        txt_destination.setText(sail_to);
        version = Build.VERSION.SDK_INT;
        txt_journery_date.setText(formattedDate);


        prefsManager = new PrefsManager(this);

        btn_book.setOnClickListener(this);
        ll_discover.setOnClickListener(this);
        ll_trips.setOnClickListener(this);
        ll_more.setOnClickListener(this);
        txt_journery_date.setOnClickListener(this);
        txt_return_date.setOnClickListener(this);
        img_exchange.setOnClickListener(this);

        ll_deck.setOnClickListener(this);
        ll_vehicles.setOnClickListener(this);
        ll_passengers.setOnClickListener(this);

        passenger_sheetBehavior = BottomSheetBehavior.from(id_passengers_layout_sheet);
        seat_sheetBehavior = BottomSheetBehavior.from(id_seat_layout_sheet);
        vehicle_sheetBehavior = BottomSheetBehavior.from(id_vehicle_layout_sheet);

        PassengerLayout();
        VehicleLayout();
        SeatLayout();

        rbt_1way.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (rbt_1way.isChecked()) {
                    System.out.println("Checked");
                    rbt_2way.setChecked(false);
                    Check_return = 1;
                    txt_return_date.setText("");
                    txt_return_date.setHint("Return Date");
                    if (ll_twoway.getVisibility() == View.VISIBLE) {
                        ll_twoway.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.left_to_right));
                    }
                    ll_twoway.setVisibility(View.GONE);
                }
            }
        });

        rbt_2way.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (rbt_1way.isChecked()) {
                    System.out.println("Checked");
                    Check_return = 1;
                    rbt_1way.setChecked(false);
                    txt_return_date.setText("");
                    txt_return_date.setHint("Return Date");
                    ll_twoway.setVisibility(View.VISIBLE);
                    ll_twoway.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.right_to_left));
                }
            }
        });


        seat_sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {

                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                        txt_upper.setTextColor(Color.parseColor("#1e1e1e"));
                        txt_lower.setTextColor(Color.parseColor("#1e1e1e"));
                        final_seat = null;
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        passenger_sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {

                        txt_total.setText("\u20B9 000");
                        price = 0;
                        prefsManager.setPassengerAdult("1");
                        prefsManager.setPassengerChild("0");
                        prefsManager.setPassengerInfant("0");

                        Adult = Integer.parseInt(prefsManager.getPassengerAdult());
                        Child = Integer.parseInt(prefsManager.getPassengerChild());
                        Infant = Integer.parseInt(prefsManager.getPassengerInfant());

                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {

                        txt_adults.setText("1");
                        txt_childs.setText("0");
                        txt_infants.setText("0");


                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        vehicle_sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {

                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {

                        final_vehicle = null;

                        rbt_no_vehicle.setChecked(true);
                        rbt_vehicle.setChecked(false);

                        rl_vechicle_type.setVisibility(View.GONE);
                        rl_vechicle_types.setVisibility(View.GONE);
                        txt_vehicle_msg.setVisibility(View.GONE);

                        txt_bicycle.setTextColor(Color.parseColor("#8e8e93"));
                        txt_car.setTextColor(Color.parseColor("#8e8e93"));
                        txt_camper.setTextColor(Color.parseColor("#8e8e93"));
                        txt_motorcycle.setTextColor(Color.parseColor("#8e8e93"));
                        txt_minibus.setTextColor(Color.parseColor("#8e8e93"));

                        txt_bicycle.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.main_border));
                        txt_car.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.main_border));
                        txt_camper.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.main_border));
                        txt_motorcycle.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.main_border));
                        txt_minibus.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.main_border));

                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

    }

    private void PassengerLayout() {

        txt_adults.setText(prefsManager.getPassengerAdult());
        txt_childs.setText(prefsManager.getPassengerChild());
        txt_infants.setText(prefsManager.getPassengerInfant());

        img_add_adult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Adult = Adult + 1;
                prefsManager.setPassengerAdult(String.valueOf(Adult));
                txt_adults.setText(prefsManager.getPassengerAdult());
            }
        });
        img_minus_adult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Adult == 1) {
                    Toast.makeText(getApplicationContext(), "Sorry adults are not less than 1", Toast.LENGTH_SHORT).show();
                    prefsManager.setPassengerAdult("1");
                    txt_adults.setText(prefsManager.getPassengerAdult());
                } else if (Adult > 1) {
                    Adult = Adult - 1;
                    prefsManager.setPassengerAdult(String.valueOf(Adult));
                    txt_adults.setText(prefsManager.getPassengerAdult());
                }
            }
        });

        img_add_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Child = Child + 1;
                prefsManager.setPassengerChild(String.valueOf(Child));
                txt_childs.setText(prefsManager.getPassengerChild());
            }
        });
        img_minus_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Child == 0) {
                    Toast.makeText(getApplicationContext(), "Sorry childs are not less than 0", Toast.LENGTH_SHORT).show();
                    prefsManager.setPassengerChild("0");
                    txt_childs.setText(prefsManager.getPassengerChild());
                } else if (Child > 0) {
                    Child = Child - 1;
                    prefsManager.setPassengerChild(String.valueOf(Child));
                    txt_childs.setText(prefsManager.getPassengerChild());
                }
            }
        });

        img_add_infant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Infant = Infant + 1;
                prefsManager.setPassengerInfant(String.valueOf(Infant));
                txt_infants.setText(prefsManager.getPassengerInfant());
            }
        });
        img_minus_infant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Infant == 0) {
                    Toast.makeText(getApplicationContext(), "Sorry infants are not less than 0", Toast.LENGTH_SHORT).show();
                    prefsManager.setPassengerInfant("0");
                    txt_infants.setText(prefsManager.getPassengerInfant());
                } else if (Infant > 0) {
                    Infant = Infant - 1;
                    prefsManager.setPassengerInfant(String.valueOf(Infant));
                    txt_infants.setText(prefsManager.getPassengerInfant());
                }
            }
        });

        btn_passenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String adults = prefsManager.getPassengerAdult();
                String childs = prefsManager.getPassengerChild();
                String infants = prefsManager.getPassengerInfant();

                System.out.println("adults " + adults);
                System.out.println("childs " + childs);
                System.out.println("infants " + infants);
                String final_text = null;
                String final_price = null;

                passenger_sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                if (Integer.parseInt(adults) >= 1) {
                    final_text = adults + " Adult";
                    System.out.println("inside adults " + final_text);
                    price = (adult_price * Integer.parseInt(adults));
                }
                if (Integer.parseInt(childs) > 0) {
                    final_text = final_text + ", " + childs + " Children";
                    System.out.println("inside children " + final_text);
                    price = price + (child_price * Integer.parseInt(childs));
                }
                if (Integer.parseInt(infants) > 0) {
                    final_text = final_text + ", " + infants + " Infant";
                    System.out.println("inside infant " + final_text);
                    price = price + (infant_price * Integer.parseInt(infants));
                }

                final_price = String.valueOf(price);

                txt_total.setText("\u20B9 " + final_price);

                txt_passengers.setText(final_text);
                prefsManager.setPassengerAdult("1");
                prefsManager.setPassengerChild("0");
                prefsManager.setPassengerInfant("0");


            }
        });

    }

    private void VehicleLayout() {
        final_vehicle = "(no vehicle)";
        rbt_no_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rbt_no_vehicle.isChecked()) {
                    rbt_vehicle.setChecked(false);
                    rl_vechicle_type.setVisibility(View.GONE);
                    rl_vechicle_types.setVisibility(View.GONE);
                    txt_vehicle_msg.setVisibility(View.GONE);
                    final_vehicle = "(no vehicle)";

                }
            }
        });

        rbt_vehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (rbt_vehicle.isChecked()) {
                    rbt_no_vehicle.setChecked(false);
                    rl_vechicle_type.setVisibility(View.VISIBLE);
                    rl_vechicle_types.setVisibility(View.VISIBLE);
                    txt_vehicle_msg.setVisibility(View.VISIBLE);
                    final_vehicle = null;
                }
            }
        });

        txt_bicycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final_vehicle = "(Bicycle)";
                txt_bicycle.setTextColor(Color.parseColor("#ffffff"));
                txt_car.setTextColor(Color.parseColor("#8e8e93"));
                txt_camper.setTextColor(Color.parseColor("#8e8e93"));
                txt_motorcycle.setTextColor(Color.parseColor("#8e8e93"));
                txt_minibus.setTextColor(Color.parseColor("#8e8e93"));

                txt_bicycle.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.border));
                txt_car.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.main_border));
                txt_camper.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.main_border));
                txt_motorcycle.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.main_border));
                txt_minibus.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.main_border));
            }
        });

        txt_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final_vehicle = "(Car)";
                txt_car.setTextColor(Color.parseColor("#ffffff"));
                txt_bicycle.setTextColor(Color.parseColor("#8e8e93"));
                txt_camper.setTextColor(Color.parseColor("#8e8e93"));
                txt_motorcycle.setTextColor(Color.parseColor("#8e8e93"));
                txt_minibus.setTextColor(Color.parseColor("#8e8e93"));

                txt_bicycle.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.main_border));
                txt_car.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.border));
                txt_camper.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.main_border));
                txt_motorcycle.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.main_border));
                txt_minibus.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.main_border));
            }
        });

        txt_camper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final_vehicle = "(Camper)";
                txt_camper.setTextColor(Color.parseColor("#ffffff"));
                txt_car.setTextColor(Color.parseColor("#8e8e93"));
                txt_bicycle.setTextColor(Color.parseColor("#8e8e93"));
                txt_motorcycle.setTextColor(Color.parseColor("#8e8e93"));
                txt_minibus.setTextColor(Color.parseColor("#8e8e93"));

                txt_bicycle.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.main_border));
                txt_car.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.main_border));
                txt_camper.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.border));
                txt_motorcycle.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.main_border));
                txt_minibus.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.main_border));

            }
        });

        txt_motorcycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final_vehicle = "(Motorcycle)";
                txt_motorcycle.setTextColor(Color.parseColor("#ffffff"));
                txt_car.setTextColor(Color.parseColor("#8e8e93"));
                txt_camper.setTextColor(Color.parseColor("#8e8e93"));
                txt_bicycle.setTextColor(Color.parseColor("#8e8e93"));
                txt_minibus.setTextColor(Color.parseColor("#8e8e93"));

                txt_bicycle.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.main_border));
                txt_car.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.main_border));
                txt_camper.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.main_border));
                txt_motorcycle.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.border));
                txt_minibus.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.main_border));
            }
        });

        txt_minibus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final_vehicle = "(Minibus)";
                txt_minibus.setTextColor(Color.parseColor("#ffffff"));
                txt_car.setTextColor(Color.parseColor("#8e8e93"));
                txt_camper.setTextColor(Color.parseColor("#8e8e93"));
                txt_motorcycle.setTextColor(Color.parseColor("#8e8e93"));
                txt_bicycle.setTextColor(Color.parseColor("#8e8e93"));

                txt_bicycle.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.main_border));
                txt_car.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.main_border));
                txt_camper.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.main_border));
                txt_motorcycle.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.main_border));
                txt_minibus.setBackground(ContextCompat.getDrawable(MainActivity.this, R.drawable.border));
            }
        });


        btn_vehicle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                System.out.println("final_vehicle " + final_vehicle);
                if (rbt_vehicle.isChecked()) {
                    if (final_vehicle == null) {
                        Toast.makeText(getApplicationContext(), "Please choose any one vehicle before submitting!!..", Toast.LENGTH_SHORT).show();
                    }else {
                        vehicle_sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        txt_vehicles.setText("Foot passenger " + final_vehicle);
                    }
                } else {
                    vehicle_sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    txt_vehicles.setText("Foot passenger " + final_vehicle);
                }
            }
        });

    }

    private void SeatLayout() {

        txt_upper.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                txt_upper.setTextColor(Color.parseColor("#027e84"));
                txt_lower.setTextColor(Color.parseColor("#1e1e1e"));
                final_seat = "Upper deck";
            }
        });


        txt_lower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_upper.setTextColor(Color.parseColor("#1e1e1e"));
                txt_lower.setTextColor(Color.parseColor("#027e84"));
                final_seat = "Lower deck";
            }
        });


        btn_seat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (final_seat == null) {
                    Toast.makeText(getApplicationContext(), "Please choose any one deck before submitting!!..", Toast.LENGTH_SHORT).show();
                } else {
                    seat_sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    txt_seat_type.setText(final_seat);
                }
            }
        });

    }


    //Adults change listener
    NumberPicker.OnValueChangeListener onValueChangeAdultsListener =
            new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker adult, int i, int i1) {
                    System.out.println("adult.getValue() " + adult.getValue());
                }
            };
    //Childs change listener
    NumberPicker.OnValueChangeListener onValueChangeChildsListener =
            new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker child, int i, int i1) {
                    System.out.println("child.getValue() " + child.getValue());
                }
            };
    //Vehicle change listener
    NumberPicker.OnValueChangeListener onValueChangeVehicleListener =
            new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker vehicle, int i, int i1) {
                    System.out.println("vehicle.getValue() " + vehicle.getValue());
                }
            };

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_book:

                break;

            case R.id.txt_journery_date:
                showDatePicker();
                break;

            case R.id.ll_deck:
                if (seat_sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    id_seat_layout_sheet.setVisibility(View.VISIBLE);
                    id_vehicle_layout_sheet.setVisibility(View.GONE);
                    id_passengers_layout_sheet.setVisibility(View.GONE);
                    seat_sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    passenger_sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    vehicle_sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else {
                    id_seat_layout_sheet.setVisibility(View.GONE);
                    id_vehicle_layout_sheet.setVisibility(View.GONE);
                    id_passengers_layout_sheet.setVisibility(View.GONE);
                    seat_sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    passenger_sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    vehicle_sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                break;

            case R.id.ll_vehicles:
                if (vehicle_sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    id_vehicle_layout_sheet.setVisibility(View.VISIBLE);
                    vehicle_sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    seat_sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    passenger_sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else {
                    id_seat_layout_sheet.setVisibility(View.GONE);
                    id_vehicle_layout_sheet.setVisibility(View.VISIBLE);
                    id_passengers_layout_sheet.setVisibility(View.GONE);
                    vehicle_sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    seat_sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    passenger_sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                break;

            case R.id.ll_passenger:

                if (passenger_sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                    id_seat_layout_sheet.setVisibility(View.GONE);
                    id_vehicle_layout_sheet.setVisibility(View.GONE);
                    id_passengers_layout_sheet.setVisibility(View.VISIBLE);
                    passenger_sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    seat_sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    vehicle_sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else {
                    id_seat_layout_sheet.setVisibility(View.GONE);
                    id_vehicle_layout_sheet.setVisibility(View.GONE);
                    id_passengers_layout_sheet.setVisibility(View.GONE);
                    passenger_sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    seat_sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    vehicle_sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
                break;

            case R.id.txt_return_date:

                if (TextUtils.isEmpty(txt_journery_date.getText().toString().trim())) {
                    Toast.makeText(getApplicationContext(), "First choose the Journey Date", Toast.LENGTH_SHORT).show();
                } else {
                    showReturnDatePicker();
                }
                break;

            case R.id.img_exchange:
                if (animate == false) {
                    img_exchange.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate));
                    animate = true;
//                    From = txt_source.getText().toString();
//                    To = txt_destination.getText().toString();
                    txt_source.setText(sail_to);
                    txt_destination.setText(sail_from);

                    final_from = txt_source.getText().toString();
                    final_to = txt_destination.getText().toString();


                } else if (animate == true) {
                    img_exchange.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate));
                    animate = false;
                    txt_source.setText(sail_from);
                    txt_destination.setText(sail_to);

                    final_from = txt_source.getText().toString();
                    final_to = txt_destination.getText().toString();

                }

                System.out.println("final _from " + final_from);
                System.out.println("final _to " + final_to);
//                txt_source.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.top_to_bottom));
//                txt_destination.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, R.anim.bottom_to_top));
                break;
        }

    }


    private void showReturnDatePicker() {
        final String inputPattern = "yyyy-MM-dd";
        final String outputPattern = "dd MMM yyyy";
        final SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.getDefault());
        final SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, Locale.getDefault());
        final Calendar c = Calendar.getInstance();
        final int cYear = c.get(Calendar.YEAR);
        final int cMonth = c.get(Calendar.MONTH);
        final int cDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener dateSetListener =
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, monthOfYear);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        Date date1 = null;
                        String str1 = null;

                        String returnDate = inputFormat.format(c.getTime());
                        System.out.println("returnDate " + returnDate);
                        //finaldate = mYear+"-"+mMonth+"-"+mDate;
                        try {
                            date1 = inputFormat.parse(returnDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        str1 = outputFormat.format(date1);
                        formattedDate = str1;
                        txt_return_date.setText(str1);

                    }
                };

        long startDate = 0;
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            Date StartDate = sdf.parse(txt_journery_date.getText().toString());

            startDate = StartDate.getTime();

        } catch (ParseException e) {
            e.printStackTrace();
        }

        long now = startDate;

        DatePickerDialog dineInDatePicker =
                new DatePickerDialog(this, dateSetListener, cYear, cMonth, cDay);
        //to disable previous dates
        Calendar calendar = Calendar.getInstance();
//        startcalendar.set(Calendar.MONTH, startcalendar.get(Calendar.MONTH) - 1);
        dineInDatePicker.getDatePicker().setMinDate(now);
//        dineInDatePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis()- 1000);
        dineInDatePicker.show();

    }

    private void showDatePicker() {
        final String inputPattern = "yyyy-MM-dd";
        final String outputPattern = "dd MMM yyyy";
        final SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.getDefault());
        final SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, Locale.getDefault());
        final Calendar c = Calendar.getInstance();
        final int cYear = c.get(Calendar.YEAR);
        final int cMonth = c.get(Calendar.MONTH);
        final int cDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog.OnDateSetListener dateSetListener =
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        c.set(Calendar.YEAR, year);
                        c.set(Calendar.MONTH, monthOfYear);
                        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        Date date1 = null;
                        String str1 = null;

                        String selectedDate = inputFormat.format(c.getTime());
                        System.out.println("selectedDate " + selectedDate);
                        //finaldate = mYear+"-"+mMonth+"-"+mDate;
                        try {
                            date1 = inputFormat.parse(selectedDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        str1 = outputFormat.format(date1);
                        formattedDate = str1;
                        txt_journery_date.setText(str1);

                    }
                };

        long now = System.currentTimeMillis();

        DatePickerDialog dineInDatePicker =
                new DatePickerDialog(this, dateSetListener, cYear, cMonth, cDay);
        //to disable previous dates
        Calendar calendar = Calendar.getInstance();
//        startcalendar.set(Calendar.MONTH, startcalendar.get(Calendar.MONTH) - 1);
        dineInDatePicker.getDatePicker().setMinDate(now);
//        dineInDatePicker.getDatePicker().setMaxDate(calendar.getTimeInMillis()- 1000);
        dineInDatePicker.show();
    }

    @Override
    public void onBackPressed() {
        if (passenger_sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            passenger_sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            return;
        } else if (seat_sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            seat_sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            return;
        } else if (vehicle_sheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            vehicle_sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            return;
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click back again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }
}
