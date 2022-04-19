package com.barcodeyboi.reanimation.branch

import com.barcodeyboi.reanimation.Script
import org.powbot.api.rt4.Bank
import org.powbot.api.script.tree.Branch
import org.powbot.api.script.tree.TreeComponent


class IsBankOpened(script: Script) : Branch<Script>(script, "Bank open") {
    override val successComponent: TreeComponent<Script> = ShouldWithdrawAntiPoison(script)
    override val failedComponent: TreeComponent<Script> = NeedToBank(script)

    override fun validate(): Boolean {
        return Bank.opened()
    }
}