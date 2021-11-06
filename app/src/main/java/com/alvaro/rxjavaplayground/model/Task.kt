package com.alvaro.rxjavaplayground.model

data class Task(
var description: String = "",
var isComplete: Boolean = false,
var priority: Int? = null
)
