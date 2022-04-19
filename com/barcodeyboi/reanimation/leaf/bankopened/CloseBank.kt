package com.barcodeyboi.reanimation.leaf.bankopened

import com.barcodeyboi.reanimation.Script
import org.powbot.api.Condition
import org.powbot.api.rt4.Bank
import org.powbot.api.script.tree.Leaf

class CloseBank(script: Script) : Leaf<Script>(script, "Closing bank.") {
    override fun execute() {
        if (Bank.close()) {
            Condition.wait { !Bank.opened() }
        }
    }
}