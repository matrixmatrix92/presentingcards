package com.matrix.test.presenting_cards.member

class User(val full_name: String, val img: String, val service: List<Service>) {
    fun getImageLink():String{
        return APIs.IMAGE_URL+img
    }
}