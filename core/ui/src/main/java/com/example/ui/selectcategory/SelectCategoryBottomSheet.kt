package com.example.ui.selectcategory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Category
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class CategoryUISelected(
    val id: String,
    val name: String,
    val isSelected: Boolean,
    val hasSubCategories: Boolean,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategorySelectBottomSheet(
){

    CategorySelectBottomSheetContent(
        categoryList = TODO(),
        parentCategory = TODO(),
        onDismissRequest = TODO(),
        onArrowClick = TODO(),
        onBackClick = TODO(),
        isShow = TODO()
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategorySelectBottomSheetContent(
    categoryList:Set<CategoryUISelected>,
    parentCategory: CategoryUISelected? = null,
    onDismissRequest: () -> Unit,
    onArrowClick: (id:String) -> Unit,
    onBackClick: () -> Unit,
    isShow: Boolean
){
    if (isShow){
        ModalBottomSheet(onDismissRequest = onDismissRequest
        ) {
            val scrollState = rememberScrollState()


            if (parentCategory!=null){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(
                            modifier = Modifier.size(18.dp),
                            onClick = onBackClick
                        ) {
                            Icon(Icons.Default.ArrowBackIosNew, contentDescription = null,)
                        }
                        Spacer(Modifier.width(12.dp))
                        Text(
                            text = "Выбор категории",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }


                    TextButton(onClick = { /* Логика создания новой */ }) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(text = "Новая", fontSize = 16.sp)
                    }
                }

                CategorySelectItem(
                    id = parentCategory.id,
                    name = parentCategory.name,
                    icon = Icons.Default.Category,
                    isSelected = parentCategory.isSelected,
                    hasSubCategories = false,
                    isParentHeader = true,
                    onItemClick = {}
                )

                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    thickness = 1.dp,
                    color = Color.LightGray.copy(alpha = 0.5f)
                )
            }
            else{
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Выбор категории",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    TextButton(onClick = { /* Логика создания новой */ }) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(text = "Новая", fontSize = 16.sp)
                    }
                }
            }
            Column(Modifier.verticalScroll(scrollState)) {
                categoryList.forEach { category ->
                    CategorySelectItem(
                        id = category.id,
                        name = category.name,
                        icon = Icons.Default.Category,
                        isSelected = category.isSelected,
                        hasSubCategories = category.hasSubCategories,
                        onItemClick = {
                            //Выделение и выбор
                        },
                        onArrowClick = { if (category.hasSubCategories) onArrowClick(category.id) else null }
                    )
                }
            }
        }
    }
}
@Preview
@Composable
fun CategorySelectBottomSheetPreview(){
    CategorySelectBottomSheetContent(setOf(
        CategoryUISelected("1","Еда и напитки",false,true),
        CategoryUISelected("2","Покупки",false,true),
        CategoryUISelected("3","Доход",false,false),
        CategoryUISelected("4","Одежда",false,false),
        CategoryUISelected("5","Подписки",false,false),
    ),
        CategoryUISelected("5","Подписки",false,false),
        {},
        {},
        onBackClick = {},
        true
    )
}