package com.hugh.outsourcing.bank_acs.service

import com.google.gson.reflect.TypeToken

class Service(val id:Int,private val params:Param?) {
    inner class Param(private val areas:List<String>){
        fun allow(area: String):Boolean{
            return areas.let { area in it }
        }

        override fun toString(): String {
            return areas.joinToString { it }
        }
    }
    override fun equals(other: Any?): Boolean {
        return other is Service && other.id == id
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + params.hashCode()
        return result
    }

    override fun toString(): String {
        return params?.let { "$id : $it" }?:"$id"
    }
}
fun main(){
    val json = "[{\"id\":5,\"params\":{\"areas\":[\"四川\",\"湖南\"]}},{\"id\":1}]"
    val gson = com.google.gson.Gson()
    val type = object : TypeToken<List<Service>>(){}.type
    val services = gson.fromJson<List<Service>>(json,type)
    println(services.joinToString { it.toString() })
}