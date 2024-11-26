package com.example.initi_test_project.ui.screens.base

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.CrossFade
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.example.initi_test_project.R
import java.net.URL

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FlagImageLoader(country: String) {
    Log.e("Country", "Country = $country")
    GlideImage(
        modifier =
            Modifier
                .clip(RoundedCornerShape(10.dp))
                .fillMaxSize(),
        model = GlideUrlCustomCacheKey("https://flagsapi.com/$country/shiny/64.png"),
        contentScale = ContentScale.Crop,
        contentDescription = stringResource(id = R.string.flag_icon),
        transition = CrossFade,
    ) {
        it
            .diskCacheStrategy(DiskCacheStrategy.ALL)
    }
}

class GlideUrlCustomCacheKey : GlideUrl {
    constructor(url: String?) : super(url)

//    constructor(url: String?, headers: Headers?) : super(url, headers)
    constructor(url: URL?) : super(url)
//    constructor(url: URL?, headers: Headers?) : super(url, headers)

    override fun getCacheKey(): String {
        val url = toStringUrl()
        return if (url.contains("?")) {
            url.substring(0, url.lastIndexOf("?"))
        } else {
            url
        }
    }
}
