package com.barcodeyboi.reanimation.leaf

import com.barcodeyboi.reanimation.Constants.AREA_WINTERTODT
import com.barcodeyboi.reanimation.Constants.IDS_GAMESNECKLACE
import com.barcodeyboi.reanimation.Script
import com.barcodeyboi.reanimation.extensions.isNotEmpty
import org.powbot.api.Condition
import org.powbot.api.rt4.*
import org.powbot.api.script.tree.Leaf


class TeleportToWintertodt(script: Script) : Leaf<Script>(script, "Teleporting to wintertodt.") {
    override fun execute() {
        val equippedNecklace = Equipment.itemAt(Equipment.Slot.NECK)
        if (IDS_GAMESNECKLACE.contains(equippedNecklace.id)) {
            if (equippedNecklace.interact("Wintertodt Camp")) {
                Condition.wait { AREA_WINTERTODT.contains(Players.local()) }
            }
        } else {
            val inventoryGamesNecklace = Inventory.stream().id(*IDS_GAMESNECKLACE).first()
            if (inventoryGamesNecklace.interact("Rub") && Condition.wait { Chat.chatting() }) {
                val chatOption =
                    Widgets.component(219, 1, 5) //Wintertodt Camp chat option widget, Chat.continueChat doesn't work...
                if (chatOption.valid() && chatOption.click()) {
                    Condition.wait { AREA_WINTERTODT.contains(Players.local()) }
                }
            }
        }
    }
}