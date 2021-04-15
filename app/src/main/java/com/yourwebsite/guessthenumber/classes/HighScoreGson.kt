package com.yourwebsite.guessthenumber

class HighScoreResult(
    val results: ArrayList<HighScoreItem>
)

class HighScoreItem(
    val displayname: String?,
    val score: Int?
)