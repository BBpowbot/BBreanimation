package com.barcodeyboi.reanimation.leaf

import com.barcodeyboi.reanimation.Script
import com.barcodeyboi.reanimation.helpers.CombatHelper
import com.barcodeyboi.reanimation.helpers.ReanimationSpell
import org.powbot.api.Condition
import org.powbot.api.Notifications
import org.powbot.api.rt4.Inventory
import org.powbot.api.script.tree.Leaf
import org.powbot.mobile.script.ScriptManager

class CastReanimationSpell(script: Script) : Leaf<Script>(script, "Casting reanimation.") {
    override fun execute() {
        val magicSpell = ReanimationSpell.getMagicSpell(script.settings.ReanimationSpell)

        if (magicSpell.canCast()) {
            if (magicSpell.cast() && Condition.wait { Inventory.opened() }) {
                if (Inventory.stream().name(script.settings.EnsouledHeadType).first().click()) {
                    Condition.wait({
                        CombatHelper.reanimatedNpcInteractingWithPlayer()
                    }, 300, 50)
                }
            }
        } else {
            Notifications.showNotification("Cannot cast reanimation spell, stopping.")
            ScriptManager.stop()
        }
    }
}