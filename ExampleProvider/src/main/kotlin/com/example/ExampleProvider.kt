package com.example

import android.annotation.SuppressLint
import android.media.Rating
import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.LoadResponse.Companion.addActors
import com.lagradost.cloudstream3.utils.*
import com.lagradost.cloudstream3.Score

class ExampleProvider : MainAPI() {
    // This is the name that will actually show up in the CloudStream app
    override var name = "Big Buck Bunny"
    override var mainUrl = "https://peach.blender.org"
    override var supportedTypes = setOf(TvType.Movie)
    override val hasMainPage = true

    // Real public URLs for testing
    private val bbbPoster = "https://upload.wikimedia.org/wikipedia/commons/c/c5/Big_buck_bunny_poster_big.jpg"
    private val bbbVideoUrl = "https://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_320x180.mp4"

    override suspend fun getMainPage(page: Int, requestStep: MainPageRequest): HomePageResponse {
        val item = newMovieSearchResponse("Big Buck Bunny", bbbVideoUrl) {
            // Serve the splash image directly to the SearchResponse payload
            // so the Compose UI receives it immediately
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

    @SuppressLint("RestrictedApi")
    override suspend fun load(url: String): LoadResponse {
        return newMovieLoadResponse("Big Buck Bunny", url, TvType.Movie, url) {
            this.posterUrl = bbbPoster
            this.plot = "A large and lovable giant rabbit confronts three mischievous rodents who torment him and ruin his day, leading to a series of comical, trap-filled retaliations."

            this.year = 2008
            this.duration = 10
            this.tags = listOf("Animation", "Comedy", "Short", "Family")

            this.score = Score.from(6.4, 10)

            this.backgroundPosterUrl = "https://peach.blender.org/wp-content/uploads/bbb-splash.png"

            addActors(listOf("Bunny", "Frank the Squirrel", "Rinky the Flying Squirrel"))
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