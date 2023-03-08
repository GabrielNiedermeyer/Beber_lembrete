package com.example.beber_lembrete

import android.app.TimePickerDialog
import android.content.Intent
import android.icu.text.NumberFormat
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import model.calcular_injestao_diaria
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var edit_peso: EditText
    lateinit var edit_idade: EditText
    lateinit var bt_calcular: Button
    lateinit var txt_resultado_ml: TextView
    lateinit var ic_redefinir_dados: ImageView
    lateinit var bt_lembrete: Button
    lateinit var bt_alarme: Button
    lateinit var txt_hora : TextView
    lateinit var txt_minutos : TextView


    private lateinit var calcularInjestaoDiaria: calcular_injestao_diaria
    private var ResultadoMl=0.00

    lateinit var timePickerDialog: TimePickerDialog
    lateinit var calendario: Calendar
    var horaAtual =0
    var minutoAtuais=0


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar!!.hide()
        IniciarComponentes()
        calcularInjestaoDiaria= calcular_injestao_diaria()

        bt_calcular.setOnClickListener{
            if (edit_peso.text.toString().isEmpty()){
                Toast.makeText(this, R.string.toast_informe_peso,Toast.LENGTH_SHORT).show()
            }else if( edit_idade.text.toString().isEmpty()){
                Toast.makeText(this, R.string.toast_informe_idade,Toast.LENGTH_SHORT).show()
            }else{
                val peso=edit_peso.text.toString().toDouble()
                val idade=edit_idade.text.toString().toInt()
                calcularInjestaoDiaria.CalcularTotalMl(peso,idade)
                ResultadoMl = calcularInjestaoDiaria.resultadoMl()
                val formatar = NumberFormat.getNumberInstance(Locale("pt","BR"))
                formatar.isGroupingUsed = false
                txt_resultado_ml.text=formatar.format(ResultadoMl)+ " " + "ml"
               // txt_resultado_ml.text=ResultadoMl.toString()+ " " + "ml"
            }
        }
        ic_redefinir_dados.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_descri)
                .setPositiveButton("OK", {dialogInteface, i ->
                    edit_peso.setText("")
                    edit_idade.setText("")
                    txt_resultado_ml.text = ""
                })
            alertDialog.setNegativeButton("Cancelar",{dialogInterface, i->

            })
            val dialog = alertDialog.create()
            dialog.show()
        }
        bt_lembrete.setOnClickListener{
            calendario=Calendar.getInstance()
            horaAtual=calendario.get(Calendar.HOUR_OF_DAY)
            minutoAtuais=calendario.get(Calendar.MINUTE)
            timePickerDialog = TimePickerDialog(this,{timePicker: TimePicker,hourOfDay: Int, minutes:Int->
                txt_hora.text= String.format("%02d",hourOfDay)
                txt_minutos.text= String.format("%02d",minutes)
            },horaAtual,minutoAtuais,true)
            timePickerDialog.show()
        }

        bt_alarme.setOnClickListener{
            if(!txt_hora.text.toString().isEmpty() && !txt_minutos.text.toString().isEmpty()){
                val intent = Intent(AlarmClock.ACTION_SET_ALARM)
                intent.putExtra(AlarmClock.EXTRA_HOUR, txt_hora.text.toString().toInt())
                intent.putExtra(AlarmClock.EXTRA_MINUTES, txt_minutos.text.toString().toInt())
                intent.putExtra(AlarmClock.EXTRA_MESSAGE,getString(R.string.time_alarme_mensagem))
                startActivity(intent)


            if(intent.resolveActivity(packageManager) !=null){
                startActivity(intent)
                }
            }
        }
    }
    private fun IniciarComponentes(){
        edit_peso=findViewById(R.id.edit_peso)
        edit_idade=findViewById(R.id.edit_idade)
        bt_calcular=findViewById(R.id.bt_calcular)
        txt_resultado_ml=findViewById(R.id.txt_resultado_ml)
        ic_redefinir_dados=findViewById(R.id.ic_redefinir)
        bt_lembrete =findViewById(R.id.bt_defirnir_lembrete)
        bt_alarme = findViewById(R.id.bt_alarme)
        txt_hora = findViewById(R.id.txt_hora)
        txt_minutos = findViewById(R.id.txt_minutos)



    }
}