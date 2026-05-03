package com.example.lemonwallet.ui.features.mainscreen.components.blocks.components.lastoperations

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lemonwallet.R
import com.example.lemonwallet.model.domain.Category
import com.example.lemonwallet.model.domain.CategoryStructure
import com.example.lemonwallet.model.domain.CreditTransaction
import com.example.lemonwallet.model.domain.Currency
import com.example.lemonwallet.model.domain.EnumColor
import com.example.lemonwallet.model.domain.GeneralTransaction
import com.example.lemonwallet.model.domain.NeedCategory
import com.example.lemonwallet.model.domain.Operation
import com.example.lemonwallet.model.domain.Owner
import com.example.lemonwallet.model.domain.StatusOperation
import com.example.lemonwallet.model.domain.Storage
import com.example.lemonwallet.model.domain.SystemColor
import com.example.lemonwallet.model.domain.TransferTransaction
import com.example.lemonwallet.model.domain.TypeStorage
import com.example.lemonwallet.ui.theme.MainDark
import com.example.lemonwallet.ui.theme.MainLight
import com.example.lemonwallet.ui.theme.SecondDark
import com.example.lemonwallet.ui.theme.SecondLight
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun LastOperationListItemPreview() {
}


@Composable
fun LastOperationListItem(operation: Operation) {


}

@Composable
fun OperationItem(operation: Operation){

}

@Composable
fun TransferOperation(transfer: TransferTransaction){

}
@Composable
fun GeneralTransaction(generalTransaction: GeneralTransaction){

}
