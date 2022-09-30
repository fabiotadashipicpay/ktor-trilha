package com.picpay.repository

import com.picpay.dto.CustomerDTO
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

class CustomerRepository {

    private val db by lazy {
        Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", driver = "org.h2.Driver")
    }

    init {
        transaction(db) {
            // print sql to std-out
            addLogger(StdOutSqlLogger)

            SchemaUtils.create(
                CustomerTable
            )

        }
    }

    fun getAll() = transaction(db) {
        CustomerEntity.all().toList()
            .map {
                CustomerDTO(
                    id = it.id.value,
                    name = it.name,
                    document = it.document
                )
            }
    }

    fun insert(customerDTO: CustomerDTO) = transaction(db) {
        val customerEntity = CustomerEntity.new {
            name = customerDTO.name
            document = customerDTO.document
        }
        CustomerDTO(
            id = customerEntity.id.value,
            name = customerEntity.name,
            document = customerEntity.document
        )
    }
}
