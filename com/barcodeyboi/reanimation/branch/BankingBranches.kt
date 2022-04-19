package com.barcodeyboi.reanimation.branch

import com.barcodeyboi.reanimation.Constants.AREA_WINTERTODT
import com.barcodeyboi.reanimation.Constants.IDS_GAMESNECKLACE
import com.barcodeyboi.reanimation.Script
import com.barcodeyboi.reanimation.extensions.isNotEmpty
import com.barcodeyboi.reanimation.leaf.OpenBank
import com.barcodeyboi.reanimation.leaf.TeleportToWintertodt
import com.barcodeyboi.reanimation.leaf.WalkToWintertodt
import org.powbot.api.rt4.Equipment
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Players
import org.powbot.api.script.tree.Branch
import org.powbot.api.script.tree.TreeComponent

class InWintertodt(script: Script) : Branch<Script>(script, "In Wintertodt") {
    override val successComponent: TreeComponent<Script> = OpenBank(script)
    override val failedComponent: TreeComponent<Script> = ShouldTeleportToWintertodt(script)

    override fun validate(): Boolean {
        return AREA_WINTERTODT.contains(Players.local())
    }
}

class ShouldTeleportToWintertodt(script: Script) : Branch<Script>(script, "In Wintertodt") {
    override val successComponent: TreeComponent<Script> = TeleportToWintertodt(script)
    override val failedComponent: TreeComponent<Script> = WalkToWintertodt(script)

    override fun validate(): Boolean {
        return script.settings.useGamesNecklace && (Inventory.isNotEmpty(*IDS_GAMESNECKLACE) || Equipment.isNotEmpty(
            *IDS_GAMESNECKLACE
        ))
    }
}