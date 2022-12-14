package com.example.cityengrecommendation

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.cityengrecommendation.data.CategoryDataProvider
import com.example.cityengrecommendation.data.RecommendationsDataProvider
import com.example.cityengrecommendation.model.Category
import com.example.cityengrecommendation.model.Recommendation
import com.example.cityengrecommendation.ui.CategoryViewModel
import com.example.cityengrecommendation.ui.theme.CityEngRecommendationTheme
import com.example.cityengrecommendation.utils.CityContentType

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CityEngRecommendationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val windowSize = calculateWindowSizeClass(this)
                    CityApp(windowSize = windowSize.widthSizeClass)
                }
            }
        }
    }
}

@Composable
fun CityApp(
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {
    val viewModel: CategoryViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val contentType = when (windowSize) {
        WindowWidthSizeClass.Compact,
        WindowWidthSizeClass.Medium,
        -> CityContentType.ListOnly
        WindowWidthSizeClass.Expanded -> CityContentType.ListAndDetail
        else -> CityContentType.ListOnly

    }
    Scaffold(
        topBar = {
            CityAppBar(
                isShowingListPage = uiState.isShowingListPage,
                isShowingListPageRecommendation = uiState.isShowingListPageRecommendation,
                windowSize = windowSize,
                category = uiState.currentCategory,
                recommendation = uiState.currentRecommendation,
                onBackButtonClickRecommendation = { viewModel.navigateToListPage() },
                onBackButtonClickDetailPage = { viewModel.navigateToListRecommended() }
            )
        }
    ) { innerPadding ->
        if (contentType == CityContentType.ListAndDetail) {
            ListAndDetailRecommendation(
                categoriesList = uiState.categoryList,
                recommendationsList = uiState.recommendationList,
                selectedRecommendation = uiState.currentRecommendation,
                selectedCategory = uiState.currentCategory,
                onClickCategory = {
                    viewModel.updateCurrentCategory(it)
                },
                onClickRecommendation = { viewModel.updateCurrentRecommendation(it) },
                modifier = modifier.padding((innerPadding))
            )
        } else if (uiState.isShowingListPageRecommendation) {
            RecommendationsList(
                recommendationsList = uiState.recommendationList,
                selectedCategory = uiState.currentCategory,
                onClick = {
                    viewModel.navigateToDetailPage()
                    viewModel.updateCurrentRecommendation(it)
                },
                {},
                modifier = modifier.padding((innerPadding))
            )
        } else if (uiState.isShowingListPage) {
            CategoriesList(
                categoriesList = uiState.categoryList,
                onClick = {
                    viewModel.updateCurrentCategory(it)
                    viewModel.navigateToListRecommended()
                },
                modifier = modifier.padding((innerPadding))
            )
        } else if (windowSize != WindowWidthSizeClass.Expanded) {
            RecommendationDetail(
                selectedRecommendation = uiState.currentRecommendation,
                {},
                modifier = modifier.padding((innerPadding))
            )
        }
    }
}

@Composable
fun CityAppBar(
    onBackButtonClickRecommendation: () -> Unit,
    onBackButtonClickDetailPage: () -> Unit,
    isShowingListPage: Boolean,
    isShowingListPageRecommendation: Boolean,
    category: Category,
    recommendation: Recommendation,
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier,
) {

    val isShowingDetailPage =
        windowSize != WindowWidthSizeClass.Expanded && !isShowingListPage && !isShowingListPageRecommendation
    TopAppBar(
        title = {
            Text(
                if (isShowingListPageRecommendation) {
                    stringResource(
                        R.string.category_fragment_label,
                        stringResource(category.titleResourceId)
                    )
                } else if (isShowingDetailPage) {
                    stringResource(
                        R.string.recommended_detail,
                        stringResource(recommendation.titleResourceId)
                    )
                } else {
                    stringResource(R.string.list_fragment_label)
                }
            )
        },
        navigationIcon =
        if (isShowingDetailPage) {
            {
                IconButton(onClick = onBackButtonClickDetailPage) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        } else if (isShowingListPageRecommendation) {
            {
                IconButton(onClick = onBackButtonClickRecommendation) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        } else {
            null
        },
        modifier = modifier
    )
}


@Composable
private fun ListAndDetailRecommendation(
    recommendationsList: List<Recommendation>,
    categoriesList: List<Category>,
    selectedRecommendation: Recommendation,
    selectedCategory: Category,
    onClickRecommendation: (Recommendation) -> Unit,
    onClickCategory: (Category) -> Unit,
    modifier: Modifier = Modifier,
) {

    Row(modifier = modifier.fillMaxWidth()) {
        CategoriesList(
            categoriesList = categoriesList,
            onClick = onClickCategory,
            modifier.weight(1f)
        )
        RecommendationsList(
            recommendationsList = recommendationsList,
            onClick = onClickRecommendation,
            selectedCategory = selectedCategory,
            onBackPressed = {},
            modifier = modifier.weight(1f)
        )
        val activity = (LocalContext.current as Activity)
        if (selectedRecommendation.categoryId == selectedCategory.id) {
            RecommendationDetail(
                selectedRecommendation = selectedRecommendation,
                onBackPressed = { activity.finish() },
                modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun RecommendationDetail(
    selectedRecommendation: Recommendation,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BackHandler {
        onBackPressed()
    }
    Column(
        modifier = modifier.padding(4.dp)
    ) {
        Box {
            Image(
                painter = painterResource(selectedRecommendation.imageResourceId),
                contentDescription = null,
                alignment = Alignment.TopCenter,
                contentScale = ContentScale.FillWidth
            )
            Text(
                text = stringResource(selectedRecommendation.titleResourceId),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onSurface,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomStart)
            )
        }
        Text(
            text = stringResource(selectedRecommendation.titleResourceId),
            style = MaterialTheme.typography.h6,
            color = MaterialTheme.colors.secondaryVariant,
            modifier = Modifier.padding(8.dp)
        )
        Text(
            text = stringResource(selectedRecommendation.description),
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun RecommendationsList(
    recommendationsList: List<Recommendation>,
    selectedCategory: Category,
    onClick: (Recommendation) -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BackHandler {
        onBackPressed()
    }
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(recommendationsList.filter { recommendation -> recommendation.categoryId == selectedCategory.id },
            key = { recommendation -> recommendation.id })
        { recommendation ->
            RecommendationsListItem(
                recommendation = recommendation,
                onItemClick = onClick,
                onBackPressed = onBackPressed
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RecommendationsListItem(
    recommendation: Recommendation,
    onItemClick: (Recommendation) -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    BackHandler {
        onBackPressed()
    }
    Card(
        elevation = 2.dp,
        modifier = modifier,
        onClick = { onItemClick(recommendation) },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 120.dp)
        ) {
            ListImageItem(recommendation)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(recommendation.titleResourceId),
                    style = MaterialTheme.typography.h5,
                )
            }
        }
    }
}


@Composable
fun CategoriesList(
    categoriesList: List<Category>,
    onClick: (Category) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(categoriesList, key = { category -> category.id })
        { category ->
            CategoriesListItem(
                category = category,
                onItemClick = onClick
            )
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoriesListItem(
    category: Category,
    onItemClick: (Category) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(elevation = 2.dp,
        modifier = modifier,
        onClick = { onItemClick(category) }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 120.dp)

        ) {
            ListImageItem(category)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 16.dp)
            ) {
                Text(
                    text = stringResource(category.titleResourceId),
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = stringResource(category.subtitleResourceId),
                    style = MaterialTheme.typography.body1,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Composable
private fun ListImageItem(data: Any, modifier: Modifier = Modifier) {
    var id = 0
    if (data is Category) {
        id = data.imageResourceId
    } else if (data is Recommendation) {
        id = data.imageResourceId
    }
    Box(
        modifier = modifier
            .width(120.dp)
            .fillMaxHeight()
    ) {
        Image(
            painter = painterResource(id),
            contentDescription = null,
            alignment = Alignment.TopCenter,
            contentScale = ContentScale.FillWidth
        )
    }
}

@Composable
@Preview(showBackground = true)
fun RecommendationDetailPreview() {
    CityEngRecommendationTheme {
        RecommendationDetail(
            selectedRecommendation = RecommendationsDataProvider.defaultRecommendation,
            {})
    }
}

@Composable
@Preview(showBackground = true)
fun CategoriesListPreview() {
    CityEngRecommendationTheme {
        CategoriesList(
            categoriesList = CategoryDataProvider.getCategoryData(),
            onClick = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
fun RecommendationsListPreview() {
    CityEngRecommendationTheme {
        RecommendationsList(
            recommendationsList = RecommendationsDataProvider.getRecommendationData(),
            selectedCategory = CategoryDataProvider.defaultCategory,
            onClick = {},
            onBackPressed = {}

        )
    }
}
