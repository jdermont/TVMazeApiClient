package omg.jd.tvmazeapiclient.utils

import omg.jd.tvmazeapiclient.ws.model.*

fun createShowList(count: Int = 1): List<WsTVShow> {
    val tvShows = (0..count-1).map {
        WsTVShow(0.0, createShow(id = it.toLong(), name = "Show $it"))
    }
    return tvShows
}

fun createShow(id: Long = 0, name: String = "Show"): WsShow {
    return WsShow(id = id,
            url = "http://www.tvmaze.com/shows/66/the-big-bang-theory",
            name = name,
            type = "Scripted",
            language = "English",
            genres = listOf("Comedy"),
            status = "Running",
            runtime = 30,
            premiered = "2007-09-24",
            schedule = WsSchedule("20:00", listOf("Thursday")),
            rating = WsRating(8.3),
            weight = 100,
            network = WsNetwork(
                    id = 2,
                    name = "CBS",
                    country = WsCountry("United States", "US", "America/New_York")
            ),
            externals = WsExternals(8511, 80379, "tt0898266"),
            image = WsImage(
                    medium = "http://static.tvmaze.com/uploads/images/medium_portrait/58/145601.jpg",
                    original = "http://static.tvmaze.com/uploads/images/original_untouched/58/145601.jpg"
            ),
            summary = "Something about The Big Bang Theory",
            updated = 1494671152L,
            links = LinksDbFlow(
                    self = "http://api.tvmaze.com/shows/66",
                    previousepisode = "http://api.tvmaze.com/episodes/1107587",
                    nextepisode = null
            ),
            embedded = null
    )
}

fun createShowListWithEpisodes(count: Int = 1): List<WsTVShow> {
    val tvShows = (0..count-1).map {
        WsTVShow(0.0,
                createShowWithEpisodes(
                        id = it.toLong(),
                        name = "Show $it",
                        episodeCount = 2,
                        episodeStartId = 100*it
                )
        )

    }
    return tvShows
}

fun createShowWithEpisodes(id: Long = 0,
                           name: String = "Show",
                           episodeCount: Int = 2,
                           episodeStartId: Int = 0): WsShow {
    return createShow(id,name).copy(
            embedded = WsEmbedded(
                    episodes = createEpisodeList(episodeCount, episodeStartId)
            )
        )
}

fun createEpisode(id: Long = 0, name: String = "Episode"): WsEpisode {
    return WsEpisode(
            id = id,
            url = "http://www.tvmaze.com/episodes/2913/the-big-bang-theory-1x01-pilot",
            name = name,
            season = 1,
            number = 1,
            airdate = "2007-09-24",
            airtime = "20:30",
            airstamp = "2007-09-24T20:30:00-04:00",
            runtime = 30,
            image = WsImage(
                    medium = "http://static.tvmaze.com/uploads/images/medium_landscape/4/12368.jpg",
                    original = "http://static.tvmaze.com/uploads/images/original_untouched/4/12368.jpg"
            ),
            summary = "Is a comedy about brilliant physicists, Leonard and Sheldon.",
            links = LinksDbFlow(
                    self = "http://api.tvmaze.com/episodes/2913",
                    previousepisode = null,
                    nextepisode = null
            )

    )
}

fun createEpisodeList(count: Int = 1, startId: Int = 0): List<WsEpisode> {
    val episodes = (0..count-1).map {
        createEpisode(id = (it+startId).toLong(), name = "Episode $it")
    }
    return episodes
}
