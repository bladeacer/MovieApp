package com.example.assignment2.utils

fun String.toTitleCase(): String {
    if (isEmpty()) return this // Handle empty string
    val words = this.split(Regex("_|\\s+")) // Split by underscore or whitespace
    val titleCased = words.joinToString(" ") // Join with spaces

    return if (titleCased.isEmpty()) {
        titleCased // Handle empty result
    } else {
        titleCased[0].uppercaseChar() + titleCased.substring(1).lowercase() // Lowercase first letter of the entire string
    }
}

