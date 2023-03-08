package com.tenarse.game.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.HttpRequestBuilder;
import com.tenarse.game.utils.Settings;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


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
        final Net.HttpRequest httpRequest = requestBuilder.newRequest().method(Net.HttpMethods.GET).url("http://" + Settings.IP_SERVER +":" + Settings.PUERTO_PETICIONES +"/getStats").build();

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

    //POST per pujar una partida SINGLE
    public void addNewPartida(String username, String type, int kills, String timePlayed, int puntos, String nomMapa){
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
        partidaJSON.put("mapa", nomMapa);


        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        //httpRequest.setUrl("http://admin.alumnes.inspedralbes.cat:7073/newPartida");
        httpRequest.setUrl("http://" + Settings.IP_SERVER +":" + Settings.PUERTO_PETICIONES +"/newPartida");
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

    //POST per pujar una partida MULTI (Array<Jugador>, tiempoPartida, puntosTotal, nombreMapa)
    public void addNewPartidaMulti(ArrayList<Jugador> jugadores, String tiempo, int puntos, String nombreMapa){
        JSONObject partidaJSON = new JSONObject();

        JSONArray jugadoresJSON = new JSONArray();

        for (int i = 0; i < jugadores.size(); i++) {
            JSONObject jugador = new JSONObject();

            jugador.put("username", jugadores.get(i).getUsername());
            jugador.put("tipo", jugadores.get(i).getTypePlayer());
            jugador.put("kills", jugadores.get(i).getKillsJugador());

            String infoJugador = "{\"username\":" + jugadores.get(i).getUsername()
                    +",\"tipo\":\"" + jugadores.get(i).getTypePlayer()
                    + "\",\"kills\":" + jugadores.get(i).getKillsJugador() + "}";

            jugadoresJSON.put(jugador);
        }

        partidaJSON.put("jugadores", jugadoresJSON);
        partidaJSON.put("tiempo", tiempo);
        partidaJSON.put("puntos", puntos);
        partidaJSON.put("mapa", nombreMapa);

        Net.HttpRequest httpRequest = new Net.HttpRequest(Net.HttpMethods.POST);
        //httpRequest.setUrl("http://admin.alumnes.inspedralbes.cat:7073/newPartida");
        httpRequest.setUrl("http://" + Settings.IP_SERVER +":" + Settings.PUERTO_PETICIONES +"/newPartida");
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
