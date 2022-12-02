package com.example.cityengrecommendation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cityengrecommendation.data.CategoryDataProvider
import com.example.cityengrecommendation.data.RecommendationsDataProvider
import com.example.cityengrecommendation.model.Category
import com.example.cityengrecommendation.model.Recommendation
import com.example.cityengrecommendation.ui.theme.CityEngRecommendationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CityEngRecommendationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                }
            }
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
    selectedCategory: Category,
    recommendationsList: List<Recommendation>,
    onClick: (Recommendation) -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(recommendationsList,
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
fun ListItemPreview() {
    CityEngRecommendationTheme {
        CategoriesList(
            categoriesList = CategoryDataProvider.getCategoryData(),
            onClick = {}
        )
    }
}

@Composable
@Preview(showBackground = true)
fun RecommendationsItemPreview() {
    CityEngRecommendationTheme {
        RecommendationsList(
            recommendationsList = RecommendationsDataProvider.getRecommendationData(),
            onClick = {},
            onBackPressed = {},
            selectedCategory = CategoryDataProvider.defaultCategory
        )
    }
}
