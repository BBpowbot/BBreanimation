package com.barcodeyboi.reanimation.helpers

import com.barcodeyboi.reanimation.Constants.ID_RUNE_BLOOD
import com.barcodeyboi.reanimation.Constants.ID_RUNE_BODY
import com.barcodeyboi.reanimation.Constants.ID_RUNE_NATURE
import com.barcodeyboi.reanimation.Constants.ID_RUNE_SOUL
import com.barcodeyboi.reanimation.extensions.count
import com.barcodeyboi.reanimation.extensions.mustWithdrawItem
import org.powbot.api.Condition
import org.powbot.api.rt4.Bank
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Magic
import org.powbot.api.rt4.walking.model.Skill

object ReanimationSpell {

    private val runesRequirementMap = mapOf(
        "Basic Reanimation" to mapOf(ID_RUNE_BODY to 4, ID_RUNE_NATURE to 2),
        "Adept Reanimation" to mapOf(ID_RUNE_BODY to 4, ID_RUNE_NATURE to 3, ID_RUNE_SOUL to 1),
        "Expert Reanimation" to mapOf(ID_RUNE_BLOOD to 1, ID_RUNE_NATURE to 3, ID_RUNE_SOUL to 2),
        "Master Reanimation" to mapOf(ID_RUNE_BLOOD to 2, ID_RUNE_NATURE to 4, ID_RUNE_SOUL to 4),
    )

    private val magicRequirementMap = mapOf(
        "Basic Reanimation" to 16,
        "Adept Reanimation" to 41,
        "Expert Reanimation" to 72,
        "Master Reanimation" to 90,
    )

    private val magicSpellMap = mapOf(
        "Basic Reanimation" to Magic.ArceuusSpell.BASIC_REANIMATION,
        "Adept Reanimation" to Magic.ArceuusSpell.ADEPT_REANIMATION,
        "Expert Reanimation" to Magic.ArceuusSpell.EXPERT_REANIMATION,
        "Master Reanimation" to Magic.ArceuusSpell.MASTER_REANIMATION,
    )

    //TODO: feature: add rune pouch support
    fun haveRunes(reanimationSpell: String, castAmount: Int): Boolean {
        val runeMap = runesRequirementMap[reanimationSpell]
        runeMap!!.forEach {
            val minRuneCount = it.value * castAmount
            if (Inventory.count(it.key) < minRuneCount) {
                return false
            }
        }
        return true;
    }

    //TODO: feature: add rune pouch support
    fun withdrawRequiredRunes(reanimationSpell: String, castAmount: Int) {
        val runeMap = runesRequirementMap[reanimationSpell]
        runeMap!!.forEach {
            val minRuneCount = it.value * castAmount
            if (Inventory.count(it.key) < minRuneCount) {
                if (Bank.mustWithdrawItem(it.key, minRuneCount)) {
                    Condition.wait { Inventory.count(it.key) >= minRuneCount }
                }
            }
        }
    }

    fun hasRequiredMagicLevel(reanimationSpell: String): Boolean {
        if (Skill.Magic.realLevel() >= magicRequirementMap[reanimationSpell]!!) {
            return true
        }
        return false
    }

    fun getMagicSpell(reanimationSpell: String): Magic.ArceuusSpell {
        return magicSpellMap[reanimationSpell]!!
    }

}