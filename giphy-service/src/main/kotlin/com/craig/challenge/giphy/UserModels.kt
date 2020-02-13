package com.craig.challenge.giphy

/**
 * Various classes used, this was a dumping ground for rapid development
 */

data class SaveDTO(val giphyId: String)

data class GiphyCard(val giphyId: String,
                     val categories: Set<String> = HashSet())

data class UserProfile(val id: String, val username: String, val giphys: Collection<GiphyCard> = ArrayList())