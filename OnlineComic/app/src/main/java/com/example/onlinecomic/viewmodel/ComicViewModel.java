//package com.example.onlinecomic.viewmodel;
//
//import android.app.Application;
//
//import androidx.annotation.NonNull;
//import androidx.lifecycle.AndroidViewModel;
//import androidx.lifecycle.MutableLiveData;
//
//import com.example.library_base.base.Callback;
//import com.example.library_base.util.log.Logger;
//import com.example.library_comic.bean.Comic;
//import com.example.library_comic.parse.ManHuaDB;
//import com.example.library_comic.parse.Parser;
//import com.example.onlinecomic.bean.ComicInfo;
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
//import io.reactivex.disposables.CompositeDisposable;
//import io.reactivex.disposables.Disposable;
//
//public class ComicViewModel extends AndroidViewModel {
//
//    public static String HOST_URL = "https://www.manhuadb.com";
//
//    private CompositeDisposable compositeDisposable;
//
//    public MutableLiveData<List<ComicInfo>> recentUpdateList = new MutableLiveData<>();
//    public MutableLiveData<List<ComicInfo>> rankingList = new MutableLiveData<>();
//    public MutableLiveData<List<Comic>> searchComicList = new MutableLiveData<>();
//
//    public ComicViewModel(@NonNull Application application) {
//        super(application);
//        compositeDisposable = new CompositeDisposable();
//    }
//
//    Disposable disposable;
//    public void requestHostUrl(String url, Callback<String> callback) {
//        Logger.i("requestHostUrl: " + url);
//        HOST_URL = url;
//        disposable = EasyHttp.get("https://www.manhuadb.com")
//                .cacheKey(getClass().getSimpleName()).execute(new SimpleCallBack<String>() {
//                    @Override
//                    public void onError(ApiException e) {
//                        Logger.i("onError: " + e.getMessage());
//                        if (callback != null)
//                            callback.onFailure(e.getMessage());
//                    }
//
//                    @Override
//                    public void onSuccess(String s) {
//                        if (callback != null)
//                            callback.onSuccess(s);
//                        recentUpdateList.setValue(parseRecentUpdateList(s));
//                        rankingList.setValue(parseRankingList(s));
//                        compositeDisposable.remove(disposable);
//                    }
//                });
//        compositeDisposable.add(disposable);
//    }
//
//    public void requestSearchData(String keyWord) {
//        Logger.i("keyWord-->"+keyWord);
//        Parser parser = new ManHuaDB();
//        parser.getSearchRequest(keyWord, -1).execute(new SimpleCallBack<String>() {
//            @Override
//            public void onError(ApiException e) {
//            }
//
//            @Override
//            public void onSuccess(String s) {
//                searchComicList.setValue(parser.parseSearch(s));
//            }
//        });
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
////                        String updateTag = authorSelects.get(1).selectFirst("a[title]").ownText();
//                    ComicInfo comicInfo = new ComicInfo(title, author, coverUrl, HOST_URL + href);
//                    comicInfo.hostUrl = HOST_URL;
////                    comicInfo.setUpdateTag(updateTag);
//                    Logger.i(comicInfo.toString());
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
//            for (Element element : top10s) {
//                String href = element.selectFirst("a").attr("href").trim();
//                String coverUrl = element.selectFirst("img").attr("src");
//                Element mediaBody = element.getElementsByClass("media-body").first();
//                String title = mediaBody.selectFirst("h4[class=mt-0 mb-0]").selectFirst("a").text();
//                Elements ps = mediaBody.select("p");
//                Elements as = ps.get(0).select("a");
//                StringBuilder author = new StringBuilder();
//                for (Element aEle : as) {
//                    if (author.length() > 0)
//                        author.append(",");
//                    author.append(aEle.text());
//                }
//                String introduce = ps.get(1).text();
//                ComicInfo comicInfo = new ComicInfo(title, author.toString(), coverUrl, HOST_URL + href);
//                comicInfo.setIntroduce(introduce);
//                Logger.i(comicInfo.toString());
//                list.add(comicInfo);
//            }
//            return list;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    public void onDestroy() {
//        compositeDisposable.clear();
//    }
//
//}