package com.barcodeyboi.reanimation

data class Settings(
    val ReanimationSpell: String,
    val EnsouledHeadType: String,

    val useGamesNecklace: Boolean,
    val shouldEquipGamesNeck: Boolean,

    val foodName: String,
    val foodAmount: Int,

    val needsAntipoison: Boolean
) {
    val eatAtHealthPercentage = 75
    val takeAmountOfCastRunes = 30
}