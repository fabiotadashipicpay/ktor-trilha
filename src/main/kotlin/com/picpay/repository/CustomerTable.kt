package com.picpay.repository

import org.jetbrains.exposed.dao.id.IntIdTable

object CustomerTable: IntIdTable() {
    val name = varchar("name", 50)
    val document = varchar("document", 11)
}