package dev.cnemo.personalaccounting.data

import java.sql.Timestamp

class Timestamp(long: Long) : Timestamp(long) {
    constructor(): this(0L)
}