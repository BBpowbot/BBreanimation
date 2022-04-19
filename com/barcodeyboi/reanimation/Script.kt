package com.barcodeyboi.reanimation

import com.barcodeyboi.reanimation.Constants.NAME_ANTIDRAGONSHIELDS
import com.barcodeyboi.reanimation.branch.IsBankOpened
import com.barcodeyboi.reanimation.helpers.ReanimationSpell
import com.google.common.eventbus.Subscribe
import org.powbot.api.Color
import org.powbot.api.Notifications
import org.powbot.api.event.MessageEvent
import org.powbot.api.rt4.Equipment
import org.powbot.api.rt4.walking.model.Skill
import org.powbot.api.script.*
import org.powbot.api.script.paint.Paint
import org.powbot.api.script.paint.PaintBuilder
import org.powbot.api.script.tree.TreeComponent
import org.powbot.api.script.tree.TreeScript
import org.powbot.mobile.script.ScriptManager
import org.powbot.mobile.service.ScriptUploader
import java.util.logging.Logger

@ScriptManifest(
    name = "BB Arceuus Reanimation",
    description = "Reanimates ensouled heads for prayer experience.",
    version = "1.0",
    category = ScriptCategory.Prayer,
    author = "barcodeyboi"
)
@ScriptConfiguration.List(
    [
        ScriptConfiguration(
            name = "Spell",
            description = "Reanimation spell type you wish to use.",
            optionType = OptionType.STRING,
            defaultValue = "Basic Reanimation",
            allowedValues = arrayOf(
                "Basic Reanimation",
                "Adept Reanimation",
                "Expert Reanimation",
                "Master Reanimation"
            )
        ),
        ScriptConfiguration(
            name = "Ensouled head",
            description = "Ensouled head type you wish to use.",
            optionType = OptionType.STRING,
            defaultValue = "Ensouled Goblin",
            allowedValues = arrayOf(
                "Ensouled Goblin head",
                "Ensouled Monkey head",
                "Ensouled Imp head",
                "Ensouled Minotaur head",
                "Ensouled Scorpion head",
                "Ensouled Bear head",
                "Ensouled Unicorn head"
            )
        ),
        ScriptConfiguration(
            name = "Food name",
            description = "Food type you wish to use.",
            optionType = OptionType.STRING,
            defaultValue = "Shark",
            allowedValues = arrayOf(
                "Manta ray",
                "Shark",
                "Monkfish",
                "Swordfish",
                "Lobster",
                "Tuna",
                "Salmon",
                "Cooked karambwan",
                "Tuna potato",
                "Anglerfish"
            )
        ),
        ScriptConfiguration(
            name = "Food amount",
            description = "Amount of food you wish to take with you.",
            optionType = OptionType.STRING,
            defaultValue = "1",
            allowedValues = arrayOf(
                "1",
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8",
                "9",
                "10",
            )
        ),
        ScriptConfiguration(
            name = "Movement",
            description = "Use games necklace to return to bank?",
            optionType = OptionType.BOOLEAN,
            defaultValue = "false",
        ),
        ScriptConfiguration(
            name = "Games necklace",
            description = "Equip games necklace?",
            optionType = OptionType.BOOLEAN,
            defaultValue = "false",
            visible = false,
        ),
    ]
)
class Script : TreeScript() {
    private val logger = Logger.getLogger(this.javaClass.name)

    override val rootComponent: TreeComponent<*> by lazy {
        IsBankOpened(this)
    }

    lateinit var settings: Settings

    override fun onStart() {
        val reanimationSpell = getOption<String>("Spell")
        val ensouledHead = getOption<String>("Ensouled head")
        val useGamesNecklace = getOption<Boolean>("Movement")
        val equipGamesNecklace = getOption<Boolean>("Games necklace")
        val foodName = getOption<String>("Food name")
        val foodCount = getOption<String>("Food amount").toInt()
        var needAntipoison = false
        if(ensouledHead == "Ensouled Kalphite head") { needAntipoison = true }
        settings = Settings(reanimationSpell, ensouledHead, useGamesNecklace, equipGamesNecklace, foodName, foodCount, needAntipoison)

        addPaint()
        checkEquipmentRequirement()
        checkMagicRequirement()
    }

    @ValueChanged("Spell")
    fun reanimationSpellUpdated(updatedValue: String) {
        val updatedEnsouledHead = when (updatedValue) {
            "Basic Reanimation" -> arrayOf(
                "Ensouled Goblin head",
                "Ensouled Monkey head",
                "Ensouled Imp head",
                "Ensouled Minotaur head",
                "Ensouled Scorpion head",
                "Ensouled Bear head",
                "Ensouled Unicorn head"
            )
            "Adept Reanimation" -> arrayOf(
                "Ensouled Dog head",
                "Ensouled Chaos druid head",
                "Ensouled Ogre head",
                "Ensouled Giant head",
                "Ensouled elf head",
                "Ensouled Troll head",
                "Ensouled Horror head"
            )
            "Expert Reanimation" -> arrayOf(
                "Ensouled Kalphite head",
                "Ensouled Dagannoth head",
                "Ensouled Bloodveld head",
                "Ensouled TzHaar head",
                "Ensouled Demon head"
            )
            "Master Reanimation" -> arrayOf("Ensouled Aviansie head", "Ensouled Abyssal head", "Ensouled Dragon head")
            else -> arrayOf()
        }
        updateAllowedOptions("Ensouled head", updatedEnsouledHead)
    }

    @ValueChanged("Movement")
    fun movementUpdated(updatedValue: Boolean) {
        val updatedMovement = when (updatedValue) {
            false -> false
            true -> true
        }
        updateVisibility("Games necklace", updatedMovement)
    }

    private fun checkMagicRequirement() {
        if (!ReanimationSpell.hasRequiredMagicLevel(settings.ReanimationSpell)) {
            Notifications.showNotification("Magic level not high enough.")
            ScriptManager.stop()
            return
        }
    }

    private fun checkEquipmentRequirement() {
        if (settings.EnsouledHeadType == "Ensouled Dragon head") {
            val offHandItemName = Equipment.itemAt(Equipment.Slot.OFF_HAND).name()
            if (!NAME_ANTIDRAGONSHIELDS.contains(offHandItemName)) {
                Notifications.showNotification("Equip an anti-dragon shield.")
                ScriptManager.stop()
                return
            }
        }
    }

    private fun addPaint() {
        val p: Paint = PaintBuilder.newBuilder()
            .addString("Ensouled head:") { settings.EnsouledHeadType }
            .addString("Leaf:") { lastLeaf.name }
            .trackSkill(Skill.Prayer)
            .backgroundColor(Color.argb(255, 102, 255, 102))
            .textColor(Color.argb(255, 0, 0, 0))
            .build()
        addPaint(p)
    }

    @Subscribe
    fun msgEvent(messageEvent: MessageEvent) {
        if (!messageEvent.sender.isNullOrEmpty()) { //filter
            return
        }
        if (messageEvent.message.contains("Oh dear, you are dead!")) {
            ScriptManager.stop()
        }
    }
}

fun main(args: Array<String>) {
    ScriptUploader().uploadAndStart(
        "BB Arceuus Reanimation",
        "",
        "127.0.0.1:5815",
        portForward = true,
        useDefaultConfigs = false
    )
}



