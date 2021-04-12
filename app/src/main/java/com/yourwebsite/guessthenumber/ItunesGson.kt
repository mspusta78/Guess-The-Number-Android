package com.yourwebsite.guessthenumber

class ItunesResult(
    val results: ArrayList<ItunesItem>
)

class ItunesItem(
    val artistName: String?,
    val trackName: String?,
    val artworkUrl100: String?
)