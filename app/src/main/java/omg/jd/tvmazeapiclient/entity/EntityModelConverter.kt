package omg.jd.tvmazeapiclient.entity

import omg.jd.tvmazeapiclient.db.dbflow.utils.StringList
import omg.jd.tvmazeapiclient.db.model.DbFlowEpisode
import omg.jd.tvmazeapiclient.db.model.DbFlowNetwork
import omg.jd.tvmazeapiclient.db.model.DbFlowTvShow

fun DbFlowTvShow.convertToTvShowEntity(): TvShow {
    return TvShow(id = this.id,
            url = this.url,
            name = this.name,
            type = this.type,
            language = this.language,
            genres = StringList(this.genres),
            status = this.status,
            runtime = this.runtime,
            premiered = this.premiered,
            scheduleTime = this.scheduleTime,
            scheduleDays = StringList(this.scheduleDays),
            rating = this.rating,
            weight = this.weight,
            network = this.network?.convertToNetworkEntity(),
            mediumImage = this.mediumImage,
            originalImage = this.originalImage,
            summary = this.summary,
            updated = this.updated,
            episodes = this.episodes?.map { it.convertToEpisodeEntity() } ?: listOf() )
}

fun DbFlowNetwork.convertToNetworkEntity(): Network {
    return Network(id = this.id,
            name = this.name,
            countryCode = this.countryCode,
            countryName = this.countryName,
            countryTimezone = this.countryTimezone)
}

fun DbFlowEpisode.convertToEpisodeEntity(): Episode {
    return Episode(id = this.id,
            url = this.url,
            name = this.name,
            season = this.season,
            number = this.number,
            airdate = this.airdate,
            airtime = this.airtime,
            airstamp = this.airstamp,
            runtime = this.runtime,
            mediumImage = this.mediumImage,
            originalImage = this.originalImage,
            summary = this.summary)
}