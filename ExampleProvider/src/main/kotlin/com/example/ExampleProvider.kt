package com.example

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*

class ExampleProvider : MainAPI() {
    // This is the name that will actually show up in the CloudStream app
    override var name = "Big Buck Bunny"
    override var mainUrl = "https://peach.blender.org"
    override var supportedTypes = setOf(TvType.Movie)
    override val hasMainPage = true

    // Real public URLs for testing
    private val bbbPoster = "https://upload.wikimedia.org/wikipedia/commons/c/c5/Big_buck_bunny_poster_big.jpg"
    private val bbbVideoUrl = "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

    override suspend fun getMainPage(page: Int, requestStep: MainPageRequest): HomePageResponse {
        val item = newMovieSearchResponse("Big Buck Bunny", bbbVideoUrl) {
            this.posterUrl = bbbPoster
        }
        return newHomePageResponse("Mock Releases", listOf(item))
    }

    override suspend fun search(query: String): List<SearchResponse> {
        val item = newMovieSearchResponse("Big Buck Bunny", bbbVideoUrl) {
            this.posterUrl = bbbPoster
        }
        return listOf(item)
    }

    override suspend fun load(url: String): LoadResponse {
        return newMovieLoadResponse("Big Buck Bunny", url, TvType.Movie, url) {
            this.posterUrl = bbbPoster
        }
    }

    override suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        // Stripped down to core parameters to match the new factory function signature
        callback(
            newExtractorLink(
                source = "Blender",
                name = "1080p MP4",
                url = data
            )
        )
        return true
    }
}