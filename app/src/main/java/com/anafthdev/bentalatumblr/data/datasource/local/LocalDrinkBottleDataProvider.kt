package com.anafthdev.bentalatumblr.data.datasource.local

import com.anafthdev.bentalatumblr.data.model.db.DrinkBottle

object LocalDrinkBottleDataProvider {

    /**
     * Custom bottle, use this bottle by copying (changing) the volume
     */
    val customBottle = DrinkBottle(
        id = 0,
        volume = 0.0,
        defaultBottle = true
    )

    val drinkBottle125Ml = DrinkBottle(
        id = 1,
        volume = 125.0,
        defaultBottle = true
    )

    val drinkBottle175Ml = DrinkBottle(
        id = 2,
        volume = 175.0,
        defaultBottle = true
    )

    val drinkBottle225Ml = DrinkBottle(
        id = 3,
        volume = 225.0,
        defaultBottle = true
    )

    val drinkBottle300Ml = DrinkBottle(
        id = 4,
        volume = 300.0,
        defaultBottle = true
    )

    val drinkBottle400Ml = DrinkBottle(
        id = 5,
        volume = 400.0,
        defaultBottle = true
    )

    val drinkBottle550Ml = DrinkBottle(
        id = 6,
        volume = 550.0,
        defaultBottle = true
    )

    val defaultBottles = arrayOf(
        drinkBottle125Ml,
        drinkBottle175Ml,
        drinkBottle225Ml,
        drinkBottle300Ml,
        drinkBottle400Ml,
        drinkBottle550Ml,
    )

    fun getRandomDefaultBottle(): DrinkBottle {
        return defaultBottles.random()
    }

}
