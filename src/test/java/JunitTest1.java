import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.*;

//source : https://www.softwaretestinghelp.com/wiremock-tutorial/
public class JunitTest1 {

    @Rule
    public WireMockRule wm = new WireMockRule(wireMockConfig().port(8080));

    @Test
    public void exampleTest() throws IOException {
        configureStubs();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://localhost:8080/test/abc")
                .get()
                .build();
        Response response = client.newCall(request).execute();
        assertEquals("Test success!", response.body().string());
        verify(exactly(1),getRequestedFor(urlEqualTo("/test/abc")));
    }

    private void configureStubs() {
        configureFor("localhost", 8080);
        stubFor(get(urlEqualTo("/test/abc"))
                .willReturn(aResponse().withBody("Test success!")));
    }
}
