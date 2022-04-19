package com.barcodeyboi.reanimation.branch

import com.barcodeyboi.reanimation.Constants.AREA_DARKALTAR
import com.barcodeyboi.reanimation.Constants.IDS_SUPERANTIPOISON
import com.barcodeyboi.reanimation.Script
import com.barcodeyboi.reanimation.extensions.isEmpty
import com.barcodeyboi.reanimation.helpers.CombatHelper
import com.barcodeyboi.reanimation.leaf.*
import org.powbot.api.rt4.*
import org.powbot.api.script.tree.Branch
import org.powbot.api.script.tree.TreeComponent

class NeedToBank(script: Script) : Branch<Script>(script, "Bank?") {
    override val successComponent: TreeComponent<Script> = InWintertodt(script)
    override val failedComponent: TreeComponent<Script> = InDarkAltarArea(script)

    override fun validate(): Boolean {
        if (script.settings.needsAntipoison && Inventory.isEmpty(*IDS_SUPERANTIPOISON) && !Combat.isPoisonImmune()) {
            return true
        }
        return (Inventory.isEmpty(script.settings.EnsouledHeadType) ||
                Inventory.isEmpty(script.settings.foodName)) && !CombatHelper.inCombat()
    }
}

class InDarkAltarArea(script: Script) : Branch<Script>(script, "Go to dark altar?") {
    override val successComponent: TreeComponent<Script> = WalkToDarkAltarArea(script)
    override val failedComponent: TreeComponent<Script> = ShouldSwapSpellbook(script)

    override fun validate(): Boolean {
        return !AREA_DARKALTAR.contains(Players.local())
    }
}

class ShouldSwapSpellbook(script: Script) : Branch<Script>(script, "Swap spellbook?") {
    override val successComponent: TreeComponent<Script> = SwapSpellBook(script)
    override val failedComponent: TreeComponent<Script> = ShouldEatFood(script)

    override fun validate(): Boolean {
        return Magic.book().name != Magic.Book.ARCEUUS.name
    }
}

class ShouldEatFood(script: Script) : Branch<Script>(script, "Eat food?") {
    override val successComponent: TreeComponent<Script> = EatFood(script)
    override val failedComponent: TreeComponent<Script> = ShouldDrinkAntiPoison(script)

    override fun validate(): Boolean {
        return Combat.healthPercent() <= script.settings.eatAtHealthPercentage
    }
}

class ShouldDrinkAntiPoison(script: Script) : Branch<Script>(script, "Drink anti-poison?") {
    override val successComponent: TreeComponent<Script> = DrinkAntipoison(script)
    override val failedComponent: TreeComponent<Script> = ShouldReanimate(script)

    override fun validate(): Boolean {
        return script.settings.needsAntipoison && Combat.isPoisoned()
    }
}

class ShouldReanimate(script: Script) : Branch<Script>(script, "Cast reanimation spell?") {
    override val successComponent: TreeComponent<Script> = ShouldFightReanimatedNpc(script)
    override val failedComponent: TreeComponent<Script> = CastReanimationSpell(script)

    override fun validate(): Boolean {
        return CombatHelper.reanimatedNpcInteractingWithPlayer()
    }
}

class ShouldFightReanimatedNpc(script: Script) : Branch<Script>(script, "Fight Npc?") {
    override val successComponent: TreeComponent<Script> = AttackNpc(script)
    override val failedComponent: TreeComponent<Script> = Sleep(script)

    override fun validate(): Boolean {
        return Players.local().interacting() == Actor.Nil
    }
}