package com.tenarse.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import org.json.JSONArray;

public class ConnectionNode {

    private String httpResultStats;
    private JSONArray statsArray;
    public final Object lock = new Object();

    public ConnectionNode() {
    }

    public void getStats(){
        //Connexió NodeJS
        HttpRequestBuilder requestBuilder = new HttpRequestBuilder();
        //final Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url("http://admin.alumnes.inspedralbes.cat:7073/getStats").build();
        final Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url("http://192.168.207.52:7073/getStats").build();
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                httpResultStats = httpResponse.getResultAsString();
                statsArray = new JSONArray(httpResultStats);
                synchronized (lock) {
                    lock.notify();
                }
            }

            @Override
            public void failed(Throwable t) {

            }

            @Override
            public void cancelled() {

            }
        });
    }

    public void addNewPartida(){

    }

    public JSONArray getStatsArray() {
        return statsArray;
    }
}
