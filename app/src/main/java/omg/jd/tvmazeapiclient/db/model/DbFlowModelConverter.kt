package omg.jd.tvmazeapiclient.db.model

import omg.jd.tvmazeapiclient.db.dbflow.utils.StringList
import omg.jd.tvmazeapiclient.entity.Episode
import omg.jd.tvmazeapiclient.entity.Network
import omg.jd.tvmazeapiclient.entity.TvShow

fun TvShow.convertToTvShowDbFlow(): DbFlowTvShow {
    val dbFlowTvShow = DbFlowTvShow(id = this.id,
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
            network = this.network?.convertToNetworkDbFlow(),
            mediumImage = this.mediumImage,
            originalImage = this.originalImage,
            summary = this.summary,
            updated = this.updated)
    dbFlowTvShow.episodes = this.episodes.map { it.convertToEpisodeDbFlow() }
    return dbFlowTvShow
}

fun Network.convertToNetworkDbFlow(): DbFlowNetwork {
    return DbFlowNetwork(id = this.id,
            name = this.name,
            countryCode = this.countryCode,
            countryName = this.countryName,
            countryTimezone = this.countryTimezone)
}

fun Episode.convertToEpisodeDbFlow(): DbFlowEpisode {
    return DbFlowEpisode(id = this.id,
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
