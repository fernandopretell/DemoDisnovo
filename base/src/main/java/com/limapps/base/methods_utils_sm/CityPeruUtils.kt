package com.limapps.base.methods_utils_sm

class CityPeruUtils {

    companion object{
        fun getCityPeruById(id:Int) : String{
            return when(id){
                0-> "Amazonas"
                1-> "Ancash"
                2-> "Apurimac"
                3-> "Arequipa"
                4-> "Ayacucho"
                5-> "Cajamarca"
                6-> "Callao"
                7-> "Cusco"
                8-> "Huancavelica"
                9-> "Huánuco"
                10-> "Ica"
                11-> "Junín"
                12-> "La Libertad"
                13-> "Lambayeque"
                14-> "Lima"
                15-> "Loreto"
                16-> "Madre de Dios"
                17-> "Moquegua"
                18-> "Pasco"
                19-> "Piura"
                20-> "Puno"
                21-> "San Martín"
                22-> "Tacna"
                23-> "Tumbes"
                24-> "Ucayali"
                else -> "Algo salio mal"
            }

        }
    }
}