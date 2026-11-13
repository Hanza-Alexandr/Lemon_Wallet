package com.example.bdui_screen.present.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.bdui_screen.domain.BDUIAction
import com.example.bdui_screen.domain.BDUIComponent
import com.example.bdui_screen.present.BDUIStateUI
import com.example.bdui_screen.present.BDUIViewModel


@Composable
fun BDUIScreen(viewModel: BDUIViewModel = hiltViewModel()){
    val promoStateUi by viewModel.promoState.collectAsStateWithLifecycle()
    when (val state = promoStateUi){
        is BDUIStateUI.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = Color.Blue)
            }
        }
        is BDUIStateUI.Loaded -> {
            PromoScreen(state.components) {

            }
        }
    }
}


@Composable
fun PromoScreen(
    components: List<BDUIComponent>, // Сюда приходит стейт из ViewModel
    onAction: (BDUIAction) -> Unit   // Сюда передаем функцию отправки Intent'а
) {
    LazyColumn(modifier = Modifier.fillMaxSize().systemBarsPadding()) {
        items(
            items = components,
            key = { it.id }
        ) { component ->
            BDUIComponentRenderer(
                component = component,
                onAction = onAction
            )
        }
    }
}

@Composable
fun BDUIComponentRenderer(
    component: BDUIComponent,
    onAction: (BDUIAction) -> Unit,
    modifier: Modifier = Modifier
) {
    when (component) {
        is BDUIComponent.HeaderBlock -> HeaderBlockView(component, modifier)
        is BDUIComponent.TextBlock -> TextBlockView(component, modifier)
        is BDUIComponent.ImageBlock -> ImageBlockView(component, modifier)
        is BDUIComponent.Spacer -> SpacerView(component, modifier)
        is BDUIComponent.Divider -> DividerView(modifier)
        is BDUIComponent.HorizontalCarousel -> HorizontalCarouselView(component, onAction, modifier)
        is BDUIComponent.VerticalCarousel -> VerticalCarouselView(component, onAction, modifier)
        is BDUIComponent.ActionArea -> ActionAreaView(component, onAction, modifier)
        is BDUIComponent.Unknows -> {
            // Fallback: ничего не рисуем. Экран не падает.
        }
    }
}

@Composable
fun HeaderBlockView(component: BDUIComponent.HeaderBlock, modifier: Modifier = Modifier) {
    Text(
        text = component.header,
        style = MaterialTheme.typography.titleLarge,
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun TextBlockView(component: BDUIComponent.TextBlock, modifier: Modifier = Modifier) {
    Text(
        text = component.text,
        style = MaterialTheme.typography.bodyMedium,
        modifier = modifier.padding(horizontal = 16.dp)
    )
}

@Composable
fun ImageBlockView(component: BDUIComponent.ImageBlock, modifier: Modifier = Modifier) {
    AsyncImage(
        model = component.url,
        contentDescription = null,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}
@Composable
fun SpacerView(component: BDUIComponent.Spacer, modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.height(component.dp.dp))
}

@Composable
fun DividerView(modifier: Modifier = Modifier) {
    HorizontalDivider(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        color = MaterialTheme.colorScheme.outlineVariant
    )
}

@Composable
fun HorizontalCarouselView(
    component: BDUIComponent.HorizontalCarousel,
    onAction: (BDUIAction) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp) // Отступы между карточками
    ) {
        items(
            items = component.components,
            key = { it.id } // Compose будет перерисовывать только изменившиеся элементы
        ) { childComponent ->
            // Рекурсивно вызываем диспетчер для отрисовки вложенного элемента
            BDUIComponentRenderer(component = childComponent, onAction = onAction)
        }
    }
}

@Composable
fun VerticalCarouselView(
    component: BDUIComponent.VerticalCarousel,
    onAction: (BDUIAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        component.components.forEach { childComponent ->
            BDUIComponentRenderer(component = childComponent, onAction = onAction)
        }
    }
}

@Composable
fun ActionAreaView(
    component: BDUIComponent.ActionArea,
    onAction: (BDUIAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.clickable {
            onAction(component.action) // Отправляем событие наверх (во ViewModel)
        }
    ) {
        // Отрисовываем сам компонент, который обернули (картинку, текст и т.д.)
        BDUIComponentRenderer(
            component = component.component,
            onAction = onAction
        )
    }
}