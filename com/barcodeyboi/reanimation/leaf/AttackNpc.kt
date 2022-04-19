package com.barcodeyboi.reanimation.leaf

import com.barcodeyboi.reanimation.Script
import com.barcodeyboi.reanimation.helpers.CombatHelper
import org.powbot.api.Condition
import org.powbot.api.rt4.Npc
import org.powbot.api.rt4.Players
import org.powbot.api.script.tree.Leaf

class AttackNpc(script: Script) : Leaf<Script>(script, "Attacking npc") {
    override fun execute() {
        val npc = CombatHelper.npcInteractingWithPlayer()
        if (npc != Npc.Nil && npc.interact("Attack")) {
            Condition.wait { Players.local().interacting() == npc }
        }
    }
}