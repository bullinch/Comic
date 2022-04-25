package com.example.library_comic.parse;

import com.example.library_base.util.log.Logger;
import com.example.library_comic.SourceEnum;
import com.example.library_comic.bean.Chapter;
import com.example.library_comic.bean.Comic;
import com.example.library_comic.bean.Page;
import com.example.library_comic.common.ComicSource;
import com.example.library_comic.jsoup.Node;
import com.example.library_comic.util.StringUtils;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.request.GetRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class MH90 extends ComicSource {

    private static final String searchUrl = "http://m.90mh.com/";

    @Override
    public int getSourceId() {
        return SourceEnum.MH90.ID;
    }

    @Override
    public String getSourceName() {
        return SourceEnum.MH90.NAME;
    }

    @Override
    public String getSourceHost() {
        return "http://m.90mh.com/";
    }

    @Override
    public GetRequest getChaptersRequest(String url) {
        return EasyHttp.get(url).cacheKey(url);
    }

    @Override
    public GetRequest getPagesRequest(String url) {
        return EasyHttp.get(url).cacheKey(url);
    }

    @Override
    public GetRequest getPageRequest(String url) {
        return EasyHttp.get(url).cacheKey(url);
    }

    @Override
    public GetRequest getSearchRequest(String keyword, int page) {
        String url = StringUtils.format("https://www.manhuadb.com/search?q=%s", keyword);
        return EasyHttp.get(url).cacheKey(url);
    }

    @Override
    public List<Chapter> parseChapters(String html, String comicUrl) {
        List<Chapter> list = new ArrayList<>();
        Element element = new Node(html).get().select("[class=list]").first();
        for (Node node : new Node(element).list("ul > li")) {
            String href = node.attr("a", "href");
            String title = node.getChild("span").text();
            String name = title;
            Chapter chapter = new Chapter(title, getSourceHost() + href, name);
            list.add(chapter);
        }
        return list;
    }

    @Override
    public List<Page> parsePages(String html) {
        Logger.i("----------");
        List<Page> list = new ArrayList<>();
        Elements elements = new Node(html).get().select("#p10 title3 > div > h1 > span");
        for(Element element : elements) {
            Logger.i(element.toString());
        }
        return list;
    }

    @Override
    public List<Comic> parseSearch(String html) {
        List<Comic> list = new ArrayList<>();
        try {
            Elements elements = new Node(html).get().select("[class=comicbook-index mb-2 mb-sm-0]");
            for(Element element:elements) {
                Node node = new Node(element);
                String title = node.attr("a", "title");
                String href = node.attr("a", "href");
                String coverUrl = node.attr("img", "data-original");
                List<Node> authorNodes = new Node(element.selectFirst("div[class=one-line comic-author]")).list("a");
                StringBuilder author = new StringBuilder();
                for(int i=0; i<authorNodes.size();i++) {
                    String split = (i == authorNodes.size() - 1) ? "" : "#";
                    author.append(authorNodes.get(i).attr("a", "title")+split);
                }
                Comic comic = new Comic(title, getSourceHost()+href, coverUrl);
                comic.setAuthor(author.toString());
                list.add(comic);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Comic> parseRecentUpdateList(String html) {
        try {
            List<Comic> list = new ArrayList<>();
            Elements elements = new Node(html).get().select("[class=imgBox]");
            Element element = null;
            for(Element e : elements) {
                if(e.toString().contains("最近更新")) {
                    element = e;
                    break;
                }
            }
            if(element != null) {
                for(Node node : new Node(element).list("[class=list-comic]")) {
                    List<Node> ll = node.list("a");
                    String coverUrl = ll.get(0).attr("mip-img", "src");
                    String title = ll.get(0).attr("mip-img", "alt");
                    String comicUrl = ll.get(0).attr("href");
                    Comic comic = new Comic(title, comicUrl, coverUrl);
                    list.add(comic);
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Comic> parseRankingList(String html) {
        return null;
    }
}
