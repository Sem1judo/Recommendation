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
import com.example.cityengrecommendation.model.Recommendation


/**
 * Sports data
 */
object RecommendationsDataProvider {
    val defaultRecommendation = getRecommendationData()[0]

    fun getRecommendationData(): List<Recommendation> {
        return listOf(
            Recommendation(
                id = 1,
                titleResourceId = R.string.english,
                subtitleResourceId = R.string.english_subtitle,
                description = R.string.english_subtitle,
                imageResourceId = R.drawable.ic_baseball_square,
            ),
            Recommendation(
                id = 2,
                titleResourceId = R.string.german,
                subtitleResourceId = R.string.german_subtitle,
                description = R.string.english_subtitle,

                imageResourceId = R.drawable.ic_badminton_square,

                ),
            Recommendation(
                id = 3,
                titleResourceId = R.string.spanish,
                subtitleResourceId = R.string.spanish_subtitle,
                description = R.string.english_subtitle,
                imageResourceId = R.drawable.ic_basketball_square
            )
        )

    }
}