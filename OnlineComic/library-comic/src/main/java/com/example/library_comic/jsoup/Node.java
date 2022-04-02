package com.example.library_comic.jsoup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.LinkedList;
import java.util.List;

public class Node {
    private Element element;

    public Node(String html) {
        this.element = Jsoup.parse(html).body();
    }

    public Node(Element element) {
        this.element = element;
    }

    public Node id(String id) {
        return new Node(element.getElementById(id));
    }

    public Element get() {
        return element;
    }

    public Node getChild(String cssQuery) {
        return new Node(get().select(cssQuery).first());
    }

    public List<Node> list(String cssQuery) {
        List<Node> list = new LinkedList<>();
        Elements elements = element.select(cssQuery);
        for (Element e : elements) {
            list.add(new Node(e));
        }
        return list;
    }

    public String text() {
        try {
            return element.text().trim();
        } catch (Exception e) {
            return null;
        }
    }

    public String text(String cssQuery) {
        try {
            return element.select(cssQuery).first().text().trim();
        } catch (Exception e) {
            return null;
        }
    }

    public String attr(String attr) {
        try {
            return element.attr(attr).trim();
        } catch (Exception e) {
            return null;
        }
    }

    public String attr(String cssQuery, String attr) {
        try {
            return element.select(cssQuery).first().attr(attr).trim();
        } catch (Exception e) {
            return null;
        }
    }
}
