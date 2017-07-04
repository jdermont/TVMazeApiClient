package omg.jd.tvmazeapiclient.utils

import omg.jd.tvmazeapiclient.db.dbflow.utils.StringList
import omg.jd.tvmazeapiclient.db.model.Episode
import omg.jd.tvmazeapiclient.db.model.Links
import omg.jd.tvmazeapiclient.db.model.Network
import omg.jd.tvmazeapiclient.db.model.TvShow
import omg.jd.tvmazeapiclient.ws.model.*

fun WsShow.convertToTvShow(): TvShow {
    return TvShow(id = this.id,
            url = this.url,
            name = this.name,
            type = this.type,
            language = this.language,
            genres = StringList(this.genres),
            status = this.status,
            runtime = this.runtime,
            premiered = this.premiered,
            scheduleTime = this.schedule?.time,
            scheduleDays = StringList(this.schedule?.days ?: listOf()),
            rating = this.rating?.average ?: 0.0,
            weight = this.weight,
            network = this.network?.convertToNetwork(),
            mediumImage = this.image?.medium,
            originalImage = this.image?.original,
            summary = this.summary,
            updated = this.updated,
            links = this.links?.convertToLinks(),
            episodes = this.embedded?.convertToEpisodes() ?: listOf())
}

fun WsNetwork.convertToNetwork(): Network {
    return Network(id = this.id,
            name = this.name,
            countryCode = this.country?.code,
            countryName = this.country?.name,
            countryTimezone = this.country?.timezone)
}

fun WsLinks.convertToLinks(): Links {
    return Links(self = this.self,
            previousepisode = this.previousepisode,
            nextepisode = this.nextepisode)
}

fun WsEpisode.convertToEpisode(): Episode {
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
            summary = this.summary,
            links = this.links?.convertToLinks())
}

fun WsEmbedded.convertToEpisodes(): List<Episode> {
    return this.episodes.map { it.convertToEpisode() }
}