package com.example.library_comic.parse;

import com.example.library_base.util.log.Logger;
import com.example.library_comic.SourceEnum;
import com.example.library_comic.bean.Chapter;
import com.example.library_comic.bean.Comic;
import com.example.library_comic.bean.Page;
import com.example.library_comic.callback.Callback;
import com.example.library_comic.common.ComicSource;
import com.example.library_comic.jsoup.Node;
import com.example.library_comic.util.StringUtils;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.SimpleCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.request.GetRequest;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class MangaBZ extends ComicSource {

    @Override
    public int getSourceId() {
        return SourceEnum.MANGA_BZ.ID;
    }

    @Override
    public String getSourceName() {
        return SourceEnum.MANGA_BZ.NAME;
    }

    @Override
    public String getSourceHost() {
        return SourceEnum.MANGA_BZ.HOST;
    }


    @Override
    public GetRequest getChaptersRequest(String url) {
        return EasyHttp.get(url).cacheKey(url);
    }

    @Override
    public GetRequest getPagesRequest(String url) {
        String dateFmt = "yyyy-MM-dd+HH:mm:ss";
        String dateStr = "";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(dateFmt);
        formatter.format(date);
        return EasyHttp.get(url).cacheKey(url).headers("Referer", "http://www.mangabz.com/m11028/")
                .headers("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 " +
                        "(KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36");
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
        Document document = Jsoup.parse(html);
        Node nodeDetail = new Node(document.selectFirst("[class=detail-list]"));
        for (Node node : nodeDetail.list("[class=detail-list-item]")) {
            String title = node.text();
            String href = node.attr("a", "href");
            String name = node.text();
            Chapter chapter = new Chapter(title, getSourceHost() + href, name);
            list.add(chapter);
        }
        return list;
    }

    @Override
    public List<Page> parsePages(String html) {
        int CID = 0;
        int MID = 0;
        String DT = "";
        String SIGN = "";
        int COUNT = 0;
        String cid_string = "MANGABZ_CID=";
        int cid_number = html.indexOf(cid_string, 0);
        CID = Integer.parseInt(html.substring(cid_number + cid_string.length(), html.indexOf(";", cid_number + 1)));
        //获取MID
        String mid_string = "MANGABZ_MID=";
        int mid_number = html.indexOf(mid_string, 0);
        MID = Integer.parseInt(html.substring(mid_number + mid_string.length(), html.indexOf(";", mid_number + 1)));
        //获取DT
        String dt_string = "MANGABZ_VIEWSIGN_DT=";
        int dt_number = html.indexOf(dt_string, 0);
        DT = html.substring(dt_number + dt_string.length(), html.indexOf(";", dt_number + dt_string.length()));
        DT = DT.replace("\"","");
        DT = DT.replace(" ","+");
        DT = DT.replace(":","%3A");

        //获取SIGN
        String sign_string = "MANGABZ_VIEWSIGN=";
        int sign_number = html.indexOf(sign_string, 0);
        SIGN = html.substring(sign_number + sign_string.length(), html.indexOf(";", sign_number + sign_string.length()));
        SIGN = SIGN.replace("\"","");
        //获取COUNT
        String count_string = "MANGABZ_IMAGE_COUNT=";
        int count_number = html.indexOf(count_string, 0);
        COUNT = Integer.parseInt(html.substring(count_number + count_string.length(), html.indexOf(";", count_number + 1)));
        Logger.i("CID=" + CID + " MID=" + MID + " DT=" + DT + " SIGN=" + SIGN+" COUNT="+COUNT);
        List<Page> pages = new ArrayList<>();

        CountDownLatch countDownLatch = new CountDownLatch(COUNT - 1);
        for(int i=1;i<COUNT;i++) {
            handleImageUrl(pages, MID, CID, DT, SIGN, i, countDownLatch);
        }
        Logger.i("pages-size="+pages.size());
        return new ArrayList<>();
    }

    private void handleImageUrl(List<Page> pages, int MID, int CID, String DT, String SIGN, int PAGE,
                                CountDownLatch countDownLatch) {
        String GET_JPG_URL = "http://www.mangabz.com/" + "m11028" + "/chapterimage.ashx?cid=" + CID + "&page=" +
                PAGE + "&key=&_cid=" + CID + "&_mid=" + MID + "&_dt=" + DT + "&_sign=" + SIGN; //发送数据的URL
        Logger.i("GET_JPG_URL="+GET_JPG_URL);
        EasyHttp.get(GET_JPG_URL).cacheKey(GET_JPG_URL).execute(new SimpleCallBack<String>() {
            @Override
            public void onError(ApiException e) {

            }

            @Override
            public void onSuccess(String html) {
                Logger.i(html);
                String END_URL;
                String KEY;

                String key_string = "dm5imagefun|";
                int key_number = html.indexOf(key_string, 0);
                int index = html.indexOf("|", key_number + key_string.length());
                KEY = html.substring(key_number + key_string.length(), index);
                Logger.i("KEY="+KEY);
                String page_string = PAGE + "_";
                int page_number = html.indexOf(page_string, 0);
                if (page_number != -1) {
                    String PAGE_URL = html.substring(page_number, html.indexOf("|", page_number + 1));//正则
                    END_URL = "http://image.mangabz.com/1/" + MID + "/" + CID + "/" + PAGE_URL + ".jpg?cid="
                    +CID+"&key="+KEY+"&uk=";
                    System.out.println("图片地址：" + END_URL);
                    pages.add(new Page(PAGE+"", END_URL, PAGE));
                    countDownLatch.countDown();
                }
            }
        });
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

    @Override
    public List<Comic> parseRecentUpdateList(String html) {
        return new ArrayList<>();
    }

    @Override
    public List<Comic> parseRankingList(String html) {
        try {
            List<Comic> list = new ArrayList<>();
            Document document = Jsoup.parse(html);
            Element element = document.body().selectFirst("[class=manga-i-list]");
            List<Node> nodes = new Node(element).list("[class=manga-i-list-item]");
            for (Node node : nodes) {
                String title = node.attr("a", "title");
                String href = node.attr("a", "href");
                String coverUrl = node.attr("img", "src");
                Comic comic = new Comic(title, getSourceHost() + href, coverUrl);
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
