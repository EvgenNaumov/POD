package com.naumov.pictureoftheday.recycler.diffutil

data class Change<out T>(
    val oldData: T,
    val newData: T)

    fun <T> createCombinedPayload(payloads: MutableList<Change<T>>): Change<T> {
        assert(payloads.isNotEmpty())
        val firstChange = payloads.first()
        val lastChange = payloads.last()

        return Change(firstChange.oldData, lastChange.newData)
    }

