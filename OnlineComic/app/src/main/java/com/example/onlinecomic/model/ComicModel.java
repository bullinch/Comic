//package com.example.onlinecomic.model;
//
//import android.util.Log;
//
//import com.example.library_base.base.BaseModel;
//import com.example.library_base.base.Callback;
//import com.zhouyou.http.EasyHttp;
//import com.zhouyou.http.callback.SimpleCallBack;
//import com.zhouyou.http.exception.ApiException;
//
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import io.reactivex.disposables.Disposable;
//
//public class ComicModel extends BaseModel {
//    private static final String TAG = "ComicModel";
//
//    public static final String HOST_URL = "https://www.manhuadb.com";
//    public String hostHtml;
//
//    public void requestGetHost(String hostUrl, Callback<String> callback) {
//        Disposable d = EasyHttp.get(hostUrl)
//                .cacheKey(getClass().getSimpleName()).execute(new SimpleCallBack<String>() {
//            @Override
//            public void onError(ApiException e) {
//                callback.onFailure(e.getMessage());
//            }
//
//            @Override
//            public void onSuccess(String s) {
//                hostHtml = s;
//                callback.onSuccess(s);
//                parseRankingList(s);
//            }
//        });
//    }
//
//
//    /**
//     * 解析最近更新漫画列表
//     */
//    public void aaaaa(String html) {
//        Document document = Jsoup.parse(hostHtml);
//        Elements container = document.getElementsByClass("container-xxl");
//        Document containerDoc = Jsoup.parse(container.toString());
//        Elements module = containerDoc.getElementsByClass("order-1 navbar-nav me-auto");
//        Document moduleDoc = Jsoup.parse(module.toString());
//        Elements elements = moduleDoc.select(".nav-item");  //选择器的形式
//        for (Element element : elements) {
//            String title = element.selectFirst("a").ownText();
//            String href = element.selectFirst("a").attr("href").trim();
//            System.out.println(title + "___" + href);
//            if (element.selectFirst("title") != null) {
//                Log.i(TAG, "onSuccess: " + element.selectFirst("a.title").ownText());
//            }
//            Log.i(TAG, "==" + element.text());
//        }
//
//        Elements elementss = moduleDoc.select(".dropdown-item");  //选择器的形式
//        for (Element element : elementss) {
//            String title = element.selectFirst("a").ownText();
//            String href = element.selectFirst("a").attr("href").trim();
//            System.out.println(title + "___" + href);
//            if (element.selectFirst("title") != null) {
//                Log.i(TAG, "onSuccess: " + element.selectFirst("a.title").ownText());
//            }
//        }
//    }
//
//    public List<ComicInfo> parseRecentUpdateList(String html) {
//        try {
//            List<ComicInfo> list = new ArrayList<>();
//            Document document = Jsoup.parse(html);
//            Elements container1 = document.getElementsByClass("col-xl-8 px-0 mb-3 mb-md-0");
//            Elements container2 = Jsoup.parse(container1.toString()).getElementsByClass("row m-0");
//            if (container2.size() > 0) {
//                // get(0):最近更新，get(1):新上架，get(2):少年，get(3):少女，get(4):青年
//                Elements elements = container2.get(0).getElementsByClass("col-3 col-sm-2 col-xl-2 px-1 px-sm-2 pb-3");
//                for (Element element : elements) {
//                    String title = element.selectFirst("a[title]").attr("title");
//                    String href = element.selectFirst("a").attr("href");
//                    String coverUrl = element.selectFirst("img").attr("src");
//                    Elements authorSelects = element.select("div[class=one-line comic-author]");
//                    String author = authorSelects.get(0).selectFirst("a").ownText();
//                    String updateTag = authorSelects.get(1).selectFirst("a[title]").ownText();
//                    ComicInfo comicInfo = new ComicInfo(title, author,  coverUrl, HOST_URL + href);
//                    comicInfo.setUpdateTag(updateTag);
//                    Log.i(TAG, "parseRecentUpdate: " + comicInfo.toString());
//                    list.add(comicInfo);
//                }
//            }
//            return list;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public List<ComicInfo> parseRankingList(String html) {
//        try {
//            List<ComicInfo> list = new ArrayList<>();
//            Document document = Jsoup.parse(html);
//            Document topDocument = Jsoup.parse(document.getElementsByClass("list-group top10-list").toString());
//            Elements top10s = topDocument.getElementsByClass("list-group-item border-0 top10-item");
//            for(Element element : top10s) {
//                String href = element.selectFirst("a").attr("href").trim();
//                String coverUrl = element.selectFirst("img").attr("src");
//                Element mediaBody = element.getElementsByClass("media-body").first();
//                String title = mediaBody.selectFirst("h4[class=mt-0 mb-0]").selectFirst("a").text();
//                Elements ps = mediaBody.select("p");
//                Elements as = ps.get(0).select("a");
//                StringBuilder author = new StringBuilder();
//                for(Element aEle : as) {
//                    if(author.length() > 0)
//                        author.append(",");
//                    author.append(aEle.text());
//                }
//                String introduce = ps.get(1).text();
//                ComicInfo comicInfo = new ComicInfo(title, author.toString(), coverUrl, HOST_URL + href);
//                comicInfo.setIntroduce(introduce);
//                Log.i(TAG, "parseRankingList: " + comicInfo.toString());
//                list.add(comicInfo);
//            }
//            return list;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public List<ComicInfo> parseClassificationList(String html) {
//        try {
//            List<ComicInfo> list = new ArrayList<>();
//            Document document = Jsoup.parse(html);
//            Document topDocument = Jsoup.parse(document.getElementsByClass("list-group top10-list").toString());
//            Elements top10s = topDocument.getElementsByClass("list-group-item border-0 top10-item");
//            for(Element element : top10s) {
//                String href = element.selectFirst("a").attr("href").trim();
//                String coverUrl = element.selectFirst("img").attr("src");
//                Element mediaBody = element.getElementsByClass("media-body").first();
//                String title = mediaBody.selectFirst("h4[class=mt-0 mb-0]").selectFirst("a").text();
//                Elements ps = mediaBody.select("p");
//                Elements as = ps.get(0).select("a");
//                StringBuilder author = new StringBuilder();
//                for(Element aEle : as) {
//                    if(author.length() > 0)
//                        author.append(",");
//                    author.append(aEle.text());
//                }
//                String introduce = ps.get(1).text();
//                ComicInfo comicInfo = new ComicInfo(title, author.toString(), coverUrl, HOST_URL + href);
//                comicInfo.setIntroduce(introduce);
//                Log.i(TAG, "parseRankingList: " + comicInfo.toString());
//                list.add(comicInfo);
//            }
//            return list;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//}
