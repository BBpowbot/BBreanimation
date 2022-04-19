package com.barcodeyboi.reanimation.leaf

import com.barcodeyboi.reanimation.Script
import org.powbot.api.Condition
import org.powbot.api.rt4.*
import org.powbot.api.script.tree.Leaf

class SwapSpellBook(script: Script) : Leaf<Script>(script, "Swapping spellbook.") {
    override fun execute() {
        val tyss = Npcs.stream().name("Tyss").first()
        if (tyss != Npc.Nil) {
            if (tyss.inViewport()) {
                if (tyss.interact("Spellbook")) {
                    Condition.wait { Magic.book().name == Magic.Book.ARCEUUS.name }
                }
            } else {
                Movement.moveTo(tyss)
                Camera.turnTo(tyss)
            }

        }
    }
}