package com.alvaro.rxjavaplayground.datasource

import com.alvaro.rxjavaplayground.model.Task

class DummyDataSource {

    companion object{

        fun createList(): List<Task> = mutableListOf<Task>().apply {
            this.add(Task(description = "Take out the trash", isComplete = true, priority = 3))
            this.add(Task(description = "Make my bed", isComplete = false, priority = 2))
            this.add(Task(description = "Unload the dishwasher", isComplete = true, priority = 1))
            this.add(Task(description = "Walk the dog", isComplete = false, priority = 0))
            this.add(Task(description = "Make dinner", isComplete = true, priority = 5))
        }

    }

}