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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
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
fun DataFoundView(
    onButtonPressed:()->Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(100.dp),
            imageVector = ImageVector.vectorResource(R.drawable.database_search),
            colorFilter = ColorFilter.tint(color = Color.Blue),
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .padding(top = 15.dp)
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            text = stringResource(R.string.data_found_description),
            textAlign = TextAlign.Center,
            fontSize = 20.sp
        )
        Button(
            modifier = Modifier
                .padding(top = 50.dp),
            onClick = onButtonPressed
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .wrapContentWidth()
                    .wrapContentHeight(),
                text = stringResource(R.string.data_download_button),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun DataFoundViewPreview() {
    DataFoundView{

    }
}