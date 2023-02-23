package com.tenarse.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import org.json.JSONArray;
import org.json.JSONObject;


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
        final Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url("http://192.168.1.34:7073/getStats").build();

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

    //POST per pujar una partida (arrayJugadores, tiempo, puntos)
    public void addNewPartida(String username, String type, int kills, String timePlayed, int puntos){
        JSONObject partidaJSON = new JSONObject();

        JSONArray jugadores = new JSONArray();

        JSONObject player1 = new JSONObject();
        player1.put("username", username);
        player1.put("tipo", type);
        player1.put("kills", kills);
        jugadores.put(player1);

        partidaJSON.put("jugadores", jugadores);
        partidaJSON.put("tiempo", timePlayed);
        partidaJSON.put("puntos", puntos);


        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        //httpRequest.setUrl("http://admin.alumnes.inspedralbes.cat:7073/newPartida");
        httpRequest.setUrl("http://192.168.207.195:7073/newPartida");
        String data = partidaJSON.toString();
        httpRequest.setContent(data);
        httpRequest.setHeader("Content-Type", "application/json");
        Gdx.net.sendHttpRequest(httpRequest, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                System.out.println("Partida añadida correctamente!");
            }

            @Override
            public void failed(Throwable t) {

            }

            @Override
            public void cancelled() {

            }
        });
    }

    public JSONArray getStatsArray() {
        return statsArray;
    }
}
