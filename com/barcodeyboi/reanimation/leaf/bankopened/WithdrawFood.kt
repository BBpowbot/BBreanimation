package com.barcodeyboi.reanimation.leaf.bankopened

import com.barcodeyboi.reanimation.Script
import com.barcodeyboi.reanimation.extensions.InventoryFoodCountEqualsFoodAmount
import com.barcodeyboi.reanimation.extensions.count
import com.barcodeyboi.reanimation.extensions.mustWithdrawItem
import org.powbot.api.Condition
import org.powbot.api.rt4.Bank
import org.powbot.api.rt4.Inventory
import org.powbot.api.script.tree.Leaf
import kotlin.math.abs

class WithdrawFood(script: Script) : Leaf<Script>(script, "Withdrawing food.") {
    override fun execute() {
        val foodName = script.settings.foodName
        val settingsFoodCount = script.settings.foodAmount
        val inventoryFoodCount = Inventory.count(foodName)
        val foodCountDifference = abs(inventoryFoodCount - settingsFoodCount)

        if (inventoryFoodCount > settingsFoodCount) {
            if (Bank.deposit(foodName, foodCountDifference)) {
                Condition.InventoryFoodCountEqualsFoodAmount(foodName, settingsFoodCount)
            }
        } else {
            if (Bank.mustWithdrawItem(foodName, foodCountDifference)) {
                Condition.InventoryFoodCountEqualsFoodAmount(foodName, settingsFoodCount)
            }
        }


    }
}