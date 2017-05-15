package omg.jd.tvmazeapiclient.utils

import omg.jd.tvmazeapiclient.db.model.Links
import omg.jd.tvmazeapiclient.db.model.Network
import omg.jd.tvmazeapiclient.db.model.TvShow
import omg.jd.tvmazeapiclient.ws.model.WsLinks
import omg.jd.tvmazeapiclient.ws.model.WsNetwork
import omg.jd.tvmazeapiclient.ws.model.WsShow

fun WsShow.convertToTvShow(): TvShow {
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
            scheduleDays = this.schedule?.days ?: ArrayList(),
            rating = this.rating?.average ?: 0.0,
            weight = this.weight,
            network = this.network?.convertToNetwork(),
            mediumImage = this.image?.medium,
            originalImage = this.image?.original,
            summary = this.summary,
            updated = this.updated,
            links = this.links?.convertToLinks())
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