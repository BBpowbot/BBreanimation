package com.barcodeyboi.reanimation.leaf

import com.barcodeyboi.reanimation.Script
import org.powbot.api.Condition
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Players
import org.powbot.api.script.tree.Leaf

class EatFood(script: Script) : Leaf<Script>(script, "Eating food.") {
    override fun execute() {
        val foodItem = Inventory.stream().name(script.settings.foodName).first()
        if (foodItem.interact("Eat")) {
            Condition.wait { !foodItem.valid() && Players.local().animation() == -1 }
        }
    }
}