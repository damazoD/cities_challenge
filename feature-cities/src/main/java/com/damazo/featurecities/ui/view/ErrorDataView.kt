package com.damazo.featurecities.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.damazo.featurecities.R

@Composable
fun ErrorDataView(){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            text = stringResource(R.string.error_tile),
            textAlign = TextAlign.Center,
            fontSize = 24.sp
        )
        Image(
            modifier = Modifier
                .padding(top = 15.dp)
                .size(100.dp),
            imageVector = ImageVector.vectorResource(R.drawable.database_remove),
            colorFilter = ColorFilter.tint(color = Color.Red),
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .padding(top = 15.dp)
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            text = stringResource(R.string.error_description),
            textAlign = TextAlign.Center,
            fontSize = 18.sp
        )
    }
}

@Preview
@Composable
fun ErrorDataViewPreview(){
    ErrorDataView()
}