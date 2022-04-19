package com.barcodeyboi.reanimation.leaf

import com.barcodeyboi.reanimation.Constants
import com.barcodeyboi.reanimation.Script
import org.powbot.api.rt4.Movement
import org.powbot.api.rt4.Players
import org.powbot.api.script.tree.Leaf

class WalkToDarkAltarArea(script: Script) : Leaf<Script>(script, "Walking to Dark Altar.") {
    override fun execute() {
        Movement
            .builder(Constants.AREA_DARKALTAR.randomTile)
            .setRunMin(20)
            .setRunMax(50)
            .setWalkUntil { Constants.AREA_DARKALTAR.contains(Players.local()) }
            .move()
    }
}