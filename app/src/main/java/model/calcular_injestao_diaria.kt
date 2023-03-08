package model

class calcular_injestao_diaria {

    private var Ml_JOVEM=40.0
    private var Ml_ADULTO=35.0
    private var Ml_IDOSO=30.0
    private var Ml_MAIS_DE_66_ANOS=25.0

    private var resultadoMl=0.0
    private var resultado_total_ml=0.0

    fun CalcularTotalMl( peso:Double, idade: Int){
        if (idade<=17){
            resultadoMl=peso* Ml_JOVEM
            resultado_total_ml=resultadoMl
        }else if(idade<=55){
            resultadoMl=peso*Ml_ADULTO
            resultado_total_ml=resultadoMl
        }else if(idade<=65){
            resultadoMl=peso*Ml_IDOSO
            resultado_total_ml=resultadoMl
        }else{
            resultadoMl=peso*Ml_MAIS_DE_66_ANOS
            resultado_total_ml=resultadoMl
        }
    }

        fun resultadoMl(): Double{
            return resultado_total_ml
        }
}