package com.example.networth

/*
This is a multipurpose dataclass for the various types of data our app stores
Item categories:
- "data"
- liability
- saving
- income
- expense
- goal
 */

data class Item(
    //val id: Int = 0,
    var name:String,
    var description:String,
    var category:String,
    var secondaryCategory:String = "",
    var amount:Float,
    var status: Boolean

)
