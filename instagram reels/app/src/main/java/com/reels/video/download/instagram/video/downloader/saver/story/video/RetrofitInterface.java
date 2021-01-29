package com.reels.video.download.instagram.video.downloader.saver.story.video;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface RetrofitInterface {
    String BASE_URL = "https://instagram.fbom3-2.fna.fbcdn.net";

    @GET()
    @Streaming
    Call<ResponseBody> downloadFile(@Url String s);
}
