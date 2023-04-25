package com.example.deliveryapp.models

data class Cart(
    var orders : MutableList<OrderLine>,
    var total : Double

) {
    constructor() : this(mutableListOf<OrderLine>(), 0.0)
}
