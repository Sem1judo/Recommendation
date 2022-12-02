/*
 * Copyright (c) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.cityengrecommendation.data

import com.example.cityengrecommendation.R
import com.example.cityengrecommendation.model.Category


/**
 * Sports data
 */
object CategoryDataProvider {
    val defaultCategory = getCategoryData()[0]

    fun getCategoryData(): List<Category> {
        return listOf(
            Category(
                id = 1,
                titleResourceId = R.string.shaurma,
                subtitleResourceId = R.string.baseball_subtitle,
                imageResourceId = R.drawable.ic_baseball_square,
            ),
            Category(
                id = 2,
                titleResourceId = R.string.pizza,
                subtitleResourceId = R.string.badminton_subtitle,
                imageResourceId = R.drawable.ic_badminton_square,
            ),
            Category(
                id = 3,
                titleResourceId = R.string.sushi,
                subtitleResourceId = R.string.basketball_subtitle,
                imageResourceId = R.drawable.ic_basketball_square,
            ),
            Category(
                id = 4,
                titleResourceId = R.string.soup,
                subtitleResourceId = R.string.bowling_subtitle,
                imageResourceId = R.drawable.ic_bowling_square,
            ),
            Category(
                id = 5,
                titleResourceId = R.string.rice,
                subtitleResourceId = R.string.cycling_subtitle,
                imageResourceId = R.drawable.ic_cycling_square,
            ),
        )
    }
}
