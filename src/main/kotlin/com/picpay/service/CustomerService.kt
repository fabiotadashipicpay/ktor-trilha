package com.picpay.service

import com.picpay.dto.CustomerDTO
import com.picpay.repository.CustomerRepository

class CustomerService(
    private val repository: CustomerRepository
) {
    fun getAll() = repository.getAll()

    fun create(customerDTO: CustomerDTO) : CustomerDTO {
        customerDTO.document.apply {
            this.replace("[^0-9]", "")
        }
        return repository.insert(customerDTO)
    }
}
