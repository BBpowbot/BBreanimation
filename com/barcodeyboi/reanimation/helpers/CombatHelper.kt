package com.barcodeyboi.reanimation.helpers

import org.powbot.api.rt4.Npc
import org.powbot.api.rt4.Npcs
import org.powbot.api.rt4.Players

object CombatHelper {
    fun npcInteractingWithPlayer(): Npc {
        return Npcs.stream().interactingWithMe().first()
    }

    fun reanimatedNpcInteractingWithPlayer(): Boolean {
        val npc = npcInteractingWithPlayer()
        return npc.valid() && npc.name().contains("Reanimated")
    }

    fun inCombat(): Boolean {
        val reanimatedNpc = npcInteractingWithPlayer()
        return (reanimatedNpc.valid() &&
                reanimatedNpc.healthBarVisible()) ||
                Players.local().healthBarVisible()
    }
}