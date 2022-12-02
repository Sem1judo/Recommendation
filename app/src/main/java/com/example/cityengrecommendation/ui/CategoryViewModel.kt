package com.example.cityengrecommendation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.livedata.core.R
import com.example.cityengrecommendation.data.CategoryDataProvider
import com.example.cityengrecommendation.data.RecommendationsDataProvider
import com.example.cityengrecommendation.model.Category
import com.example.cityengrecommendation.model.Recommendation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class CategoryViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        UiState(
            categoryList = CategoryDataProvider.getCategoryData(),
            recommendationList = RecommendationsDataProvider.getRecommendationData(),
            currentCategory = CategoryDataProvider.getCategoryData().getOrElse(0) {
                CategoryDataProvider.defaultCategory
            },
            currentRecommendation = RecommendationsDataProvider.getRecommendationData()
                .getOrElse(0) {
                    RecommendationsDataProvider.defaultRecommendation
                }

        )
    )
    val uiState: StateFlow<UiState> = _uiState

    fun updateCurrentCategory(selectedCategory: Category) {
        _uiState.update {
            it.copy(currentCategory = selectedCategory)
        }
    }

    fun updateCurrentRecommendation(selectedRecommendation: Recommendation) {
        _uiState.update {
            it.copy(currentRecommendation = selectedRecommendation)
        }
    }

    fun navigateToListPage() {
        _uiState.update {
            it.copy(isShowingListPage = true)
        }
    }
    fun navigateToListRecommended() {
        _uiState.update {
            it.copy(isShowingListPage = false,isShowingListPageRecommendation = true)

        }
    }


    fun navigateToDetailPage() {
        _uiState.update {
            it.copy(isShowingListPageRecommendation = false,isShowingListPage = false)
        }
    }
}

data class UiState(
    val categoryList: List<Category> = emptyList(),
    val recommendationList: List<Recommendation> = emptyList(),
    val currentCategory: Category = CategoryDataProvider.defaultCategory,
    val currentRecommendation: Recommendation = RecommendationsDataProvider.defaultRecommendation,
    val isShowingListPage: Boolean = true,
    val isShowingListPageRecommendation: Boolean = false,
)
