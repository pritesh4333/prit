package app.reelsdownloadervideo.storydownloader;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import okhttp3.ResponseBody;


public interface RetroInterface {
    String BASE_URLS = "https://instagram.fbom3-2.fna.fbcdn.net";

    @GET()
    @Streaming
    Call<ResponseBody> downloadFiles(@Url String s);
}
