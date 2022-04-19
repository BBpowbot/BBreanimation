package com.barcodeyboi.reanimation.extensions

import org.powbot.api.rt4.Inventory

fun Inventory.count(vararg name: String): Int {
    return stream().name(*name).count(true).toInt()
}

fun Inventory.count(vararg ids: Int): Int {
    return stream().id(*ids).count(true).toInt()
}

fun Inventory.isEmpty(vararg name: String): Boolean {
    return stream().name(*name).isEmpty()
}

fun Inventory.isEmpty(vararg ids: Int): Boolean {
    return stream().id(*ids).isEmpty()
}

fun Inventory.isNotEmpty(vararg name: String): Boolean {
    return stream().name(*name).isNotEmpty()
}

fun Inventory.isNotEmpty(vararg ids: Int): Boolean {
    return stream().id(*ids).isNotEmpty()
}