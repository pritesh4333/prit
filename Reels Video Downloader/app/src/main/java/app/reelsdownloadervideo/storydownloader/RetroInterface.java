package app.reelsdownloadervideo.storydownloader;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import okhttp3.ResponseBody;


public interface RetroInterface {

    @GET()
    @Streaming
    Call<ResponseBody> downloadFiles(@Url String s);

    @GET()
    Call<ResponseBody> downloadresponse(@Url String s);
}
