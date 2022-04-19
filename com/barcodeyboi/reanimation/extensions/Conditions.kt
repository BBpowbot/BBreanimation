package com.barcodeyboi.reanimation.extensions

import org.powbot.api.Condition
import org.powbot.api.rt4.Equipment
import org.powbot.api.rt4.Inventory


fun Condition.InventoryFoodCountEqualsFoodAmount(foodName: String, foodCount: Int): Boolean {
    return Condition.wait { Inventory.count(foodName) == foodCount }
}