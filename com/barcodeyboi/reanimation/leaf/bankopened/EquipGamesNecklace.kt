package com.barcodeyboi.reanimation.leaf.bankopened

import com.barcodeyboi.reanimation.Constants.IDS_GAMESNECKLACE
import com.barcodeyboi.reanimation.Script
import com.barcodeyboi.reanimation.extensions.isNotEmpty
import org.powbot.api.Condition
import org.powbot.api.rt4.Equipment
import org.powbot.api.rt4.Inventory
import org.powbot.api.script.tree.Leaf

class EquipGamesNecklace(script: Script) : Leaf<Script>(script, "Equipping games necklace.") {
    override fun execute() {
        val necklace = Inventory.stream().id(*IDS_GAMESNECKLACE).first()
        if (necklace.interact("Wear")) {
            Condition.wait { Equipment.isNotEmpty(*IDS_GAMESNECKLACE) }
        }
    }
}