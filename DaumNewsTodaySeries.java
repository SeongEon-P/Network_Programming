import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

// 문제 1) jsoup을 사용하여 다음 뉴스의 오늘의 연재 부분을 파싱하여 화면에 출력하는 프로그램을 작성하세요
// 출력 형태 = 기사 제목, 기사 링크 2가지 내용을 출력하세요
// 실행 순서
// 1. url 설정
// 2. Document 객체 생성
// 3. Connection.Response 객체 생성 및 Jsoup.connect()로 지정한 url
// 4. 받아온 데이터를 Document 객체로 변환
// 5. 가져올 데이터가 있는 태그 중 가장 가까운 조상 태그 가져오기
// 6. select()를 사용하여 원하는 태그 가져오기
// 7. 마지막에 선택한 태그에서 기사 제목 및 기사 링크 가져오기

public class DaumNewsTodaySeries {
    public static void main(String[] args){
        System.out.println("\n다음 뉴스의 오늘의 연재 출력하기\n");
        // 다음 뉴스 페이지 url 입력
        String url = "https://news.daum.net/?nil_profile=mini&nil_src=news";
        
        // Jsoup 파싱 데이터를 받기 위한 빈 객체 생성
        Document html = null;
        
        try{
            // 지정한 웹 서버의 Jsoup을 통해서 접속 및 전체 데이터 가져오기
            Connection.Response res = Jsoup.connect(url).method(Connection.Method.GET).execute();
            // 전체 데이터 중 html 코드만 파싱하여 받기
            html = res.parse();

        }
        catch (IOException e){
            System.out.println("Jsoup 로 데이터 파싱 중 오류가 발생했습니다.");
            e.printStackTrace();
        }
//        Elements items = html.select(".list_todayseries"):
//        System.out.println(items.size()); ul로 검색했을 때 몇개 나오는지 확인하는 코드
        
//        // 클래스 값이 lis_todayseries인 태그를 선택        
//        Element list_todayseries = html.select(".list_todayseries").first();
//        
//        // 클래스 값이 list_todayseries인 태그에서 클래스 값이 item_todayseries인 태그를 모두 검색
//        
//        Elements item_todayseries = list_todayseries.select(".item_todayseries");
        
        // 전체 html에서 클래스 값이 item_todayseries 인 태그 모두 검색
        Elements aTagList = html.select(".item_todayseries");
        
//        for (int i = 0; i < aTagList.size(); i++) {
//            Element item = aTagList.get(i);
            for(Element item : aTagList){ //향상된 for문
            

            Element newsATag = item.select(".link_txt").first();
            String newsTitle = newsATag.text();
            String newsLink = newsATag.attr("href");

            System.out.println("기사 제목 : " + newsTitle);
            System.out.println("기사 링크 : " + newsLink);


            // 2024-03-28
            // 해당 기사 페이지의 기사 내용 가져오기
            // 1. 기사 페이지 url 설정
            // 2. 기사 페이지의 html 태그를 받을 Document 객체 생성
            // 3. Jsoup.connect()를 사용하여 기사 페이지에 접속
            // 4. parser()를 사용하여 가져온 html 태그를 document 객체에 저장
            // 5. 기사 내용이 있는 태그를 select()를 사용하여 검색
            // 6. 기사 내용 가져오기

            // 기사 내용 페이지의html 태그를 받기 위한 Document 객체 생성
            Document subHtml = null;
            try {
            // 위에서 받아온 기사 링크를 이용하여 해당 기사 내용 페이지에 접속
                Connection.Response subRes = Jsoup.connect(newsLink).method
                        (Connection.Method.GET).execute();

            // 기사 내용 페이지의 내용을 가져와서 html로 파싱 후 저장
                subHtml = subRes.parse();

            }
            catch (IOException e){
                System.out.println("jsoup 사용 중 오류가 발생했습니다.");
                e.printStackTrace();
            }
            // 기사 내용 중 본문 부분을 검색
            // 기사 내용 본문의 부모 태그의 class가 article_view 이므로 select()를 사용하여 검색함
            Element subArticle_view = subHtml.select(".article_view").first();
            // 기사 내용은 p 태그를 사용하여 한 문단씩 작성되어 있으므로 select()를 이용하여 p 태그를 검색
            Elements subNewsPList = subArticle_view.select("p");
            // 기사 내용이 있는 첫번째 p 태그를 선택
            // 기사에 따라 첫번째 p 태그에 내용이 없을 수 있으므로 확인이 필요함
            Element subNewsPTAg = subNewsPList.get(0);
            // 기사 내용을 text()를 사용하여 가져옴
            String subNews = subNewsPTAg.text();
            System.out.println("뉴스 내용 : " + subNews);


            System.out.println("---------------------\n");


        }
    }
}
