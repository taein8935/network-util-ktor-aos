package com.tikim.networkutilktor

open class Animal

class Cat: Animal()
class Dog: Animal()

class House<T: Animal>()


/**
 * 공변성, 반공변성, 무공변성 예시
 */
fun doSomething() {

    val cat: Animal = Cat()
    val dog: Animal = Dog()

    val catHouse: House<Animal> = House<Cat>()
    val dogHouse: House<Animal> = House<Dog>()
    val amimalHouse: House<Animal> = House<Nothing>()
    val anyHouse: House<Any> = House<Cat>()

    val nothingHouse: House<Nothing> = House<Cat>()
}



