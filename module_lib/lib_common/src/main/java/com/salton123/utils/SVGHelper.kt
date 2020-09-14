package com.salton123.utils

import android.graphics.Path
import android.util.Log
import android.util.SparseArray
import androidx.core.graphics.PathParser
import com.salton123.log.XLog
import org.w3c.dom.Element
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory

/**
 * User: newSalton@outlook.com
 * Date: 2019-08-30 10:34
 * ModifyTime: 10:34
 * Description:
 */
object SVGHelper {
    const val TAG = "SVGHelper"
    fun parse(inputStream: InputStream, tags: Map<String, ArrayList<Element>>) {
        try {
            val documentElement = DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(inputStream)
                .documentElement
            val split = documentElement.getAttribute("viewBox").split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val width = java.lang.Float.parseFloat(split[2])
            val height = java.lang.Float.parseFloat(split[3])
            Log.e("ParserUtil", "width :$width, height :$height")
            val elementsByTagName = documentElement.getElementsByTagName("g")
            for (index in 0 until elementsByTagName.length) {
                val element = elementsByTagName.item(index) as Element
                val attribute = element.getAttribute("id")
                if (tags.containsKey(attribute)) {
                    tags[attribute]?.add(element)
                }
            }

        } catch (ignore: Throwable) {
            ignore.printStackTrace()
            XLog.i(TAG, "error:${ignore.message}")
        } finally {
            inputStream.close()
        }
    }

    fun parseRegions(colors: SparseArray<ArrayList<Path>>, element: Element) {
        val elementsByTagName2 = element.getElementsByTagName("path")
        for (j in 0 until elementsByTagName2.length) {
            val element2 = elementsByTagName2.item(j) as Element
            val attribute2 = element2.getAttribute("d")
            val attribute3 = element2.getAttribute("regions")
            val regionArr = attribute3.split(",")

            try {
                regionArr.forEach {
                    try {
                        val region = Integer.parseInt(it.trim())
                        val resgions: ArrayList<Path>?
                        if (colors.indexOfKey(region) < 0) {
                            resgions = ArrayList()
                            colors.put(region, resgions)
                        } else {
                            resgions = colors.get(region)
                        }
                        resgions?.add(PathParser.createPathFromPathData(attribute2))
                    } catch (ignore: Throwable) {
                        ignore.printStackTrace()
                        XLog.i(TAG, "error:${ignore.message}")
                    }
                }
            } catch (ignore: Throwable) {
                ignore.printStackTrace()
                XLog.i(TAG, "error:${ignore.message}")
            }
        }
    }

    fun parseLines(lines: ArrayList<Path>, element: Element) {
        val linePathNode = element.getElementsByTagName("path")
        for (nodeIndex in 0 until linePathNode.length) {
            val linePathElement = linePathNode.item(nodeIndex) as Element
            val linePath = PathParser.createPathFromPathData(linePathElement.getAttribute("d"))
            lines.add(linePath)
        }
    }
}