package br.sofex.com.blutooth;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    //TODO: https://dragaosemchama.com/2015/05/programacao-bluetooth-no-android/

    private static final int BT_ATIVAR = 0;
    Button Btn_Procurar;
    TextView txt_View;

    public static int ENABLE_BLUETOOTH = 1;
    public static int SELECT_PAIRED_DEVICE = 2;
    public static int SELECT_DISCOVERED_DEVICE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_View = findViewById(R.id.txt_View);
        Btn_Procurar = findViewById(R.id.Btn_Procurar);
        Btn_Procurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchPairedDevices();
            }
        });

        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter == null) {
            txt_View.setText("Que pena! Hardware Bluetooth não está funcionando :(");
        } else {
            //txt_View.setText("Ótimo! Hardware Bluetooth está funcionando :)");
            if(!btAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, ENABLE_BLUETOOTH);
                txt_View.setText("Solicitando ativação do Bluetooth...");
            } else {
                txt_View.setText("Bluetooth já ativado :)");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == ENABLE_BLUETOOTH) {
            if(resultCode == RESULT_OK) {
                txt_View.setText("Bluetooth ativado :D");
            }
            else {
                txt_View.setText("Bluetooth não ativado :(");
            }
        }
        else if(requestCode == SELECT_PAIRED_DEVICE) {
            if(resultCode == RESULT_OK) {
                txt_View.setText("Você selecionou " + data.getStringExtra("btDevName") + "\n"
                        + data.getStringExtra("btDevAddress"));
            }
            else {
                txt_View.setText("Nenhum dispositivo selecionado :(");
            }
        }
    }
    public void searchPairedDevices() {

        Intent searchPairedDevicesIntent = new Intent(this, PairedDevices.class);
        startActivityForResult(searchPairedDevicesIntent, SELECT_PAIRED_DEVICE);
    }

}
