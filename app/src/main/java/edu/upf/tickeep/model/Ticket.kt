package edu.upf.tickeep.model

import java.sql.Timestamp

data class Ticket(
    var id:String ?= null,
    var dataFi: com.google.firebase.Timestamp? = null,
    var dataIni:com.google.firebase.Timestamp? =null,
    var factura:Boolean ?= null,
    var fav:Boolean ?= null,
    var important:Boolean ?= null,
    var place:String ?= null,
    var products: List<Product> ?= null
)
