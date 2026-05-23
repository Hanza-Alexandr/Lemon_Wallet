package com.example.ui.oeration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.domainmodel.DebitOperation
import com.example.domain.domainmodel.DomainOperation
import com.example.domain.domainmodel.DomainStorage
import com.example.domain.domainmodel.TransferOperation
import com.example.ui.oeration.components.AccountsSection
import com.example.ui.oeration.components.AmountSection
import com.example.ui.oeration.components.CalculatorKeyboard
import com.example.ui.oeration.components.CategoriesSection
import com.example.ui.oeration.components.TransactionTypeSelector
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.reflect.KClass
import kotlin.time.Clock


@Preview
@Composable
fun GlobalDetailsOperationContent(){
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        val expression: String = ""
        val result: String = ""
        val operationType: KClass<out DomainOperation> = TransferOperation::class
        val storage: DomainStorage
        val date =Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val time = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).time

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            AmountSection(
                expression = expression,
                result = result
            )
            TransactionTypeSelector(
                onChangeType = { type ->

                }
            )
            if (operationType == TransferOperation::class){
                Column() {
                    AccountsSection(
                        storages = listOf(),
                        onSelected = { storage ->

                        }
                    )
                    AccountsSection(
                        storages = listOf(),
                        onSelected = { storage ->

                        }
                    )
                }
            }
            else{
                Column() {
                    CategoriesSection(
                        topCategories = listOf(),
                        onSelect = { category ->

                        }
                    )
                    AccountsSection(
                        storages = listOf(),
                        onSelected = { storage ->

                        }
                    )
                }
            }


        }
        CalculatorKeyboard(
            onKeyClick = { key ->

            }
        )

    }
}

