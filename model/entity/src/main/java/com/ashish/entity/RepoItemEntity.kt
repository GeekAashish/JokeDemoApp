package com.ashish.entity

data class RepoItemEntity(
    val joke: String,
    var date: String,
) {
    constructor() : this(
        joke = "Jokes",
        date = "date",
    )
}