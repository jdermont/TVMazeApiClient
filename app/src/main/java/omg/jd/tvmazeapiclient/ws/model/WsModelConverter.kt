package omg.jd.tvmazeapiclient.ws.model

import omg.jd.tvmazeapiclient.entity.Episode
import omg.jd.tvmazeapiclient.entity.Network
import omg.jd.tvmazeapiclient.entity.TvShow

fun WsShow.convertToTvShowEntity(): TvShow {
    return TvShow(id = this.id,
            url = this.url,
            name = this.name,
            type = this.type,
            language = this.language,
            genres = this.genres,
            status = this.status,
            runtime = this.runtime,
            premiered = this.premiered,
            scheduleTime = this.schedule?.time,
            scheduleDays = this.schedule?.days ?: listOf(),
            rating = this.rating?.average ?: 0.0,
            weight = this.weight,
            network = this.network?.convertToNetworkEntity(),
            mediumImage = this.image?.medium,
            originalImage = this.image?.original,
            summary = this.summary,
            updated = this.updated,
            episodes = this.embedded?.convertToEpisodesListEntity() ?: listOf())
}

fun WsNetwork.convertToNetworkEntity(): Network {
    return Network(id = this.id,
            name = this.name,
            countryCode = this.country?.code,
            countryName = this.country?.name,
            countryTimezone = this.country?.timezone)
}

fun WsEpisode.convertToEpisodeEntity(): Episode {
    return Episode(id = this.id,
            url = this.url,
            name = this.name,
            season = this.season,
            number = this.number,
            airdate = this.airdate,
            airtime = this.airtime,
            airstamp = this.airstamp,
            runtime = this.runtime,
            mediumImage = this.image?.medium,
            originalImage = this.image?.original,
            summary = this.summary)
}

fun WsEmbedded.convertToEpisodesListEntity(): List<Episode> {
    return this.episodes.map { it.convertToEpisodeEntity() }
}