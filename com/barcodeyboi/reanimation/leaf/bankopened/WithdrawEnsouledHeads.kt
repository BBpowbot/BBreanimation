package com.barcodeyboi.reanimation.leaf.bankopened

import com.barcodeyboi.reanimation.Script
import com.barcodeyboi.reanimation.extensions.mustWithdrawItem
import org.powbot.api.Condition
import org.powbot.api.rt4.Bank
import org.powbot.api.rt4.Inventory
import org.powbot.api.script.tree.Leaf

class WithdrawEnsouledHeads(script: Script) : Leaf<Script>(script, "Withdrawing ensouled head.") {
    override fun execute() {
        if (Bank.mustWithdrawItem(script.settings.EnsouledHeadType, Inventory.emptySlotCount())) {
            Condition.wait { Inventory.isFull() }
        }
    }
}