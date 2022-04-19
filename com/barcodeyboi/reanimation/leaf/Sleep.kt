package com.barcodeyboi.reanimation.leaf

import com.barcodeyboi.reanimation.Script
import org.powbot.api.script.tree.Leaf

class Sleep(script: Script) : Leaf<Script>(script, "Waiting.") {
    override fun execute() {
        //Do nothing...
    }
}