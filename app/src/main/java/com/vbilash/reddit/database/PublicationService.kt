package com.vbilash.reddit.database

import android.os.StrictMode
import android.util.Log
import com.vbilash.reddit.model.Publication
import java.net.URL
import kotlin.text.StringBuilder

class PublicationService {

    private var publicationsList = mutableListOf<Publication>()
    private val fakeBase = false

    init {
        if (fakeBase) {
            fillFakePublicationList()
        } else {
            loadJsonData()
        }
    }

    private fun loadJsonData() {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        val url = "https://www.reddit.com/top.json"
        val json: String = URL(url).readText()

        parseJsonFile(json)
    }

    private fun parseJsonFile(json: String) {
        val initialComponent = "\"t3\", \"data\""
        var startingIndex = json.indexOf(initialComponent, 0)

        while (startingIndex >= 0) {
            val id = findUnit(startingIndex, json, "\"name\"")
            val title = cleanText(findUnit(startingIndex, json, "\"title\""))
            val userName = findUnit(startingIndex, json, "\"subreddit_name_prefixed\"")
            val thumbnail = findUnit(startingIndex, json, "\"thumbnail\"")
            val icon = findUnit(startingIndex, json, "\"icon_url\"")
            val numComments = findUnit(startingIndex, json, "\"num_comments\"")

            publicationsList.add(
                Publication(
                    id,
                    icon,
                    userName,
                    title,
                    thumbnail,
                    numComments
                )
            )

            startingIndex = json.indexOf(initialComponent, startingIndex + initialComponent.length)
        }
    }

    private fun cleanText(findUnit: String): String {
        var result = findUnit
        val unicodeValues = "\\u"
        val lengthValue = 6

        while (result.indexOf(unicodeValues) != -1) {
            result = result.removeRange(
                result.indexOf(unicodeValues),
                result.indexOf(unicodeValues) + lengthValue
            )
        }
        return result
    }

    private fun findUnit(startPoint: Int, json: String, unit: String): String {
        val result = StringBuilder()
        val startingIndex = json.indexOf(unit, startPoint) + unit.length
        var quotesOpen = false

        for (index in startingIndex..json.length) {
            if (json[index] == '"') {
                quotesOpen = !quotesOpen
                continue
            }
            if (quotesOpen || !quotesOpen && json[index].isDigit()) {
                result.append(json[index])
            }
            if (json[index] == ',' && !quotesOpen) {
                return result.toString()
            }
        }
        return result.toString()
    }


    private fun fillFakePublicationList() {
        for (n in 0..20) {
            publicationsList.add(
                Publication(
                    n.toString(),
                    "https://i.pravatar.cc/50?img=$n",
                    "Fake name",
                    n.toString(),
                    "some text",
                    "https://i.pravatar.cc/500?img=$n"
                )
            )
        }
    }

    fun getPublicationsList(): MutableList<Publication> {
        return publicationsList
    }
}