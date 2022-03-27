package com.hugh.outsourcing.bank_acs.service

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