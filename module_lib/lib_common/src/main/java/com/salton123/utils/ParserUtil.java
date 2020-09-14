package com.salton123.utils;

import android.graphics.Path;
import android.util.Log;
import android.util.SparseArray;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilderFactory;

import androidx.core.graphics.PathParser;

/**
 * User: newSalton@outlook.com
 * Date: 2019/8/26 0:37
 * ModifyTime: 0:37
 * Description:
 */
public class ParserUtil {

    public static Path parse(String path, SparseArray<Path> regionPaths) {
        Path linePath = null;
        int reginIdex = 0;
        try {
            InputStream inputStream = new FileInputStream(path);
            Element documentElement = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(inputStream)
                    .getDocumentElement();
            String[] split = documentElement.getAttribute("viewBox").split(" ");
            float width = Float.parseFloat(split[2]);
            float height = Float.parseFloat(split[3]);
            Log.e("ParserUtil", "width :" + width + ", height :" + height);
            NodeList elementsByTagName = documentElement.getElementsByTagName("g");
            for (int i = 0; i < elementsByTagName.getLength(); i++) {
                Element element = (Element) elementsByTagName.item(i);
                String attribute = element.getAttribute("id");
                if (attribute.equals("color")) {        //填色
                    NodeList elementsByTagName2 = element.getElementsByTagName("path");
                    for (int j = 0; j < elementsByTagName2.getLength(); j++) {
                        Element element2 = (Element) elementsByTagName2.item(j);
                        String attribute2 = element2.getAttribute("d");
                        String attribute3 = element2.getAttribute("regions");
                        if (regionPaths != null) {
                            regionPaths.put(reginIdex, PathParser.createPathFromPathData(attribute2));
                            reginIdex++;
                        }
                    }
                } else {
                    if (attribute.equals("line")) { //线稿
                        NodeList linePathNode = element.getElementsByTagName("path");
                        Element linePathElement = (Element) linePathNode.item(0);
                        linePath = PathParser.createPathFromPathData(linePathElement.getAttribute("d"));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return linePath;
    }
}
