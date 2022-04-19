package com.barcodeyboi.reanimation.leaf

import com.barcodeyboi.reanimation.Constants.AREA_WINTERTODT
import com.barcodeyboi.reanimation.Script
import org.powbot.api.rt4.Movement
import org.powbot.api.rt4.Players
import org.powbot.api.script.tree.Leaf

class WalkToWintertodt(script: Script) : Leaf<Script>(script, "Walking to wintertodt.") {
    override fun execute() {
        Movement
            .builder(AREA_WINTERTODT.centralTile)
            .setRunMin(20)
            .setRunMax(50)
            .setWalkUntil { AREA_WINTERTODT.contains(Players.local()) }
            .move()
    }
}