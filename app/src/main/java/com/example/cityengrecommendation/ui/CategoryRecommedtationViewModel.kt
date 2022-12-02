package com.example.cityengrecommendation.ui

import androidx.lifecycle.ViewModel
import com.example.cityengrecommendation.data.CategoryDataProvider
import com.example.cityengrecommendation.data.RecommendationsDataProvider
import com.example.cityengrecommendation.model.Category
import com.example.cityengrecommendation.model.Recommendation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CourseViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        UiState(
            categoryList = CategoryDataProvider.getCategoryData(),
            recommendationList = RecommendationsDataProvider.getRecommendationData(),
            currentCategory = CategoryDataProvider.getCategoryData().getOrElse(0) {
                CategoryDataProvider.defaultCategory
            },
            currentRecommendation = RecommendationsDataProvider.getRecommendationData().getOrElse(0) {
                RecommendationsDataProvider.defaultRecommendation
            }

        )
    )
    val uiState: StateFlow<UiState> = _uiState
}

data class UiState(
    val categoryList: List<Category> = emptyList(),
    val recommendationList: List<Recommendation> = emptyList(),
    val currentCategory: Category = CategoryDataProvider.defaultCategory,
    val currentRecommendation: Recommendation = RecommendationsDataProvider.defaultRecommendation,
    val isShowingListPage: Boolean = true
)
