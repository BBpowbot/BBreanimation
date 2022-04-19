package com.barcodeyboi.reanimation.branch

import com.barcodeyboi.reanimation.Constants.IDS_GAMESNECKLACE
import com.barcodeyboi.reanimation.Constants.IDS_SUPERANTIPOISON
import com.barcodeyboi.reanimation.Script
import com.barcodeyboi.reanimation.extensions.count
import com.barcodeyboi.reanimation.extensions.isEmpty
import com.barcodeyboi.reanimation.extensions.isNotEmpty
import com.barcodeyboi.reanimation.helpers.ReanimationSpell
import com.barcodeyboi.reanimation.leaf.EatFood
import com.barcodeyboi.reanimation.leaf.bankopened.*
import org.powbot.api.rt4.Combat
import org.powbot.api.rt4.Equipment
import org.powbot.api.rt4.Inventory
import org.powbot.api.script.tree.Branch
import org.powbot.api.script.tree.TreeComponent

class ShouldWithdrawAntiPoison(script: Script) : Branch<Script>(script, "Withdraw anti-poison?") {
    override val successComponent: TreeComponent<Script> = WithdrawAntiPoison(script)
    override val failedComponent: TreeComponent<Script> = ShouldWithdrawGamesNecklace(script)

    override fun validate(): Boolean {
        return script.settings.needsAntipoison && Inventory.isEmpty(*IDS_SUPERANTIPOISON)
    }
}

class ShouldWithdrawGamesNecklace(script: Script) : Branch<Script>(script, "Withdraw games necklace?") {
    override val successComponent: TreeComponent<Script> = WithdrawGamesNecklace(script)
    override val failedComponent: TreeComponent<Script> = ShouldEquipGamesNecklace(script)

    override fun validate(): Boolean {
        if (!script.settings.useGamesNecklace) {
            return false
        }
        return Inventory.isEmpty(*IDS_GAMESNECKLACE) && Equipment.isEmpty(*IDS_GAMESNECKLACE)
    }
}

class ShouldEquipGamesNecklace(script: Script) : Branch<Script>(script, "Equip games necklace?") {
    override val successComponent: TreeComponent<Script> = EquipGamesNecklace(script)
    override val failedComponent: TreeComponent<Script> = ShouldWithdrawFood(script)

    override fun validate(): Boolean {
        if (!script.settings.useGamesNecklace || !script.settings.shouldEquipGamesNeck) {
            return false
        }
        return Inventory.isNotEmpty(*IDS_GAMESNECKLACE) && Equipment.isEmpty(*IDS_GAMESNECKLACE)
    }
}

class ShouldWithdrawFood(script: Script) : Branch<Script>(script, "Withdraw food?") {
    override val successComponent: TreeComponent<Script> = WithdrawFood(script)
    override val failedComponent: TreeComponent<Script> = ShouldEatFoodInBank(script)

    override fun validate(): Boolean {
        return Inventory.count(script.settings.foodName) != script.settings.foodAmount
    }
}

class ShouldEatFoodInBank(script: Script) : Branch<Script>(script, "Eat food in bank?") { //DONE... test
    override val successComponent: TreeComponent<Script> = EatFood(script)
    override val failedComponent: TreeComponent<Script> = ShouldWithdrawRunes(script)

    override fun validate(): Boolean {
        return Combat.healthPercent() <= script.settings.eatAtHealthPercentage
    }
}

class ShouldWithdrawRunes(script: Script) : Branch<Script>(script, "Withdraw runes?") {
    override val successComponent: TreeComponent<Script> = WithdrawRunes(script)
    override val failedComponent: TreeComponent<Script> = ShouldWithdrawEnsouledheads(script)

    override fun validate(): Boolean {
        return !ReanimationSpell.haveRunes(script.settings.ReanimationSpell, script.settings.takeAmountOfCastRunes)
    }
}

class ShouldWithdrawEnsouledheads(script: Script) : Branch<Script>(script, "Withdraw ensouled heads?") {
    override val successComponent: TreeComponent<Script> = WithdrawEnsouledHeads(script)
    override val failedComponent: TreeComponent<Script> = CloseBank(script)

    override fun validate(): Boolean {
        return !Inventory.isFull()
    }
}

