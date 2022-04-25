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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class CartoonMad extends ComicSource {

    private static final String searchUrl = "https://www.cartoonmad.com";

    @Override
    public int getSourceId() {
        return SourceEnum.CARTOONMAD.ID;
    }

    @Override
    public String getSourceName() {
        return SourceEnum.CARTOONMAD.NAME;
    }

    @Override
    public String getSourceHost() {
        return "https://www.cartoonmad.com";
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
        List<Node> nodes = new Node(html).list("ul[class=list_block show] > li > a");
        for (Node node : nodes) {
            String href = node.attr("href");
            String name = node.text();
            if (!href.startsWith("http")) {
                Chapter chapter = new Chapter(name, getSourceHost() + href, name);
                list.add(chapter);
            }
        }
        return list;
    }

    @Override
    public List<Page> parsePages(String html) {
        List<Page> list = new ArrayList<>();
        String matchString = StringUtils.match("var z_img='\\[(.*)\\]'", html, 1);
        String[] urlArray = matchString.split(",");
        for (int i = 1; i <= urlArray.length; i++) {
            String imgUrl = urlArray[i - 1].replace("\"", "");
            Page page = new Page(i + "", imgUrl, i);
            list.add(page);
        }
        return list;
    }

    @Override
    public List<Comic> parseSearch(String html) {
        List<Comic> list = new ArrayList<>();
        try {
            Elements elements = new Node(html).get().select("[class=comicbook-index mb-2 mb-sm-0]");
            for (Element element : elements) {
                Node node = new Node(element);
                String title = node.attr("a", "title");
                String href = node.attr("a", "href");
                String coverUrl = node.attr("img", "data-original");
                List<Node> authorNodes = new Node(element.selectFirst("div[class=one-line comic-author]")).list("a");
                StringBuilder author = new StringBuilder();
                for (int i = 0; i < authorNodes.size(); i++) {
                    String split = (i == authorNodes.size() - 1) ? "" : "#";
                    author.append(authorNodes.get(i).attr("a", "title") + split);
                }
                Comic comic = new Comic(title, getSourceHost() + href, coverUrl);
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
            Logger.i(html + "----------");
            List<Comic> list = new ArrayList<>();
            for(Element element : Jsoup.parse(html).getAllElements()) {
                Logger.i(element.toString() + "=========");
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
