package org.me.gcu.remote;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;

public interface ApiEndpoints {

    /**
     * This method returns an observable, i.e, data is emitted continuously.
     * @Streaming is used to get data in form of a stream.
     * This is the endpoint to get the xml file from the url
     **/

    @Streaming
    @GET("/feeds/MhSeismology.xml")
    Observable<ResponseBody> getData();
}
