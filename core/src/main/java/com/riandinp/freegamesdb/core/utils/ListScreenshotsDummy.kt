package com.riandinp.freegamesdb.core.utils

import com.riandinp.freegamesdb.core.data.source.remote.response.DetailGameResponse
import kotlin.random.Random

object ListScreenshotsDummy {
    fun generateImages(): List<String> {
        val imageList = mutableListOf<String>()
        for (i in 1..Random.nextInt(1, 5)) {
            imageList.add("https://picsum.photos/600/300")
        }
        return imageList
    }

    fun generateImagesAsResponse(): List<DetailGameResponse.ScreenshotsItem> {
        val imageList = mutableListOf<DetailGameResponse.ScreenshotsItem>()
        for (i in 1..Random.nextInt(1, 5)) {
            imageList.add(
                DetailGameResponse.ScreenshotsItem(
                    id = i,
                    image = "https://picsum.photos/600/300"
                )
            )
        }
        return imageList
    }
}