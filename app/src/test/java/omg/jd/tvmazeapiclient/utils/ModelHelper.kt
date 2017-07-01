package omg.jd.tvmazeapiclient.utils

import omg.jd.tvmazeapiclient.ws.model.*

fun createShowList(count: Int = 1): List<WsTVShow> {
    val tvShows = (0..count).map {
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
            links = WsLinks(
                    self = "http://api.tvmaze.com/shows/66",
                    previousepisode = "http://api.tvmaze.com/episodes/1107587",
                    nextepisode = null
            ),
            embedded = null
    )
}

fun createShowWithEpisodes(id: Long = 0, name: String = "Show"): WsShow {
    return createShow(id,name).copy(
            embedded = WsEmbedded(
                    episodes = listOf(
                            createEpisode(0, "Pilot 1"),
                            createEpisode(1, "Pilot 2")
                    )
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
            links = WsLinks(
                    self = "http://api.tvmaze.com/episodes/2913",
                    previousepisode = null,
                    nextepisode = null
            )

    )
}
