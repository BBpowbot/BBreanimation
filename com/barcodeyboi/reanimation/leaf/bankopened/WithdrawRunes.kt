package com.barcodeyboi.reanimation.leaf.bankopened

import com.barcodeyboi.reanimation.Script
import com.barcodeyboi.reanimation.helpers.ReanimationSpell
import org.powbot.api.script.tree.Leaf

class WithdrawRunes(script: Script) : Leaf<Script>(script, "Withdrawing runes.") {
    override fun execute() {
        ReanimationSpell.withdrawRequiredRunes(script.settings.ReanimationSpell, script.settings.takeAmountOfCastRunes)
    }
}