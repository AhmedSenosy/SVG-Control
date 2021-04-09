package com.senosy.svgtask

import android.util.Log
import com.senosy.svgtask.models.Line
import kotlinx.coroutines.*
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream

private const val TAG = "LinesRepo"

class LinesRepo(private val inputStream: InputStream) {

    suspend fun parseData():List<Line> {

        val parserFactory = XmlPullParserFactory.newInstance()
        val pullParser = parserFactory.newPullParser()

        pullParser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
        pullParser.setInput(inputStream, null)
        return try {
            processParsing(pullParser);
        } catch (e: Exception) {
            Log.e(TAG, "parseData: ", e)
            emptyList()
        }

    }

    @Throws(IOException::class, XmlPullParserException::class)
    private suspend fun processParsing(parser: XmlPullParser): List<Line> =
        withContext<ArrayList<Line>>(Dispatchers.IO)
        {

                var lines = ArrayList<Line>()
                var eventType = parser.eventType
                var line: Line? = null
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    var eltName: String? = null
                    when (eventType) {
                        XmlPullParser.START_TAG -> {
                            eltName = parser.name
                            if ("g" == eltName && parser.getAttributeValue(0) == "DOORS") {
                                parser.nextTag()
                                var tagName = parser.name
                                while (tagName == "line") {
                                    if (parser.eventType == XmlPullParser.END_TAG) {
                                        parser.nextTag()
                                        tagName = parser.name
                                        continue
                                    }
                                    line = Line(
                                        id = parser.getAttributeValue(null, "id"),
                                        lineClass = parser.getAttributeValue(null, "class"),
                                        x1 = parser.getAttributeValue(null, "x1"),
                                        x2 = parser.getAttributeValue(null, "x2"),
                                        y1 = parser.getAttributeValue(null, "y1"),
                                        y2 = parser.getAttributeValue(null, "y2"),
                                    )
                                    lines.add(line)

                                    parser.nextToken()
                                    tagName = parser.name
                                }
                                break
                            }
                        }
                    }
                    eventType = parser.next()
                }
            return@withContext lines


    }
}