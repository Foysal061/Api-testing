package service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStreamReader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GitHubRepoApiTest {
    private final String repoName = "test";
    private final String githubName = "Foysal061";
    private final String token = "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";

    @Test
    public void acreateRepoSuccessful_then201IsReceived() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://api.github.com/user/repos");
        String json = "{\n" +
                "        \"name\": \"" + repoName + "\", \n" +
                "      }";
        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Authorization", "token " + token);

        CloseableHttpResponse response = client.execute(httpPost);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_CREATED));
        client.close();
    }

    @Test
    public void bcheckIfRepoExist_validateJson() throws IOException, JSONException, ParseException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("https://api.github.com/user/repos");
        httpGet.setHeader("Authorization", "token " + token);
        CloseableHttpResponse response = client.execute(httpGet);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_OK));
        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
        JSONArray jsonArray = (JSONArray) obj;
        for (int i= 0; i<jsonArray.size();i++){
            Object object = jsonArray.get(i);
            JSONObject jsonObject = (JSONObject) JSONValue.parse(new ObjectMapper().writeValueAsString(object));
            if(jsonObject.get("name").equals(repoName)){
                assert true;
                return;
            }
        }
        assert false;
    }

    @Test
    public void cdeleteRepoSuccessful_then204IsReceived() throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete("https://api.github.com/repos/"+githubName+"/"+repoName);
        httpDelete.setHeader("Accept", "application/json");
        httpDelete.setHeader("Authorization", "token " + token);

        CloseableHttpResponse response = client.execute(httpDelete);
        assertThat(response.getStatusLine().getStatusCode(), equalTo(HttpStatus.SC_NO_CONTENT));
        client.close();
    }

}
