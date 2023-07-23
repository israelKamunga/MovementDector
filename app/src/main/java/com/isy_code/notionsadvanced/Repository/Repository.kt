package com.isy_code.notionsadvanced.Repository

import com.isy_code.notionsadvanced.Data.DataClass
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class Repository {
    var DataSource = DataClass()

    fun getList(): Flow<List<String>> = flow {
        emit(DataSource.voitures)
    }
}