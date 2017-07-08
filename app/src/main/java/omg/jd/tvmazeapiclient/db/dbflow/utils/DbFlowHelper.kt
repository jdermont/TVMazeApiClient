package omg.jd.tvmazeapiclient.db.dbflow.utils

import com.raizlabs.android.dbflow.config.FlowManager
import com.raizlabs.android.dbflow.structure.BaseModel
import omg.jd.tvmazeapiclient.db.dbflow.TvMazeDatabase

fun BaseModel.saveInTransation() {
    FlowManager.getDatabase(TvMazeDatabase::class.java).executeTransaction {
        save()
    }
}

fun List<BaseModel>.saveInTransation() {
    FlowManager.getDatabase(TvMazeDatabase::class.java).executeTransaction {
        forEach { it.save() }
    }
}

fun BaseModel.deleteInTransaction() {
    FlowManager.getDatabase(TvMazeDatabase::class.java).executeTransaction {
        delete()
    }
}

fun List<BaseModel>.deleteInTransation() {
    FlowManager.getDatabase(TvMazeDatabase::class.java).executeTransaction {
        forEach { it.delete() }
    }
}