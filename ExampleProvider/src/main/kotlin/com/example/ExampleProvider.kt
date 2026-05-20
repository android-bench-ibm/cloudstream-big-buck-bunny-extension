package com.example

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*

class ExampleProvider : MainAPI() {
    override var name = "Big Buck Mock"
    override var mainUrl = "http://localhost:8080"
    override var supportedTypes = setOf(TvType.Movie)
    override val hasMainPage = true

    override suspend fun getMainPage(page: Int, requestStep: MainPageRequest): HomePageResponse {
        val item = newMovieSearchResponse("Big Buck Bunny", "http://localhost:8080/bbb.mp4") {
            this.posterUrl = "http://localhost:8080/bbb_poster.jpg"
        }
        return newHomePageResponse("Mock Releases", listOf(item))
    }

    override suspend fun search(query: String): List<SearchResponse> {
        val item = newMovieSearchResponse("Big Buck Bunny", "http://localhost:8080/bbb.mp4") {
            this.posterUrl = "http://localhost:8080/bbb_poster.jpg"
        }
        return listOf(item)
    }

    override suspend fun load(url: String): LoadResponse {
        return newMovieLoadResponse("Big Buck Bunny", url, TvType.Movie, url) {
            this.posterUrl = "http://localhost:8080/bbb_poster.jpg"
            this.backgroundPosterUrl = "http://localhost:8080/bbb_header.jpg"
            this.plot = "A large and lovable rabbit deals with three bullying rodents."
        }
    }

    override suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        // Simplified call to avoid parameter errors, letting the API handle defaults
        callback(
            newExtractorLink(
                source = "Mock",
                name = "Local 1080p",
                url = "http://localhost:8080/bbb.mp4"
            )
        )
        return true
    }
}