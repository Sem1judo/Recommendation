
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
                categoryId = 1

            ),
            Recommendation(
                id = 2,
                titleResourceId = R.string.english,
                subtitleResourceId = R.string.english_subtitle,
                description = R.string.english_subtitle,
                imageResourceId = R.drawable.ic_badminton_square,
                categoryId = 1

            ),
            Recommendation(
                id = 3,
                titleResourceId = R.string.german,
                subtitleResourceId = R.string.german_subtitle,
                description = R.string.english_subtitle,
                imageResourceId = R.drawable.ic_badminton_square,
                categoryId = 2

            ),
            Recommendation(
                id = 4,
                titleResourceId = R.string.german,
                subtitleResourceId = R.string.german_subtitle,
                description = R.string.english_subtitle,
                imageResourceId = R.drawable.ic_basketball_square,
                categoryId = 2

            ),
            Recommendation(
                id = 5,
                titleResourceId = R.string.spanish,
                subtitleResourceId = R.string.spanish_subtitle,
                description = R.string.english_subtitle,
                imageResourceId = R.drawable.ic_basketball_square,
                categoryId = 3
            ),
            Recommendation(
                id = 6,
                titleResourceId = R.string.spanish,
                subtitleResourceId = R.string.spanish_subtitle,
                description = R.string.english_subtitle,
                imageResourceId = R.drawable.ic_badminton_square,
                categoryId = 3
            )

        )

    }
}
