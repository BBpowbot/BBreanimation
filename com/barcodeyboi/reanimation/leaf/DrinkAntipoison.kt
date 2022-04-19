package com.barcodeyboi.reanimation.leaf

import com.barcodeyboi.reanimation.Constants.IDS_SUPERANTIPOISON
import com.barcodeyboi.reanimation.Script
import org.powbot.api.Condition
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Players
import org.powbot.api.script.tree.Leaf

class DrinkAntipoison(script: Script) : Leaf<Script>(script, "Drinking antipoison") {
    override fun execute() {
        val superAntiPoisonItem = Inventory.stream().id(*IDS_SUPERANTIPOISON).first()
        if (superAntiPoisonItem.interact("Drink")) {
            Condition.wait { !superAntiPoisonItem.valid() && Players.local().animation() == -1 }
        }
    }
}