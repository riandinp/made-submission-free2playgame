package com.riandinp.freegamesdb.core.data.source.remote.response


data class DetailGameResponse(	
	val shortDescription: String,
	val thumbnail: String,
	val freetogameProfileUrl: String,
	val description: String,
	val title: String,
	val platform: String,
	val screenshots: List<ScreenshotsItem> = emptyList(),
	val gameUrl: String,
	val releaseDate: String,
	val genre: String,
	val publisher: String,
	val developer: String,
	val id: Int,
) {
	data class ScreenshotsItem(
		val image: String,
		val id: Int
	)
}


