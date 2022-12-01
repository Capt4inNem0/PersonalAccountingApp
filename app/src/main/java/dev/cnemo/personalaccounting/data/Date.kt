package dev.cnemo.personalaccounting.data


class Date(long: Long) : java.util.Date(long) {
    constructor(): this(0L)
    companion object {
        fun fromDate(date: java.util.Date) = Date(date.time)
    }
}