package br.sofex.com.blutooth;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

    Button Btn_Procurar;
    TextView resultadoDispositivos;

    public static int ENABLE_BLUETOOTH = 1;
    public static int SELECT_PAIRED_DEVICE = 2;
    public static int SELECT_DISCOVERED_DEVICE = 3;

    private String bluetoothAtivadoSmile = "Bluetooth ativado :D";
    private String bluetoothAtivadoNotSmile = "Bluetooth não ativado :(";
    private String bluetoothDispositivoNotSelected = "Nenhum dispositivo selecionado :(";
    private String bluetoothAtivado = "Bluetooth já ativado :)";
    private String bluetoothSolicitacao = "Solicitando ativação do Bluetooth...";
    private String bluetoothNaoFunciona = "Que pena! Hardware Bluetooth não está funcionando :(";


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        resultadoDispositivos     = findViewById(R.id.resultadoDispositivos);
        Btn_Procurar              = findViewById(R.id.Btn_Procurar);

        /* Listener que fica escutando quando o botão é clicado
        * Procura os dipositivos de blutooth */
        Btn_Procurar.setOnClickListener(view -> searchPairedDevices());

        BluetoothAdapter btAdapter = BluetoothAdapter.getDefaultAdapter();

        /* verifica se o adpter for nulo o blutooth não está funcionando */
        if (btAdapter == null) {

            /* Seta a mensagem */
            resultadoDispositivos.setText(bluetoothNaoFunciona);

        } else {

            /* Verifica se o blutooth está habilitado */
            if(!btAdapter.isEnabled()) {

                /* Habilita o blutooth */
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, ENABLE_BLUETOOTH);

                /* Seta a mensagem */
                resultadoDispositivos.setText(bluetoothSolicitacao);
            } else {

                /* Seta a mensagem */
                resultadoDispositivos.setText(bluetoothAtivado);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        /* Verifica se o bluetooth está ativado */
        if(requestCode == ENABLE_BLUETOOTH) {

            /* Se estiver habilitado  mostra a mensagem  de ativado, caso não mostra a mensagem
            * que não está ativado */
            if(resultCode == RESULT_OK) {

                /* Seta a mensagem */
                resultadoDispositivos.setText(bluetoothAtivadoSmile);
            }
            else {

                /* Seta a mensagem */
                resultadoDispositivos.setText(bluetoothAtivadoNotSmile);
            }
        }
        /* Verifica se o bluetooth está ativado e se encontrou um dispositivo , emparelha  */
        else if(requestCode == SELECT_PAIRED_DEVICE) {
            if(resultCode == RESULT_OK) {
                String disposingSelectedTex = "Você selecionou " + data.getStringExtra("btDevName") + "\n"
                        + data.getStringExtra("btDevAddress");
                resultadoDispositivos.setText(disposingSelectedTex);
            }
            else {
                resultadoDispositivos.setText(bluetoothDispositivoNotSelected);
            }
        }
    }
    public void searchPairedDevices() {

        /* Procura pelos dispositivos */
        Intent searchPairedDevicesIntent = new Intent(this, PairedDevices.class);
        startActivityForResult(searchPairedDevicesIntent, SELECT_PAIRED_DEVICE);
    }

}
