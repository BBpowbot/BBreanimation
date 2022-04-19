package com.barcodeyboi.reanimation.leaf.bankopened

import com.barcodeyboi.reanimation.Constants.IDS_GAMESNECKLACE
import com.barcodeyboi.reanimation.Script
import com.barcodeyboi.reanimation.extensions.isNotEmpty
import com.barcodeyboi.reanimation.extensions.mustWithdrawItem
import org.powbot.api.Condition
import org.powbot.api.rt4.Bank
import org.powbot.api.rt4.Inventory
import org.powbot.api.script.tree.Leaf

class WithdrawGamesNecklace(script: Script) : Leaf<Script>(script, "Withdrawing games necklace.") {
    override fun execute() {
        if (Bank.mustWithdrawItem(IDS_GAMESNECKLACE, 1)) {
            Condition.wait { Inventory.isNotEmpty(*IDS_GAMESNECKLACE) }
        }
    }
}


