package com.aherbel.movieapp.domain

interface Mapper<I, O> {
    
    fun map(input: I): O
    
}