package com.barcodeyboi.reanimation.leaf.bankopened

import com.barcodeyboi.reanimation.Constants.IDS_SUPERANTIPOISON
import com.barcodeyboi.reanimation.Constants.ID_SUPERANTIPOISON_4DOSE
import com.barcodeyboi.reanimation.Script
import com.barcodeyboi.reanimation.extensions.isNotEmpty
import com.barcodeyboi.reanimation.extensions.mustWithdrawItem
import org.powbot.api.Condition
import org.powbot.api.rt4.Bank
import org.powbot.api.rt4.Inventory
import org.powbot.api.script.tree.Leaf

class WithdrawAntiPoison(script: Script) : Leaf<Script>(script, "Withdrawing anti-poison.") {
    override fun execute() {
        if (Bank.mustWithdrawItem(ID_SUPERANTIPOISON_4DOSE, 1)) {
            Condition.wait { Inventory.isNotEmpty(*IDS_SUPERANTIPOISON) }
        }
    }
}