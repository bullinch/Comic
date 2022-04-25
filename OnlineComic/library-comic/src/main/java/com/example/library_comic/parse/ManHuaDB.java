package com.example.library_comic.parse;

import com.example.library_comic.SourceEnum;
import com.example.library_comic.bean.Chapter;
import com.example.library_comic.bean.Comic;
import com.example.library_comic.bean.Page;
import com.example.library_comic.callback.Callback;
import com.example.library_comic.common.ComicSource;
import com.example.library_comic.jsoup.Node;
import com.example.library_comic.util.DecryptionUtils;
import com.example.library_comic.util.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.request.GetRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ManHuaDB extends ComicSource {

    private static final String searchUrl = "https://www.manhuadb.com/";

    @Override
    public int getSourceId() {
        return SourceEnum.MAN_HUA_DB.ID;
    }

    @Override
    public String getSourceName() {
        return SourceEnum.MAN_HUA_DB.NAME;
    }

    @Override
    public String getSourceHost() {
        return "https://www.manhuadb.com";
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
        return null;
    }

    @Override
    public GetRequest getSearchRequest(String keyword, int page) {
        String url = StringUtils.format("https://www.manhuadb.com/search?q=%s", keyword);
        return EasyHttp.get(url).cacheKey(url);
    }

    @Override
    public List<Chapter> parseChapters(String html, String comicUrl) {
        List<Chapter> list = new ArrayList<>();
        Node nodeHtml = new Node(html);
        for (Node node : nodeHtml.list("#comic-book-list > div > ol > li > a")) {
            String title = node.attr("title");
            String href = node.attr("href");
            String name = node.text();
            Chapter chapter = new Chapter(title, getSourceHost() + href, name);
            list.add(chapter);
        }
        return list;
    }

    @Override
    public List<Page> parsePages(String html) {
        List<Page> list = new ArrayList<>();
        try {
            Element element = new Node(html).get().selectFirst("[class=d-none vg-r-data]");
            Node node = new Node(element);
            String dataHost = node.attr("data-host");
            String dataImgPre = node.attr("data-img_pre");
            String base64Data = StringUtils.match("var img_data = '(.*?)';", html, 1);
            String jsonStr = DecryptionUtils.base64Decrypt(base64Data);
            List<Img> imageList = new Gson().fromJson(jsonStr,
                    new TypeToken<List<Img>>() {}.getType());
            for (Img img: imageList) {
                // https://i2.manhuadb.com/static/31256/312554/010_qdhazwsy.jpg
                String imgUrl = dataHost+dataImgPre+img.img;
                Page page = new Page(img.p.toString(), imgUrl, img.p);
                list.add(page);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            Document document = Jsoup.parse(html);
            Elements container1 = document.getElementsByClass("col-xl-8 px-0 mb-3 mb-md-0");
            Elements container2 = Jsoup.parse(container1.toString()).getElementsByClass("row m-0");
            if (container2.size() > 0) {
                // get(0):最近更新，get(1):新上架，get(2):少年，get(3):少女，get(4):青年
                Elements elements = container2.get(0).getElementsByClass("col-3 col-sm-2 col-xl-2 px-1 px-sm-2 pb-3");
                for (Element element : elements) {
                    String title = element.selectFirst("a[title]").attr("title");
                    String href = element.selectFirst("a").attr("href");
                    String coverUrl = element.selectFirst("img").attr("src");
                    Elements authorSelects = element.select("div[class=one-line comic-author]");
                    String author = authorSelects.get(0).selectFirst("a").ownText();
//                        String updateTag = authorSelects.get(1).selectFirst("a[title]").ownText();
                    Comic comic = new Comic(title, getSourceHost() + href, coverUrl);
                    comic.setAuthor(author);
//                    comicInfo.setUpdateTag(updateTag);
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
        try {
            List<Comic> list = new ArrayList<>();
            Document document = Jsoup.parse(html);
            Document topDocument = Jsoup.parse(document.getElementsByClass("list-group top10-list").toString());
            Elements top10s = topDocument.getElementsByClass("list-group-item border-0 top10-item");
            for (Element element : top10s) {
                String href = element.selectFirst("a").attr("href").trim();
                String coverUrl = element.selectFirst("img").attr("src");
                Element mediaBody = element.getElementsByClass("media-body").first();
                String title = mediaBody.selectFirst("h4[class=mt-0 mb-0]").selectFirst("a").text();
                Elements ps = mediaBody.select("p");
                Elements as = ps.get(0).select("a");
                StringBuilder author = new StringBuilder();
                for (Element aEle : as) {
                    if (author.length() > 0)
                        author.append(",");
                    author.append(aEle.text());
                }
                String introduce = ps.get(1).text();
                Comic comic = new Comic(title, getSourceHost() + href, coverUrl);
                comic.setAuthor(author.toString());
                comic.setIntroduce(introduce);
                list.add(comic);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    class Img {
        String img;
        Integer p;

        @Override
        public String toString() {
            return "Img{" +
                    "img='" + img + '\'' +
                    ", p=" + p +
                    '}';
        }
    }
}
