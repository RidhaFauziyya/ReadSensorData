package com.example.pertemuan4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

//Untuk menampilkan callback atau event dari sensor maka diperlukan implement class baru
//Agar apabila terdapat sensor yang berubah, akan memberi informasi update
public class MainActivity extends AppCompatActivity implements SensorEventListener {

    //Untuk mengatur sensor siperlukan sensor manager
    private SensorManager mSensorManager;

    //Membuat variabel dengan tipe sensor untuk menampilkan data sensor light dan proximity
    private Sensor mSensorLight;
    private Sensor mSensorProximity;
    private Sensor mSensorAmbient;
    private Sensor mSensorPressure;
    private Sensor mSensorHumidity;

    //Mengambil elemen text view;
    private TextView mTextSensorLight;
    private TextView mTextSensorProximity;
    private TextView mTextSensorAmbient;
    private TextView mTextSensorPressure;
    private TextView mTextSensorHumidity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //Membuat List untuk sesnor sensor yang ada
        //Karena ingin mengambil semua tipe data sesnor, maka diatur dengan semua tipe data (Type All)
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        StringBuilder sensorText = new StringBuilder();
        //Membuat iterasi untuk memasukkan sensor data ke dalam list
        for (Sensor currenSensor : sensorList){
            sensorText.append(currenSensor.getName())
                    .append(System.getProperty("line.separator"));
        }

        //Untuk menampilkan pada aplikasi, maka list ditampilkan pada text view
        TextView sensorTextView = findViewById(R.id.sensor_list);
        sensorTextView.setText(sensorText);

        //Mengambil nilai elemen text view
        mTextSensorLight = findViewById(R.id.label_light);
        mTextSensorProximity = findViewById(R.id.label_proximity);
        mTextSensorAmbient = findViewById(R.id.label_ambient);
        mTextSensorPressure = findViewById(R.id.label_pressure);
        mTextSensorHumidity = findViewById(R.id.label_humidity);

        //Mengambil data sensor sesuai dengan tipe data sensor yang ingin ditampilkan
        mSensorLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        mSensorProximity = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        mSensorAmbient = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        mSensorPressure = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        mSensorHumidity = mSensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        //Pengecekan sensor, apakah ada atau tidak
        String sensor_error = "No Sensor";
        if (mSensorLight == null){
            mTextSensorLight.setText(sensor_error);
        }
        if (mSensorProximity == null){
            mTextSensorProximity.setText(sensor_error);
        }
        if (mSensorAmbient == null){
            mTextSensorAmbient.setText(sensor_error);
        }
        if (mSensorPressure == null){
            mTextSensorPressure.setText(sensor_error);
        }
        if (mSensorHumidity == null){
            mTextSensorHumidity.setText(sensor_error);
        }

    }

    //Membuat fungsi baru untuk mendaftarkan sensor yang akan digunakna pada activity
    public void onStart() {
        super.onStart();
        //Untuk memberi informasi bahwa sensor di update
        if(mSensorLight != null){
            mSensorManager.registerListener(this, mSensorLight,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(mSensorProximity != null){
            mSensorManager.registerListener(this, mSensorProximity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(mSensorAmbient != null){
            mSensorManager.registerListener(this, mSensorAmbient,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }
        if(mSensorPressure!= null){
            mSensorManager.registerListener(this, mSensorPressure,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }if(mSensorHumidity != null){
            mSensorManager.registerListener(this, mSensorHumidity,
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    //Agar event nya tidak berjalan terus, perlu dibuat method baru untuk menghentikan recycle dari event
    public void onStop(){
        super.onStop();
        //Menghentikan event, dengan mengunregister listenernya
        mSensorManager.unregisterListener(this);
    }

    //Membuat method untuk mengubah backgorun colour dari layout
    public void changeBackgroundColour (float currentValue){
        LinearLayout mLayout = findViewById(R.id.layout);
        //Nilai sensor nya terang
        if(currentValue <= 200 && currentValue >=100) mLayout.setBackgroundColor(Color.RED);

        //Nilai sesnodrnya gelap
        else if(currentValue < 100 && currentValue >=10) mLayout.setBackgroundColor(Color.LTGRAY);
    }

    //Untuk mengetahui sensor mana yang mengalami perubahan atau yang diupdate
    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        float currentValue = event.values[0];

        switch (sensorType){
            case Sensor.TYPE_LIGHT:
                mTextSensorLight.setText(
                        String.format("Light sensor : %1$.2f", currentValue));
                changeBackgroundColour(currentValue);
                break;
            case Sensor.TYPE_PROXIMITY:
                mTextSensorProximity.setText(
                        String.format("Light sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                mTextSensorAmbient.setText(
                        String.format("Light sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_PRESSURE:
                mTextSensorPressure.setText(
                        String.format("Light sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                mTextSensorHumidity.setText(
                        String.format("Light sensor : %1$.2f", currentValue));
                break;
            default:
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}