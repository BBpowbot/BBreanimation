package com.barcodeyboi.reanimation.extensions

import org.powbot.api.rt4.Equipment

fun Equipment.isEmpty(vararg name: String): Boolean {
    return stream().name(*name).isEmpty()
}

fun Equipment.isEmpty(vararg ids: Int): Boolean {
    return stream().id(*ids).isEmpty()
}

fun Equipment.isNotEmpty(vararg name: String): Boolean {
    return stream().name(*name).isNotEmpty()
}

fun Equipment.isNotEmpty(vararg ids: Int): Boolean {
    return stream().id(*ids).isNotEmpty()
}