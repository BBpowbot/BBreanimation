package com.barcodeyboi.reanimation

import org.powbot.api.Area
import org.powbot.api.Tile

object Constants {
    val IDS_GAMESNECKLACE = intArrayOf(3853, 3855, 3857, 3859, 3861, 3863, 3865, 3867)
    val IDS_SUPERANTIPOISON = intArrayOf(2448, 181, 183, 185)
    const val ID_SUPERANTIPOISON_4DOSE = 2448

    const val ID_RUNE_BODY = 559
    const val ID_RUNE_NATURE = 561
    const val ID_RUNE_SOUL = 566
    const val ID_RUNE_BLOOD = 565

    val NAME_ANTIDRAGONSHIELDS = arrayOf("Anti-dragon shield", "Dragonfire shield", "Dragonfire ward")

    val AREA_WINTERTODT = Area(
        Tile(1615, 3935),
        Tile(1622, 3929),
        Tile(1631, 3931),
        Tile(1644, 3936),
        Tile(1647, 3941),
        Tile(1645, 3950),
        Tile(1635, 3953),
        Tile(1624, 3953),
        Tile(1615, 3944)
    );

    val AREA_DARKALTAR = Area(Tile(1707, 3885, 0), Tile(1712, 3882, 0))

}